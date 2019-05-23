package com.appRestful.api.component;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

import com.appRestful.api.algorithm.RunStatistics;
import com.appRestful.api.enums.Demographic;
import com.appRestful.api.enums.PrecinctSelection;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.model.request.RequestQueue;
import com.appRestful.api.model.response.DataResponse;
import com.appRestful.api.utility.JoinabilityComparator;
import com.appRestful.api.utility.PopulationComparator;
import com.appRestful.api.utility.Utility;
import com.google.common.collect.Sets;

public class State extends SimpleGraph<Cluster,Edge> {
    private int precinctQuantity;
    private Map<String,Precinct> precincts;
    private Set<Cluster[]> candidatePairs;
    private Set<District> majorityMinorityDist;
    private RunStatistics statistics;
    private int totalPopulation;

    public State(Class<? extends Edge> edgeClass) {
        super(edgeClass);
        initiateState();
    }

    public State(Supplier<Cluster> vertexSupplier, Supplier<Edge> edgeSupplier) {
        super(vertexSupplier, edgeSupplier, Utility.notWeighted);
        initiateState();
    }

    private void initiateState(){
        this.precincts = new TreeMap<>();
        this.majorityMinorityDist = new HashSet<>();
        this.statistics = new RunStatistics();
    }

    private List<Cluster> sortClustersByPopulation(){
        List<Cluster> clusters = new ArrayList<>();
        Cluster[] arrayType = new Cluster[0];
        Collections.addAll(clusters,this.getClusters().toArray(arrayType));
        clusters.sort(new PopulationComparator());
        return clusters;
    }

    private List<Edge> sortEdgeByJoinability(Set<Edge> unsortedEdges){
        List<Edge> sortedEdges = new ArrayList<>();
        Iterator<Edge> edgeIterator = unsortedEdges.stream().sorted(new JoinabilityComparator()).iterator();
        while (edgeIterator.hasNext()){
            sortedEdges.add(edgeIterator.next());
        }

        return sortedEdges;
    }

    public void findCandidatePairs(){
        this.candidatePairs = new HashSet<>();
        List<Cluster> sortedClusters = sortClustersByPopulation();
        Iterator<Cluster> clusterIterator = sortedClusters.iterator();
        Set<Cluster> pairedClusters = new HashSet<>();
        int pairsFound = 0;

        while (pairsFound < targetPairQuantity(sortedClusters) && clusterIterator.hasNext()){
            Cluster[] pair = new Cluster[Utility.pair_size];
            findCandidatePairForCluster(pair,pairedClusters,clusterIterator);
            if(pair[Utility.destination] != null) {
                candidatePairs.add(pair);
                pairsFound++;
            }
        }
    }

    private void findCandidatePairForCluster(Cluster[] pair, Set<Cluster> pairedClusters, Iterator<Cluster> clusterIterator){
        pair[Utility.source] = clusterIterator.next();
        if(pairedClusters.contains(pair[Utility.source])) return;
        List<Edge> sortedEdges = sortEdgeByJoinability(this.edgesOf(pair[Utility.source]));

        for(Edge edge:sortedEdges){
            Cluster destination = Graphs.getOppositeVertex(this,edge,pair[Utility.source]);
            if(!pairedClusters.contains(destination)){
                pair[Utility.destination] = destination;
                boolean exists = this.assertVertexExist(pair[Utility.source]);
                boolean exists2 = this.assertVertexExist(pair[Utility.destination]);
                pairedClusters.add(pair[Utility.source]);
                pairedClusters.add(pair[Utility.destination]);
                break;
            }
        }
    }

    public void joinCandidatePairs(AlgorithmRequestModel algorithmRequestModel){
        for (Cluster[] candidatePair : candidatePairs){
            mergeClusters(candidatePair[Utility.source],candidatePair[Utility.destination], algorithmRequestModel);
            //RequestQueue.requestQueue.add(createPhaseOneResponse(candidatePair[Utility.destination]));
            if(algorithmRequestModel.getGoalDistricts() == getClustersQuantity()) return;
        }
    }
    
    
    private DataResponse createPhaseOneResponse(Cluster cluster){
    	List<String> data = new ArrayList();
    	data.add(cluster.getPrimaryId());
    	for(Precinct precinct : cluster.getPrecincts()) {
    		data.add(precinct.getPrecinctID());
    	}
    	DataResponse dataResponse = new DataResponse();
    	dataResponse.setDistrictData(data);
    	dataResponse.setStage(Utility.phaseOneResponse);
    	return dataResponse;
    }

