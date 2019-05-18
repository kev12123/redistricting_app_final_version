package com.appRestful.api.component;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.graph.DefaultEdge;

import com.appRestful.api.component.data.Demography;
import com.appRestful.api.component.data.Geography;
import com.appRestful.api.enums.Demographic;
import com.appRestful.api.enums.PoliticalParty;
import com.appRestful.api.enums.WeightCategory;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.utility.Utility;

public class Edge extends DefaultEdge {

    private Map<WeightCategory, Double> weights;

    public Edge() {
        weights = new HashMap<>();
    }

    public Edge(Cluster source, Cluster destination, AlgorithmRequestModel algorithmRequestModel) {
        weights = new HashMap<>();
        for (WeightCategory weightCategory : WeightCategory.values())
            calculateJoinability(source, destination, weightCategory, algorithmRequestModel);
    }

    public void calculateJoinability(Cluster source, Cluster destination, WeightCategory weightCategory, AlgorithmRequestModel algorithmRequestModel) {
        switch (weightCategory) {
            case COUNTY:
                weights.put(WeightCategory.COUNTY, calculateCountyJoinability(source, destination));
                break;
            case POLITICAL:
                weights.put(WeightCategory.POLITICAL, calculatePoliticalJoinability(source, destination));
                break;
            case POPULATION:
                weights.put(WeightCategory.POPULATION, calculatePopulationJoinability(source, destination, algorithmRequestModel));
                break;
            case RACE:
                weights.put(WeightCategory.RACE, calculateRaceJoinability(source, destination, algorithmRequestModel));
                break;
        }
    }

    public double calculateCountyJoinability(Cluster source, Cluster destination) {
        Geography sourceGeography = source.getClusterData().getGeographicData();
        Geography destinationGeography = destination.getClusterData().getGeographicData();
        if (sourceGeography.getCounty().equals(destinationGeography.getCounty()))
            return Utility.fullWeight;
        else
            return Utility.noWeight;
    }

    private double calculatePoliticalJoinability(Cluster source, Cluster destination) {
        double maxRating = PoliticalParty.values().length;
        double DemocraticRating = calculatePoliticalRating(source, destination, PoliticalParty.DEMOCRAT);
        double RepublicanRating = calculatePoliticalRating(source, destination, PoliticalParty.REPUBLICAN);
        double finalRating = Utility.MAX_PERCENTAGE - ((DemocraticRating + RepublicanRating) / maxRating);
        return finalRating/Utility.MAX_PERCENTAGE;
    }

    private double calculatePoliticalRating(Cluster source, Cluster destination, PoliticalParty party) {
        Demography sourceDemography = source.getClusterData().getDemographyData();
        Demography destinationDemography = destination.getClusterData().getDemographyData();
        double sourcePartyPercentage = sourceDemography.getPartyPopulationPercentage(party);
        double destinationPartyPercentage = destinationDemography.getPartyPopulationPercentage(party);
        return Math.abs(destinationPartyPercentage - sourcePartyPercentage);
    }

    private double calculatePopulationJoinability(Cluster source, Cluster destination, AlgorithmRequestModel algorithmRequestModel) {
        Demography sourceDemography = source.getClusterData().getDemographyData();
        Demography destinationDemography = destination.getClusterData().getDemographyData();
        long totalPopulation = sourceDemography.getTotalPopulation() + destinationDemography.getTotalPopulation();
        return Utility.fullWeight -  ((double) totalPopulation / algorithmRequestModel.getTargetPopulation());
    }

    private double calculateRaceJoinability(Cluster source, Cluster destination, AlgorithmRequestModel algorithmRequestModel) {
        Demography sourceDemography = source.getClusterData().getDemographyData();
        Demography destinationDemography = destination.getClusterData().getDemographyData();
        double mergedPercentage = calculateMergedPercentage(source, destination, algorithmRequestModel);
        double lowerBound = algorithmRequestModel.getMajorityMinorityMinPercentage();
        double upperBound = algorithmRequestModel.getMajorityMinorityMaxPercentage();
        boolean isNowMajorityMinority = lowerBound <= mergedPercentage && mergedPercentage <= upperBound;
        if (sourceDemography.isMajorityMinority() || destinationDemography.isMajorityMinority())
            return (isNowMajorityMinority) ? Utility.fullWeight : Utility.noWeight;
        else
            return (isNowMajorityMinority) ? Utility.fullWeight : Utility.halfWeight;
    }

    private double calculateMergedPercentage(Cluster source, Cluster destination, AlgorithmRequestModel algorithmRequestModel) {
        Demography sourceDemography = source.getClusterData().getDemographyData();
        Demography destinationDemography = destination.getClusterData().getDemographyData();
        Demographic majorityMinorityDemographic = algorithmRequestModel.getMajorityMinorityDemographic();
        long sourceRacePopulation = sourceDemography.getDemographicPopulation(majorityMinorityDemographic);
        long destinationRacePopulation = destinationDemography.getDemographicPopulation(majorityMinorityDemographic);
        long mergedRacePopulations = sourceRacePopulation + destinationRacePopulation;
        long mergedTotalPopulation = sourceDemography.getTotalPopulation() + destinationDemography.getTotalPopulation();
        return (double)mergedRacePopulations / mergedTotalPopulation;
    }

    public double getWeight(WeightCategory weightCategory) {
        return weights.get(weightCategory);
    }

    public Object clone(){
        Edge edge = new Edge();
        for(WeightCategory category:this.weights.keySet()){
            edge.weights.put(category,this.weights.get(category));
        }
        return edge;
    }
}
