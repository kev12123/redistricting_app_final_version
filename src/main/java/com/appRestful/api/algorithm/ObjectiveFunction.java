package com.appRestful.api.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.appRestful.api.component.Cluster;
import com.appRestful.api.component.Precinct;
import com.appRestful.api.component.State;
import com.appRestful.api.component.data.Data;
import com.appRestful.api.component.data.Demography;
import com.appRestful.api.component.data.Geography;
import com.appRestful.api.component.data.Result;
import com.appRestful.api.enums.Measure;
import com.appRestful.api.enums.PoliticalParty;
import com.appRestful.api.enums.WeightCategory;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.utility.PoliticalVoteComparator;
import com.appRestful.api.utility.Utility;

public class ObjectiveFunction {
    private Map<Measure, Double> weights;
    private Map<Measure,Double> values;
    private AlgorithmRequestModel algorithmRequestModel;
    private State state;

    public ObjectiveFunction(Map<Measure, Double> weights, AlgorithmRequestModel algorithmRequestModel, State state) {
        this.weights = new HashMap<>();
        this.values = new HashMap<>();
        for(Measure measure:weights.keySet()){
            this.weights.put(measure,weights.get(measure));
        }
        for(Measure measure:Measure.values()){
            values.put(measure, Utility.noWeight);
        }

        this.algorithmRequestModel = algorithmRequestModel;
        this.state = state;
    }

    public double getTotalValue(){
        double totalValue  = 0;

        for(Measure measure: getMeasuresPresent()){
            totalValue += weights.get(measure) * values.get(measure);
        }

        return totalValue;
    }

    public void updateAllMeasures(Data data){
        for(Measure measure: Measure.values()){
            updateMeasure(measure,data);
        }
    }

    public void updateMeasure(Measure measure, Data data){
        switch (measure){
            case POPULATION_EQUALITY:
                calculatePopulationEqualityValue(data);
                break;
            case LENGTH_WIDTH_RATIO:
                calculateLengthWidthRatioValue(data);
                break;
            case POLSBY_POPPER:
                calculatePolsbyPopperValue(data);
                break;
            case EFFICENCY_GAP:
                calculateEfficiencyGapValue(data);
                break;
            case MEAN_MEDIAN:
                calculateMeanMedianValue(data);
                break;
            case CONVEX_HULL:
                calculateConvexHullValue(data);
                break;
            case EDGE_CUT:
                calculateEdgeCutValue(data);
                break;
            case MAJORITY_MINORITY:
                calclateMajorityMinorityEqualityValue(data);
                break;

            default:
                break;
        }
    }

    private void calculateEdgeCutValue(Data data){
        Geography geography = data.getGeographicData();
        double edgeCutScore = ((double) geography.getInternalEdgeCount()) / (geography.getInternalEdgeCount() + geography.getExternalEdgeCount());
        values.put(Measure.EDGE_CUT,edgeCutScore);
    }
    private void calculateConvexHullValue(Data data){
        Geography geography = data.getGeographicData();
        double convexHullScore = geography.getArea() / geography.getConvexHullArea();
        values.put(Measure.CONVEX_HULL,convexHullScore);
    }
    private void calculateMeanMedianValue(Data data){
        double medianVoteShare = getMedianVoteShare();
        double meanVoteShare = getMeanVoteShare();
        double meanMedianDifference = meanVoteShare - medianVoteShare;
        double meanMedianRating =  1 - (meanMedianDifference/(meanVoteShare + medianVoteShare));
        values.put(Measure.MEAN_MEDIAN,meanMedianRating);
    }

    private double getMedianVoteShare(){
        List<Cluster> clusters = new ArrayList<>(state.getClusters());
        clusters.sort(new PoliticalVoteComparator());
        Cluster medianCluster = clusters.get(clusters.size()/2);
        return medianCluster.getClusterData().getDemographyData().getPopulationByParty(PoliticalParty.DEMOCRAT);
    }