    public void removeCluster(Cluster cluster){
        this.removeVertex(cluster);
    }

    public void addCluster(Cluster cluster){
        this.addVertex(cluster);
        if(cluster.getClusterData().getDemographyData().isMajorityMinority()){
            majorityMinorityDist.add((District) cluster);
        }
        totalPopulation += cluster.getClusterData().getDemographyData().getTotalPopulation();
    }

    public Set<Cluster> getClusters(){
        return this.vertexSet();
    }

    public Set<District> getDistricts(){
        return getClusters().stream().map(District.class::cast).collect(Collectors.toSet());
    }

    public List<District> getDistrictsAsList(){
        return getClusters().stream().map(District.class::cast).collect(Collectors.toList());
    }

    public int getClustersQuantity(){
        return this.getClusters().size();
    }


    private void mergeClusters(Cluster source, Cluster destination, AlgorithmRequestModel algorithmRequestModel){
        this.removeFromMajorityMinority(source);
        this.removeFromMajorityMinority(destination);
        this.removeEdge(source,destination);

        for(Precinct precinct : source.vertexSet()) {
            destination.addPrecinct(precinct, algorithmRequestModel);
        }

        Set<Edge[]> commonEdges = getCommonEdges(source,destination);
        Map<Edge,Cluster> distinctEdges = getDistinctEdges(source,destination);

        for(Edge[] pairs: commonEdges){
            mergeEdge(destination,pairs[Utility.source],pairs[Utility.destination], algorithmRequestModel);
        }

        for(Edge distinctEdge : distinctEdges.keySet()){
            Cluster neighbor = distinctEdges.get(distinctEdge);
            Edge newEdge = new Edge(destination,neighbor, algorithmRequestModel);
            this.removeEdge(distinctEdge);
            this.addEdge(destination,neighbor,newEdge);
        }

        this.addToMajorityMinority(destination);
        this.removeCluster(source);
    }

    private void removeFromMajorityMinority(Cluster cluster){
        if(majorityMinorityDist.contains(cluster)){
            majorityMinorityDist.remove(cluster);
        }
    }

    private void addToMajorityMinority(Cluster cluster){
        if(cluster.getClusterData().getDemographyData().isMajorityMinority()){
            majorityMinorityDist.add((District) cluster);
        }
    }

    private Set<Cluster> findCommonNeighbors(Cluster source, Cluster destination){
        Set<Cluster> sourceNeighbors = Graphs.neighborSetOf(this,source);
        Set<Cluster> destinationNeighbors = Graphs.neighborSetOf(this,destination);
        return Sets.intersection(sourceNeighbors,destinationNeighbors);
    }

    private Set<Edge[]> getCommonEdges(Cluster source, Cluster destination){
        Set<Cluster> commonNeighbors = findCommonNeighbors(source,destination);
        Set<Edge[]> commonEdges = new HashSet<>();

        for(Cluster neighbor: commonNeighbors){
            commonEdges.add(new Edge[] {this.getEdge(source,neighbor),this.getEdge(destination,neighbor)});
        }

        return commonEdges;
    }

    private Map<Edge,Cluster> getDistinctEdges(Cluster source, Cluster destination){
        Set<Cluster> commonNeighbors = findCommonNeighbors(source,destination);
        Set<Cluster> distinctDestinationNeighbors = Sets.difference(Graphs.neighborSetOf(this,destination),commonNeighbors);
        Set<Cluster> distinctSourceNeighbors = Sets.difference(Graphs.neighborSetOf(this,source),commonNeighbors);
        Map<Edge,Cluster> distinctEdges = new HashMap<>();

        for(Cluster neighbor : distinctDestinationNeighbors){
            distinctEdges.put(this.getEdge(destination,neighbor),neighbor);
        }
        for(Cluster neighbor : distinctSourceNeighbors){
            distinctEdges.put(this.getEdge(source,neighbor),neighbor);
        }
        return distinctEdges;
    }

