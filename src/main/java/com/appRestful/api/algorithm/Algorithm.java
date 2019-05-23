package com.appRestful.api.algorithm;
import java.util.*;
import java.util.stream.Collectors;

import com.appRestful.api.component.Cluster;
import com.appRestful.api.component.District;
import com.appRestful.api.component.Precinct;
import com.appRestful.api.component.State;
import com.appRestful.api.component.data.Move;
import com.appRestful.api.enums.AlgorithmStatus;
import com.appRestful.api.enums.Measure;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.model.request.RequestQueue;
import com.appRestful.api.model.response.DataResponse;
import com.appRestful.api.utility.Utility;

//TODO: Finish writing algorithm class
public class Algorithm {
    private State origState;
    private State newState;
    private AlgorithmStatus status;
    private ObjectiveFunction objectiveFunction;
    private AlgorithmRequestModel algorithmRequestModel;


    public Algorithm(State state) {
        this.origState = state;
        status = AlgorithmStatus.CREATED;
    }

    public void initializeAlgorithm(ObjectiveFunction objectiveFunction, AlgorithmRequestModel algorithmRequestModel) {
        if (algorithmRequestModel.getGoalDistricts() >= origState.getClusters().size())
            throw new IllegalArgumentException(Utility.districtGoalOutOfBounds);
        this.objectiveFunction = objectiveFunction;
        this.algorithmRequestModel = algorithmRequestModel;
        newState = (State) origState.clone();
        newState.assertClustersAreDistricts();
        objectiveFunction.setState(newState);
        status = AlgorithmStatus.INITIALIZED;
    }
    HashMap<String, String>  districtMapper = new HashMap<>();
    public void run() {
        assertInitialization();
        status = AlgorithmStatus.RUNNING;
        runPhaseOne();
        for(Cluster cluster : newState.getClusters()) {
            System.out.println("CLUSTER " + cluster.getPrimaryId());
            for(Precinct precinct : cluster.getPrecincts()){
                System.out.printf("\tprecinct ID: %s\n",precinct.getPrecinctID());
            }
            ArrayList<String> precinctsData =  new ArrayList<>();
            precinctsData.add(cluster.getPrimaryId());

            for(Precinct precinct : cluster.getPrecincts()){
                System.out.println("\t" + "PRECINCT " + precinct.getPrecinctID());
                precinctsData.add(precinct.getPrecinctID());
                districtMapper.put(precinct.getPrecinctID(), cluster.getPrimaryId());
            }
            DataResponse dataResponse =  new DataResponse();
            dataResponse.setDistrictData(precinctsData);
            dataResponse.setStage(Utility.phaseOneResponse);
            dataResponse.setPopulation(cluster.getClusterData().getDemographyData().getTotalPopulation());
//            String toSend = "";
//            for (String s :dataResponse.getDistrictData()){
//                toSend += "#" + s;
//            }
            //dataResponse.getDistrictData().removeAll(dataResponse.getDistrictData());
            RequestQueue.requestQueue.add(dataResponse);
        }
        runPhaseTwo();

//        for(Cluster cluster : newState.getClusters()) {
//            System.out.println("CLUSTER " + cluster.getPrimaryId());
//            System.out.println("Precints being inserted" + cluster.getPrecincts().size());
//            ArrayList<String> precintsData =  new ArrayList<>();
//            precintsData.add(cluster.getPrimaryId());
//            for(Precinct precinct : cluster.getPrecincts()){
//                System.out.println("\t" + "PRECINCT " + precinct.getPrecinctID());
//                precintsData.add(precinct.getPrecinctID());
//            }
//            DataResponse dataResponse =  new DataResponse();
//            dataResponse.setDistrictData(precintsData);
//            dataResponse.setStage(Utility.phaseTwoResponse);
//
//            RequestQueue.requestQueue.add(dataResponse);
//        }


        sendFinalInformation();
        DataResponse doneResponse = new DataResponse();
        doneResponse.setStage("DONE");
        RequestQueue.requestQueue.add(doneResponse);

        status = AlgorithmStatus.COMPLETED;
    }

    private void sendFinalInformation(){
        sendFinalMajorityMinorityData();
        sendFinalGerrymanderingData();
    }

    private void sendFinalMajorityMinorityData(){
        List<String> majorityMinorityIDs = new ArrayList<>();
        for(District district : newState.getDistricts()){
            if(district.getDistrictData().getDemographyData().isMajorityMinority()){
                majorityMinorityIDs.add(district.getPrimaryId());
            }
        }
        DataResponse majorityMinorityResponse = new DataResponse();
        majorityMinorityResponse.setDistrictData(majorityMinorityIDs);
        majorityMinorityResponse.setStage(Utility.majorityMinorityResponse);
        RequestQueue.requestQueue.add(majorityMinorityResponse);
    }

    private void sendFinalGerrymanderingData(){
        DataResponse dataResponse = new DataResponse();
        dataResponse.setFinalGerrymanderingValue(objectiveFunction.getValue(Measure.EFFICENCY_GAP));
        dataResponse.setStage(Utility.finalGerrymanderingResponse);
        RequestQueue.requestQueue.add(dataResponse);
    }

    private DataResponse createDataResponse(Cluster cluster){
        DataResponse dataResponse = new DataResponse();
        List<String> ids = new ArrayList<>();
        ids.add(cluster.getPrimaryId());
        for(Precinct precinct: cluster.getPrecincts()){
            ids.add(precinct.getPrecinctID());
        }
        dataResponse.setDistrictData(ids);
        dataResponse.setStage(Utility.phaseOneResponse);
        return dataResponse;
    }

