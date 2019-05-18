package com.appRestful.api.utility;

import java.util.Comparator;

import com.appRestful.api.component.Edge;
import com.appRestful.api.enums.WeightCategory;

public class JoinabilityComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge e1, Edge e2) {
        double joinTotal1 = 0;
        double joinTotal2 = 0;
        for(WeightCategory weightCategory: WeightCategory.values()){
            joinTotal1 += e1.getWeight(weightCategory);
            joinTotal2 += e2.getWeight(weightCategory);
        }

        return Double.compare(joinTotal1,joinTotal2);
    }
}