package com.appRestful.api.algorithm;

import java.util.Map;

import com.appRestful.api.component.Precinct;
import com.appRestful.api.enums.WeightCategory;

public class ObjectiveFunction {
	
    Map<WeightCategory, Integer> weights;
    double locationIndexWeight;
    double boundaryIndexWeight;
    double geographicIndexWeight;
    double racialIndexWeight;
    double politicalIndexWeight;
    double specialInterestIndexWeight;
    double populationIndexWeight;
    double clusterIndexWeight;

    public double calcObjectiveFunction(){

        return 0;
    }


    public double getWeight(WeightCategory weightCategory){

        return 0;
    }

    public double clacLocationIndex(Precinct cluster1, Precinct cluster2){

        return 0;
    }

    public double calcBaundryIndex(Precinct cluster1, Precinct cluster2){

        return 0;
    }

    public double calcGeographicIndex(Precinct cluster1, Precinct cluster2){

        return 0;
    }

    public double calcRacialIndex(Precinct cluster1, Precinct cluster2){

        return 0;
    }


    public double calcPoliticalIndexc1(Precinct cluster1, Precinct cluster2){

        return 0;
    }

    public double calcSpecialInterestIndex(Precinct cluster1, Precinct cluster2){

        return 0;
    }

    public double calcPopulationIndex(Precinct cluster1, Precinct cluster2){

        return 0;
    }


    public double calcClusterIndex(Precinct cluster1, Precinct cluster2){

        return 0;
    }




}
