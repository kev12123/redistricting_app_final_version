package com.appRestful.api.component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

import com.appRestful.api.component.data.Data;
import com.appRestful.api.component.data.Demography;
import com.appRestful.api.component.data.ElectionResults;
import com.appRestful.api.component.data.NoDataException;
import com.appRestful.api.enums.Demographic;
import com.appRestful.api.enums.Operation;
import com.appRestful.api.enums.PoliticalParty;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.utility.Utility;

public class Cluster extends SimpleGraph<Precinct,Edge> {

    protected Data data;
    protected String primaryId;

    protected Cluster(Class<? extends Edge> edgeClass) {
        super(edgeClass);
    }

    protected Cluster(Supplier<Precinct> vertexSupplier, Supplier<Edge> edgeSupplier, boolean weighted) {
        super(vertexSupplier, edgeSupplier, weighted);
    }

    public Cluster(Class<? extends Edge> edgeClass, Precinct firstPrecinct) {
        super(edgeClass);
        this.primaryId = firstPrecinct.getPrecinctID();
        this.addVertex(firstPrecinct);
        this.data = (Data) firstPrecinct.getPrecinctData().clone();
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

    public void addPrecinct(Precinct precinct, AlgorithmRequestModel algorithmData){
        this.addVertex(precinct);
        addPrecinctDemographyData(precinct,algorithmData);
        addPrecinctElectionResults(precinct);
    }

    private void addPrecinctDemographyData(Precinct precinct, AlgorithmRequestModel algorithmData){
        Demography precinctDemography =  precinct.getPrecinctData().getDemographyData();
        Demography clusterDemography = this.getClusterData().getDemographyData();
        for(PoliticalParty party:precinctDemography.getAllPartiesPresent()){
                clusterDemography.addToPartyPopulation(party, precinctDemography.getPopulationByParty(party));
        }
        for(Demographic demographic :precinctDemography.getAllRacesPresent()){
                clusterDemography.addToRacePopulation(demographic,precinctDemography.getDemographicPopulation(demographic),algorithmData);
        }
    }

    private void addPrecinctElectionResults(Precinct precinct){
        ElectionResults precinctResults = precinct.getPrecinctData().getElectionResultsByYear();
        ElectionResults clusterResults = this.getClusterData().getElectionResultsByYear();
        for(int year : precinctResults.getAllYearsPresent()){
            clusterResults.updateResult(year,precinctResults.getResultFromYear(year), Operation.ADD);
        }
    }

    public boolean removePrecinct(Precinct precinct,AlgorithmRequestModel algorithmData){
        boolean removed = this.removeVertex(precinct);
        if(removed) {
            removePrecinctDemographicData(precinct,algorithmData);
            removePrecinctElectionResults(precinct);
        }
        return removed;
    }

    private void removePrecinctDemographicData(Precinct precinct, AlgorithmRequestModel algorithmData){
        Demography precinctDemography = precinct.getPrecinctData().getDemographyData();
        Demography clusterDemography = this.getClusterData().getDemographyData();
        for(Demographic demographic :precinctDemography.getAllRacesPresent()){
            clusterDemography.addToRacePopulation(demographic,-precinctDemography.getDemographicPopulation(demographic),algorithmData);
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
        return cluster;
    }

    protected Map<Precinct,Precinct> clonePrecincts(Cluster cluster){
        Map<Precinct,Precinct> cloneMapping = new HashMap<>();
        for(Precinct precinct:getPrecincts()) {
            Precinct clonePrecinct = (Precinct) precinct.clone();
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

}