    public void runPhaseOne() {
        boolean phaseOneCompleted;
        int count = 0;
        do {
            phaseOneCompleted = runPhaseOneStep();
            System.out.println("PHASE 1 STEP: " + count++);
        } while (!phaseOneCompleted);
    }

    public boolean runPhaseOneStep() {
        newState.findCandidatePairs();
        newState.joinCandidatePairs(algorithmRequestModel);
        return newState.getClustersQuantity() == algorithmRequestModel.getGoalDistricts() || !newState.hasCandidatePairs();
    }

    public void runPhaseTwo() {
        initializePhaseTwo();
        System.out.println("START PHASE TWO");
        int iteration = Utility.initialIteration;
        while (iteration < algorithmRequestModel.getIterationQuantity()){
            iteration += (runPhaseTwoStep()) ? Utility.nextIteration : Utility.skipIterations;
            System.out.println("ITERATION: " + iteration);
        }
     }

    private void initializePhaseTwo(){
        for(Cluster cluster:newState.getClusters()){
            objectiveFunction.updateAllMeasures(cluster.getClusterData());
        }
        sendInitialGerrymanderingValue();
    }

    private void sendInitialGerrymanderingValue(){
        DataResponse gerrymanderingDataResponse = new DataResponse();
        gerrymanderingDataResponse.setInitialGerrymanderingValue(objectiveFunction.getValue(Measure.EFFICENCY_GAP));
        gerrymanderingDataResponse.setStage(Utility.initialGerrymanderingResponse);
        RequestQueue.requestQueue.add(gerrymanderingDataResponse);
    }

    private boolean runPhaseTwoStep() {
        Move move = getMove();
        if (move.applyMove()){
            DataResponse dataResponse =  new DataResponse();
            List<String> toSend = new ArrayList<>();
            toSend.add(move.getToDistrict().getPrimaryId());
            toSend.add(move.getPrecinct().getPrecinctID());
            dataResponse.setDistrictData(toSend);
            dataResponse.setObjectiveFunctionValue(objectiveFunction.getTotalValue());
            dataResponse.setStage(Utility.phaseTwoResponse);
            RequestQueue.requestQueue.add(dataResponse);
            System.out.printf("TO SEND TO QUEUE: Cluster ID: %s :: Precinct ID: %s\n",dataResponse.getDistrictData().get(0), dataResponse.getDistrictData().get(1));
            System.out.printf("BACKEND DATA: Cluster ID: %s :: Precinct ID: %s\n", move.getToDistrict().getPrimaryId(), move.getPrecinct().getPrecinctID());
            return true;
        }else {
            return false;
        }
    }

    private Move getMove(){
        District fromDistrict = getRandomDistrict();
        Precinct precinct = getBorderPrecinct(fromDistrict);
        District toDistrict = getBorderingDistrict(precinct);
        if(!newState.getClusters().contains(toDistrict) || !newState.getClusters().contains(fromDistrict))
            System.out.println("-----------------------INLAID CLUSTER-----------------------------");
        return new Move(precinct,fromDistrict,toDistrict, algorithmRequestModel,objectiveFunction);
    }

    private District getRandomDistrict(){
        List<District> districts = newState.getDistrictsAsList();
        int randomIndex = (int) (Math.random() * districts.size());
        return districts.get(randomIndex);
    }

    private Precinct getBorderPrecinct(District district){
        List<Precinct> borderPrecincts = district.getBorderPrecinctsAsList();
        int randomIndex = (int) (Math.random() * borderPrecincts.size());
        return borderPrecincts.get(randomIndex);
    }

    private District getBorderingDistrict(Precinct precinct){
        List<Precinct> neighborPrecincts = new ArrayList<>(precinct.getAllNeighborsNotInParentCluster());
        int randomIndex = (int)(Math.random() * neighborPrecincts.size());
        return neighborPrecincts.get(randomIndex).getParentDistrict();
    }

    private District getNeighborDistrict(Precinct precinct){
        for(Precinct neighbor: precinct.getAllNeighbors()){
            if(neighbor.isOnBorder())
                return neighbor.getParentDistrict();
        }
        return null;
    }

    private List<District> toDistrictList(List<Cluster> clusters){
        return clusters.stream().map(District.class::cast).collect(Collectors.toList());
    }

    public State getNewState() {
        return newState;
    }

    public RunStatistics makeRunStatistics(){
        RunStatistics runStatistics = new RunStatistics();
        return runStatistics;
    }

    public AlgorithmRequestModel getAlgorithmRequestModel() {
        return algorithmRequestModel;
    }

    public boolean isFinished(){
        return status == AlgorithmStatus.COMPLETED;
    }

    public void initializeCluster(){
        //TODO:Link Database and algorithm
    }

    public boolean finalizeAlgorithm(){
        return false;
    }


    public boolean pause(){
        return false;
    }


    public Set<District> getMejorityMinorityDistrict(){
        return null;
    }


    public int calcuateObjectiveFunctionValue(){
        return 0;
    }

    public boolean isInitialized(){
        return newState != null && status == AlgorithmStatus.INITIALIZED;
    }


    public Map<District, String> calculateRaceDeinsity(){
        return null;
    }


    private void assertInitialization(){
        if(!isInitialized())
            throw new NotInitializedException(Utility.notInitializedExceptionMessage);
    }


}
