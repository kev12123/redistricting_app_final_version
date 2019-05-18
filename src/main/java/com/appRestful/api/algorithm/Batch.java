package com.appRestful.api.algorithm;

import java.util.List;
import java.util.Map;

import com.appRestful.api.component.State;

public class Batch {
	
	static Map<State, RunStatistics> runStats;
    static boolean stopBatch = false;

    public static void startBatch(Algorithm algorithm, ObjectiveFunction objectiveFunction){
        algorithm.initializeAlgorithm(objectiveFunction,algorithm.getAlgorithmData());
        while(!algorithm.isFinished()){
            try {
                if (stopBatch) return;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //TODO proper handling
            }
        }
        runStats.put(algorithm.getNewState(), algorithm.makeRunStatistics());
    }


    public static Map<State, RunStatistics> generateBatchStatistics(List<ObjectiveFunction> objectiveFunctions, State state){
        for (ObjectiveFunction objectiveFunction : objectiveFunctions){
            if (!stopBatch) startBatch(new Algorithm(state), objectiveFunction);
            else return null;
        }

        return  runStats;
    }


    public int stopBatch(){
        stopBatch  = true;
        return 0;
    }
}
