package com.appRestful.api.algorithm;

import java.util.Map;
import java.util.Set;

import com.appRestful.api.component.District;
import com.appRestful.api.component.State;
import com.appRestful.api.enums.AlgorithmStatus;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.utility.Utility;

//TODO: Finish writing algorithm class
public class Algorithm {
    private State origState;
    private State newState;
    private AlgorithmStatus status;
    private ObjectiveFunction objectiveFunction;
    private AlgorithmRequestModel algorithmData;


    public Algorithm(State state){
        this.origState = state;
        status = AlgorithmStatus.CREATED;
    }

    public void initializeAlgorithm(ObjectiveFunction objectiveFunction, AlgorithmRequestModel algorithmData){
        if(algorithmData.getGoalDistricts() >= origState.getClusters().size())
            throw new IllegalArgumentException(Utility.districtGoalOutOfBounds);
        this.objectiveFunction = objectiveFunction;
        this.algorithmData = algorithmData;
        newState = (State) origState.clone();
        newState.assertClustersAreDistricts();
        System.out.println("here");
        status = AlgorithmStatus.INITIALIZED;
    }

    public void run(){
        assertInitialization();
        status = AlgorithmStatus.RUNNING;
        runPhaseOne();
        runPhaseTwo(objectiveFunction);
        status = AlgorithmStatus.COMPLETED;
    }

    public void runPhaseTwo(ObjectiveFunction objectiveFunction) {
        //TODO:Run Phase Two
    }

    public void runPhaseOne(){
        boolean phaseOneCompleted;
        do {
            phaseOneCompleted = runPhaseOneStep();
        }while (!phaseOneCompleted);
    }

    public boolean runPhaseOneStep(){
        newState.findCandidatePairs();
        newState.joinCandidatePairs(algorithmData);
        return newState.getClustersQuantity() == algorithmData.getGoalDistricts() || !newState.hasCandidatePairs();
    }

    public State getNewState() {
        return newState;
    }

    public RunStatistics makeRunStatistics(){
        RunStatistics runStatistics = new RunStatistics();
        return runStatistics;
    }

    public AlgorithmRequestModel getAlgorithmData() {
        return algorithmData;
    }

    public boolean isFinished(){
        return status == AlgorithmStatus.COMPLETED;
    }

    public void initializeCluster(){
        //TODO:Link Database and algorithm
    }

    public boolean isFinished(State state, int districtNumber){


        return false;
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

