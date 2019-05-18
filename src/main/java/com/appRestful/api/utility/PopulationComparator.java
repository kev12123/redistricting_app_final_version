package com.appRestful.api.utility;

import java.util.Comparator;

import com.appRestful.api.component.Cluster;

public class PopulationComparator implements Comparator<Cluster> {
    @Override
    public int compare(Cluster c1, Cluster c2) {
        return Long.compare(c1.getClusterData().getDemographyData().getTotalPopulation(),
                c2.getClusterData().getDemographyData().getTotalPopulation());
    }
}
