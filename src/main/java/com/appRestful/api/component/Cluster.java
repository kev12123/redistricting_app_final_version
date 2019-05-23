package com.appRestful.api.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;
import org.locationtech.jts.geom.Geometry;

import com.appRestful.api.component.data.Data;
import com.appRestful.api.component.data.Demography;
import com.appRestful.api.component.data.ElectionResults;
import com.appRestful.api.component.data.Geography;
import com.appRestful.api.component.data.NoDataException;
import com.appRestful.api.enums.Demographic;
import com.appRestful.api.enums.Operation;
import com.appRestful.api.enums.PoliticalParty;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.utility.Utility;

public class Cluster extends SimpleGraph<Precinct,Edge> {

    protected Data data;
    protected String primaryId;
    protected Set<Precinct> borderPrecincts;

    protected Cluster(Class<? extends Edge> edgeClass) {
        super(edgeClass);
        borderPrecincts = new HashSet<>();
    }

    protected Cluster(Supplier<Precinct> vertexSupplier, Supplier<Edge> edgeSupplier, boolean weighted) {
        super(vertexSupplier, edgeSupplier, weighted);
        borderPrecincts = new HashSet<>();
    }


    public Cluster(Class<? extends Edge> edgeClass, Precinct firstPrecinct) {
        super(edgeClass);
        this.primaryId = firstPrecinct.getPrecinctID();
        this.addVertex(firstPrecinct);
        this.data = (Data) firstPrecinct.getPrecinctData().clone();
        borderPrecincts = new HashSet<>();
        borderPrecincts.add(firstPrecinct);
        firstPrecinct.setParentCluster(this);
        data.getGeographicData().updateExternalEdgeCount(firstPrecinct.getAllNeighbors().size());
        //on construction firstPrecinct was setOnBorder = true
    }

    public void initilizeData(Data data){
        this.data = data;
    }

    public Set<Precinct> getPrecincts(){
        return this.vertexSet();
    }

    public String getPrimaryId(){
        return primaryId;
    }

    public Data getClusterData() {
        if(data != null)
            return data;
        else
            throw new NoDataException(Utility.noDataExceptionMessage);
    }

    public void addPrecinct(Precinct precinct, AlgorithmRequestModel algorithmRequestModel){
        this.addVertex(precinct);
        addPrecinctDemographyData(precinct, algorithmRequestModel);
        addPrecinctElectionResults(precinct);
        updateGrownBorders(precinct);
        updateGeometryOnAdd(precinct);
    }

    private void addPrecinctDemographyData(Precinct precinct, AlgorithmRequestModel algorithmRequestModel){
        Demography precinctDemography =  precinct.getPrecinctData().getDemographyData();
        Demography clusterDemography = this.getClusterData().getDemographyData();
        for(PoliticalParty party:precinctDemography.getAllPartiesPresent()){
                clusterDemography.addToPartyPopulation(party, precinctDemography.getPopulationByParty(party));
        }
        for(Demographic demographic :precinctDemography.getAllRacesPresent()){
                clusterDemography.addToRacePopulation(demographic,precinctDemography.getDemographicPopulation(demographic), algorithmRequestModel);
        }
    }

    private void addPrecinctElectionResults(Precinct precinct){
        ElectionResults precinctResults = precinct.getPrecinctData().getElectionResultsByYear();
        ElectionResults clusterResults = this.getClusterData().getElectionResultsByYear();
        for(int year : precinctResults.getAllYearsPresent()){
            clusterResults.updateResult(year,precinctResults.getResultFromYear(year), Operation.ADD);
        }
    }

    public void updateGrownBorders(Precinct newPrecinct){
        newPrecinct.setParentCluster(this);
        updateInternalExternalEdgeCountsOnAdd(newPrecinct);
        expandBorder(newPrecinct);
    }

    private void updateInternalExternalEdgeCountsOnAdd(Precinct newPrecinct){
        Set<Precinct> internalNeighbors = newPrecinct.getAllNeighborsInParentCluster();
        Set<Precinct> externalNeighbors = newPrecinct.getAllNeighborsNotInParentCluster();
        Geography geography = this.getClusterData().getGeographicData();
        geography.updateExternalEdgeCount(-internalNeighbors.size());
        geography.updateInternalEdgeCount(internalNeighbors.size());
        geography.updateExternalEdgeCount(externalNeighbors.size());
    }

    private void expandBorder(Precinct newPrecinct){
        Set<Precinct> neighborsInCluster = newPrecinct.getAllNeighborsInParentCluster();
        for(Precinct neighbor : neighborsInCluster){
            neighbor.setOnBorder(Utility.isNotOnBorder);
            if(neighbor.isOnBorder())
                borderPrecincts.add(neighbor);
            else
                borderPrecincts.remove(neighbor);
        }
        borderPrecincts.add(newPrecinct);
    }
    
    private void updateGeometryOnAdd(Precinct precinct) {
    		Geography geography = getClusterData().getGeographicData();
    		Geography precinctGeography = precinct.getPrecinctData().getGeographicData(); 
    		Geometry geometry = geography.getGeometry();
    		Geometry precinctGeomtry = precinctGeography.getGeometry();
    		geography.setGeometry(geometry.union(precinctGeomtry));
    }

