package com.appRestful.api.component.data;

import java.util.HashMap;
import java.util.Map;

import com.appRestful.api.enums.Measure;
import com.appRestful.api.utility.Utility;

public class Objective {
    private Map<Measure,Double> measures;

    public Objective() {
        measures = new HashMap<>();
        for(Measure measure:Measure.values()){
            measures.put(measure, Utility.noWeight);
        }
    }

    public void changeMeasure(Measure measure, double newValue){
        measures.put(measure,newValue);
    }

    public void updateMeasure(Measure measure, double valueChange){
        measures.put(measure,measures.get(measure) + valueChange);
    }

    public Object clone(){
        Objective objectiveClone = new Objective();
        objectiveClone.measures = new HashMap<>();

        for(Measure measure: measures.keySet()){
            objectiveClone.measures.put(measure,this.measures.get(measure));
        }

        return objectiveClone;
    }

}
