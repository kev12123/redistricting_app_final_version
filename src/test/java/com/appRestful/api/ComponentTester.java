package com.appRestful.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;

import com.appRestful.api.algorithm.Algorithm;
import com.appRestful.api.algorithm.ObjectiveFunction;
import com.appRestful.api.component.Cluster;
import com.appRestful.api.component.District;
import com.appRestful.api.component.State;
import com.appRestful.api.component.data.Data;
import com.appRestful.api.component.data.Demography;
import com.appRestful.api.component.data.ElectionResults;
import com.appRestful.api.component.data.Geography;
import com.appRestful.api.component.data.Result;
import com.appRestful.api.enums.Demographic;
import com.appRestful.api.enums.Measure;
import com.appRestful.api.enums.PoliticalParty;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.component.Edge;
import com.appRestful.api.component.Precinct;

public class ComponentTester {

    private static int nextId = 0;
    private static final int NUMBER_OF_CLUSTERS = 1000;
    private static final int GOAL_DISTRICTS = 8;
    private int numEdges = 0;
    private long start;
    private long end;

    public static void main(String[] args){
        ComponentTester tester = new ComponentTester();
        AlgorithmRequestModel algorithmRequestModel = tester.createTestAlgorithmData();
        Map<Measure,Double> weights = new HashMap<>();
        for(Measure measure : Measure.values()) weights.put(measure,1.0);
        State state = tester.createTestState(NUMBER_OF_CLUSTERS, algorithmRequestModel);
        ObjectiveFunction objectiveFunction = new ObjectiveFunction(weights, algorithmRequestModel,state);
        System.out.println(tester.numEdges);
        Algorithm algorithm = new Algorithm(state);
        algorithm.initializeAlgorithm(objectiveFunction, algorithmRequestModel);
        int count = 0;
        for(Cluster cluster: algorithm.getNewState().getClusters()) {
            System.out.printf("%d: %s\n",++count,cluster);
        }
        System.out.println();
        tester.startTimer();
        algorithm.run();
        tester.endTime();
        count = 0;
        for(Cluster cluster: algorithm.getNewState().getClusters()) {
           System.out.printf("%d: %s\n",++count,cluster);
        }
        System.out.println("number of synthetic edges: " + tester.getNumEdges());
        System.out.println("Time Elapsed: " + tester.getRunTime() + "sec");
    }

    public State createTestState(int numClusters, AlgorithmRequestModel algorithmRequestModel){
        State state = new State(Edge.class);
        for(int i = 0; i < numClusters;i++){
            Cluster newCluster = createTestCluster(algorithmRequestModel);
            state.addCluster(newCluster);
            for(Cluster cluster : state.getClusters()){
                if(cluster != newCluster && Math.random() > .97) {
                    Edge edge = new Edge(newCluster,cluster, algorithmRequestModel);
                    state.addEdge(newCluster, cluster,edge);
                    numEdges++;
                }
            }
            System.out.println("Cluster " + newCluster.getPrimaryId() + " created");
        }

        System.out.println("here");
        int count = 0;
        for(Cluster cluster : state.getClusters()){
            for(Cluster neighbor : state.getClusters()){
                for(Precinct precinct : cluster.getPrecincts())
                    for(Precinct  neighborPrecinct: neighbor.getPrecincts())
                        if(Math.random() > .99)
                            precinct.addMutualNeighbor(neighborPrecinct);
                        System.out.println(count++);
            }
        }
        System.out.println("here 2");

        for(Cluster cluster : state.getClusters()){
                for(Precinct precinct : cluster.getPrecincts())
                        precinct.initilizeBorder();
        }

        algorithmRequestModel.setTargetPopulation(state.getTotalPopulation()/ algorithmRequestModel.getGoalDistricts());
        return state;
    }

    public Cluster createTestCluster(AlgorithmRequestModel algorithmRequestModel){
        Precinct precinct = createTestPrecinct(algorithmRequestModel);
        return new District(Edge.class,precinct);
    }

    public AlgorithmRequestModel createTestAlgorithmData(){
        AlgorithmRequestModel algorithmRequestModel = new AlgorithmRequestModel();
        algorithmRequestModel.setGoalDistricts(GOAL_DISTRICTS);
        algorithmRequestModel.setGoalMajorityMinorityDistricts(1);
        algorithmRequestModel.setMajorityMinorityDemographic(Demographic.BLACK);
        algorithmRequestModel.setMajorityMinorityMinPercentage(0);
        algorithmRequestModel.setMajorityMinorityMaxPercentage(1);
        algorithmRequestModel.setTargetPopulation(300);
        algorithmRequestModel.setIterationQuantity(100);

        return algorithmRequestModel;
    }


    public Precinct createTestPrecinct(AlgorithmRequestModel algorithmRequestModel){

        Demography demography = createTestDemography(algorithmRequestModel);
        ElectionResults electionResults = createTestElectionResults();
        Geography geography = createTestGeography();

        Data data = new Data(demography,electionResults,geography);

        Precinct precinct = new Precinct(nextId + "");
        nextId++;
        precinct.initializeData(data);

        return precinct;
    }

    public Demography createTestDemography(AlgorithmRequestModel algorithmRequestModel){
        Map<Demographic,Long> demographics0 = new HashMap<>();
        int[] populationSizes = new int[]{100,100,100};
        int populationSize = populationSizes[(int)(Math.random() * 3)];
        int equalRaceDivisor = 3;
        int equalPartDivisor = 2;
        demographics0.put(Demographic.WHITE,(long)populationSize/equalRaceDivisor);
        demographics0.put(Demographic.BLACK,(long)populationSize/equalRaceDivisor);
        demographics0.put(Demographic.HISPANIC,(long)populationSize/equalRaceDivisor);
        Map<PoliticalParty,Long> parties0 = new HashMap<>();
        parties0.put(PoliticalParty.DEMOCRAT,(long)populationSize/equalPartDivisor);
        parties0.put(PoliticalParty.REPUBLICAN,(long)populationSize/equalPartDivisor);
        return new Demography((long)populationSize,demographics0,parties0, algorithmRequestModel);
    }

    public ElectionResults createTestElectionResults(){
        Result result00 = new Result(1999,75,75);
        Result result01 = new Result(2000,75,75);
        Result result02 = new Result(2001,75,75);
        Collection<Result> results0 = new ArrayList<>();
        results0.add(result00);
        results0.add(result01);
        results0.add(result02);
        return new ElectionResults(results0);
    }

    public Geography createTestGeography(){
        Coordinate coordinate1 = new Coordinate(0,0);
        Coordinate coordinate2 = new Coordinate(5,0);
        Coordinate coordinate4 = new Coordinate(5,5);
        Coordinate coordinate3 = new Coordinate(0,5);
        Coordinate coordinate5 = new Coordinate(0,0);
        return new Geography((Math.random() > .5) ? "Nassau":"Suffolk",new Coordinate[]{coordinate1,coordinate2,coordinate3,coordinate4,coordinate5});
    }

    public int getNumEdges() {
        return numEdges;
    }

    public void startTimer(){
        start = System.nanoTime();
    }

    public void endTime(){
        end = System.nanoTime();
    }

    public double getRunTime(){
        return (end - start)/1000000000.;
    }
}