    public boolean removePrecinct(Precinct precinct, AlgorithmRequestModel algorithmRequestModel){
        boolean removed = this.removeVertex(precinct);
        if(removed) {
            removePrecinctDemographicData(precinct, algorithmRequestModel);
            removePrecinctElectionResults(precinct);
            updateShrunkBorders(precinct);
            updateGeometryOnRemove(precinct);
        }
        return removed;
    }

    private void removePrecinctDemographicData(Precinct precinct, AlgorithmRequestModel algorithmRequestModel){
        Demography precinctDemography = precinct.getPrecinctData().getDemographyData();
        Demography clusterDemography = this.getClusterData().getDemographyData();
        for(Demographic demographic : precinctDemography.getAllRacesPresent()){
            clusterDemography.addToRacePopulation(demographic,-precinctDemography.getDemographicPopulation(demographic), algorithmRequestModel);
        }
        for (PoliticalParty politicalParty :precinctDemography.getAllPartiesPresent()){
            clusterDemography.addToPartyPopulation(politicalParty,-precinctDemography.getPopulationByParty(politicalParty));
        }
    }

    private void removePrecinctElectionResults(Precinct precinct){
        ElectionResults precinctResults = precinct.getPrecinctData().getElectionResultsByYear();
        ElectionResults clusterResults = this.getClusterData().getElectionResultsByYear();
        for(int year: precinctResults.getAllYearsPresent()){
            clusterResults.updateResult(year,precinctResults.getResultFromYear(year),Operation.REMOVE);
        }
    }

    private void updateShrunkBorders(Precinct removedPrecinct){
        updateInternalExternalEdgeCountsOnRemove(removedPrecinct);
        contractBorders(removedPrecinct);
    }

    private void updateInternalExternalEdgeCountsOnRemove(Precinct removePrecinct){
        Set<Precinct> internalNeighbors = removePrecinct.getAllNeighborsInParentCluster();
        Set<Precinct> externalNeighbors = removePrecinct.getAllNeighborsNotInParentCluster();
        Geography geography = this.getClusterData().getGeographicData();
        geography.updateInternalEdgeCount(-internalNeighbors.size());
        geography.updateExternalEdgeCount(internalNeighbors.size());
        geography.updateExternalEdgeCount(-externalNeighbors.size());
    }

    private void contractBorders(Precinct removedPrecinct){
        Set<Precinct> neighbors = removedPrecinct.getAllNeighborsInParentCluster();
        for (Precinct precinct : neighbors){
            precinct.setOnBorder(Utility.isOnBorder);
            borderPrecincts.add(precinct);
        }
        borderPrecincts.remove(removedPrecinct);
    }
    
    private void updateGeometryOnRemove(Precinct precinct) {
    		Geography geography = getClusterData().getGeographicData();
    		Geography precinctGeography = precinct.getPrecinctData().getGeographicData();
    		Geometry geometry = geography.getGeometry();
    		Geometry precinctGeometry = precinctGeography.getGeometry();
    		geography.setGeometry(geometry.difference(precinctGeometry));
    }

    public Set<Precinct> getBorderPrecincts(){
        return borderPrecincts;
    }

    public List<Precinct> getBorderPrecinctsAsList(){
        return new ArrayList<>(borderPrecincts);
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "data=" + data +
                ", primaryId='" + primaryId + '\'' +
                '}';
    }


    public int hashCode(){
        return primaryId.hashCode();
    }

    public boolean equals(Object o){
        if(!(o instanceof Cluster))
            return false;
        Cluster candidate = (Cluster)o;
        return this.primaryId.equals(candidate.primaryId);
    }

    public Object clone(){
        Cluster cluster = new Cluster(Edge.class);
        cluster.primaryId = this.primaryId;
        cluster.data = (Data) data.clone();
        Map<Precinct,Precinct> cloneMapping = clonePrecincts(cluster);
        cloneEdges(cluster,cloneMapping);
        cloneBorderPrecincts(cluster,cloneMapping);
        return cluster;
    }

    protected Map<Precinct,Precinct> clonePrecincts(Cluster cluster){
        Map<Precinct,Precinct> cloneMapping = new HashMap<>();
        for(Precinct precinct:getPrecincts()) {
            Precinct clonePrecinct = (Precinct) precinct.clone();
            clonePrecinct.setParentCluster(cluster);
            cluster.addVertex(clonePrecinct);
            cloneMapping.put(precinct,clonePrecinct);
        }
        return cloneMapping;
    }

    protected void cloneEdges(Cluster cluster, Map<Precinct,Precinct> cloneMapping){
        for(Precinct precinct:getPrecincts()) {
            for (Precinct neighbor : Graphs.neighborListOf(this, precinct)) {
                Edge edge = (Edge) this.getEdge(precinct, neighbor).clone();
                Precinct cloneNeighbor = cloneMapping.get(neighbor);
                Precinct clonePrecinct = cloneMapping.get(precinct);
                cluster.addEdge(clonePrecinct, cloneNeighbor, edge);
            }
        }
    }

    protected void cloneBorderPrecincts(Cluster cluster, Map<Precinct,Precinct> cloneMapping){
        for(Precinct precinct : borderPrecincts){
            cluster.borderPrecincts.add(cloneMapping.get(precinct));
        }
    }


}