    private void mergeEdge(Cluster destination, Edge sourceEdge, Edge destinationEdge, AlgorithmRequestModel algorithmRequestModel){
        Cluster neighbor = Graphs.getOppositeVertex(this,destinationEdge,destination);
        Edge newEdge  = new Edge(neighbor,destination, algorithmRequestModel);
        this.removeEdge(sourceEdge);
        this.removeEdge(destinationEdge);
        this.addEdge(neighbor,destination,newEdge);
    }


    private boolean attemptPrecinctMove(){
        return false;
    }

    public Set<District> getMajorityMinorityDist(){
        return majorityMinorityDist;
    }


    public Set<Precinct> sortByEthnicity(Demographic demographic, Set<Precinct> precincts){
        return null;
    }


    public Set<Set<Precinct>> splitByPercentile(Set<Precinct> precincts){
        return null;
    }


    public Map<District, String> colorByPrecentiles(Set<Set<Precinct>> percentiles){
        return null;
    }

    public long targetPairQuantity(List<Cluster> sortedClusters){
       return (long) Math.floor(sortedClusters.size() * Utility.targetClusterPercentageMerged);
    }

    //TODO:Check precinctQuantity Update
    public Object clone(){
        State state = new State(Edge.class);
        Map<Cluster,Cluster> cloneMapping = cloneClusters(state);
        cloneEdges(state,cloneMapping);
        cloneMajorityMinority(state,cloneMapping);
        state.statistics = new RunStatistics();
        return state;
    }

    private Map<Cluster,Cluster> cloneClusters(State state){
        Map<String,Precinct> allPrecincts = new TreeMap<>();
        Map<Cluster,Cluster> cloneMapping = new HashMap<>();
        for(Cluster cluster:vertexSet()){
            Cluster cloneCluster = (Cluster) cluster.clone();
            state.addVertex(cloneCluster);
            cloneMapping.put(cloneCluster,cloneCluster);
            addPrecinctsToGlobalMapping(cloneCluster,allPrecincts);
        }
        state.resetPrecinctNeighbors(allPrecincts);
        return cloneMapping;
    }

    private void cloneEdges(State state,Map<Cluster,Cluster> cloneMapping){
        for(Cluster cluster:getClusters()) {
            for (Cluster neighbor : Graphs.neighborListOf(this, cluster)) {
                Edge edge = (Edge) this.getEdge(cluster, neighbor).clone();
                Cluster neighborClone = cloneMapping.get(neighbor);
                Cluster clusterClone = cloneMapping.get(cluster);
                state.addEdge(clusterClone, neighborClone, edge);
                System.out.println("Cloned edge " + cluster.getPrimaryId() + "-" + neighbor.getPrimaryId());
            }
        }
    }

    private void addPrecinctsToGlobalMapping(Cluster cluster, Map<String,Precinct> allPrecincts){
        for(Precinct precinct : cluster.getPrecincts()){
            allPrecincts.put(precinct.getPrecinctID(),precinct);
        }
    }

    private void resetPrecinctNeighbors(Map<String,Precinct> allPrecincts){
        for(Cluster cluster: this.getClusters()){
            for(Precinct precinct : cluster.getPrecincts()){
                precinct.resetNeighbors(allPrecincts);
            }
        }
    }

    private void cloneMajorityMinority(State newState, Map<Cluster,Cluster> clusterMapping){
        newState.majorityMinorityDist = new HashSet<>();
        for(District district : majorityMinorityDist){
            newState.majorityMinorityDist.add((District) clusterMapping.get(district));
        }
    }

    public boolean hasCandidatePairs(){
        return candidatePairs.size() != 0;
    }

    public void assertClustersAreDistricts(){
        for(Cluster cluster:this.getClusters()){
            if(!(cluster instanceof District))
                throw new InvalidDistrictException();
        }
    }

    public int getTotalPopulation() {
        return totalPopulation;
    }
}

