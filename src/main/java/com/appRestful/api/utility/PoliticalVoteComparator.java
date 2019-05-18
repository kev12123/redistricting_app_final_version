package com.appRestful.api.utility;

import java.util.Comparator;

import com.appRestful.api.component.Cluster;
import com.appRestful.api.component.data.Demography;
import com.appRestful.api.enums.PoliticalParty;

public class PoliticalVoteComparator implements Comparator<Cluster> {
    @Override
    public int compare(Cluster c1, Cluster c2) {
        Demography demography1 = c1.getClusterData().getDemographyData();
        Demography demography2 = c2.getClusterData().getDemographyData();
        double politicalPercentage1 = demography1.getPopulationByParty(PoliticalParty.DEMOCRAT);
        double politicalPercentage2 = demography2.getPopulationByParty(PoliticalParty.DEMOCRAT);
        return Double.compare(politicalPercentage1,politicalPercentage2);
    }
}