    private double getMeanVoteShare(){
        List<Cluster> clusters = new ArrayList<>(state.getClusters());
        double mean = 0;
        for(Cluster cluster : clusters){
            mean += cluster.getClusterData().getDemographyData().getPopulationByParty(PoliticalParty.DEMOCRAT);
        }
        return mean/((double) clusters.size());
    }

    private void calculateEfficiencyGapValue(Data data){
        Iterator<Integer> it =  data.getElectionResultsByYear().getAllYearsPresent().iterator();
        while (it.hasNext()){
            int year = it.next();
            Result result = data.getElectionResultsByYear().getResultFromYear(year);
            long totalVotes = result.getDemocraticVotes() + result.getRepublicanVotes();
            long neededToWin = totalVotes / 2;
            long netWastedVotes = 0;
            if (result.getDemocraticVotes() > neededToWin){
                long netDWastedVotes = result.getDemocraticVotes()  - neededToWin;
                netWastedVotes = Math.abs(netDWastedVotes - result.getRepublicanVotes());
            }else{
                long netRWastedVotes =  result.getRepublicanVotes()  - neededToWin;
                netWastedVotes = Math.abs(netRWastedVotes - result.getDemocraticVotes());
            }
            values.put(Measure.EFFICENCY_GAP, (double) netWastedVotes / totalVotes);
        }
    }

    private void calculatePolsbyPopperValue(Data data){
        double polsbyPopperValue = 4 * Math.PI * data.getGeographicData().getArea() / Math.pow(data.getGeographicData().getPerimeter(), 2);
        values.put(Measure.POLSBY_POPPER, polsbyPopperValue);
    }
    private void calculatePopulationEqualityValue(Data data){
        double percentage = Math.abs(algorithmRequestModel.getTargetPopulation() - data.getDemographyData().getTotalPopulation()) / algorithmRequestModel.getTargetPopulation();
        percentage *= 100;
        if (Math.abs(algorithmRequestModel.getAllowedPopulationDeviation() - percentage) < 10) values.put(Measure.POPULATION_EQUALITY, Utility.fullWeight);
        else if (Math.abs(algorithmRequestModel.getAllowedPopulationDeviation() - percentage) < 20) values.put(Measure.POPULATION_EQUALITY, Utility.halfWeight);
        else if (Math.abs(algorithmRequestModel.getAllowedPopulationDeviation() - percentage) < 30) values.put(Measure.POPULATION_EQUALITY, 0.35);
        else values.put(Measure.POPULATION_EQUALITY, 0.25);
    }

    private void calclateMajorityMinorityEqualityValue(Data data){
        Demography demography = data.getDemographyData();
        if(demography.isMajorityMinority())
            weights.put(Measure.MAJORITY_MINORITY,Utility.fullWeight);
        else if(demography.wasMajorityMinority())
            weights.put(Measure.MAJORITY_MINORITY,Utility.noWeight);
        else
            weights.put(Measure.MAJORITY_MINORITY,Utility.halfWeight);
    }



    private void calculateLengthWidthRatioValue(Data data){
        double  lengthWidthRatio = data.getGeographicData().getLongestAxis() / data.getGeographicData().getMaxWidthPerpendicular();
        values.put(Measure.LENGTH_WIDTH_RATIO, lengthWidthRatio);
    }

    public double getWeight(Measure measure) {
        return weights.get(measure);
    }

    public double getValue(Measure measure) {
        return values.get(measure);
    }

    public void changeWeight(Measure measure, double newWeight){
        weights.put(measure,newWeight);
    }

    public void updateWeight(Measure measure, double weightChange){
        weights.put(measure,weights.get(measure) + weightChange);
    }

    public void changeValue(Measure measure, double newValue){
        values.put(measure,newValue);
    }

    public void updateValue(Measure measure, double valueChange){
        values.put(measure,values.get(measure) + valueChange);
    }

    public Set<Measure> getMeasuresPresent(){
        return weights.keySet();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}