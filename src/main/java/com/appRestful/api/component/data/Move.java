package com.appRestful.api.component.data;

import java.util.ArrayList;
import java.util.List;

import com.appRestful.api.algorithm.ObjectiveFunction;
import com.appRestful.api.component.District;
import com.appRestful.api.component.Precinct;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.model.request.RequestQueue;
import com.appRestful.api.model.response.DataResponse;
import com.appRestful.api.utility.Utility;

public class Move {
    private Precinct precinct;
    private District fromDistrict;
    private District toDistrict;
    private AlgorithmRequestModel algorithmRequestModel;
    private ObjectiveFunction objectiveFunction;

    public Move(Precinct precinct, District fromDistrict, District toDistrict, AlgorithmRequestModel algorithmRequestModel, ObjectiveFunction objectiveFunction){
        this.precinct = precinct;
        this.fromDistrict = fromDistrict;
        this.toDistrict = toDistrict;
        this.algorithmRequestModel = algorithmRequestModel;
        this.objectiveFunction = objectiveFunction;
    }


    public void doMove(){
        fromDistrict.removePrecinct(precinct, algorithmRequestModel);
        toDistrict.addPrecinct(precinct, algorithmRequestModel);
    }

    public void undoMove(){
        toDistrict.removePrecinct(precinct, algorithmRequestModel);
        fromDistrict.addPrecinct(precinct, algorithmRequestModel);
    }

    public boolean applyMove(){
        doMove();
        boolean isMoveValid = isMoveValid(calculateChange());
        if(!isMoveValid)
            undoMove();
        else
        	RequestQueue.requestQueue.add(createDataResponse());
        return isMoveValid;
    }
    
    private DataResponse createDataResponse(){
    	List<String> info = new ArrayList();
    	info.add(fromDistrict.getPrimaryId());
    	info.add(toDistrict.getPrimaryId());
    	info.add(precinct.getPrecinctID());
    	DataResponse resp = new DataResponse();
    	resp.setDistrictData(info);
    	resp.setStage(Utility.phaseTwoResponse);
    	return resp;
    }
    
    private double calculateChange(){
        double oldValue = objectiveFunction.getTotalValue();
        objectiveFunction.updateAllMeasures(fromDistrict.getClusterData());
        objectiveFunction.updateAllMeasures(toDistrict.getClusterData());
        double newValue = objectiveFunction.getTotalValue();
        return newValue - oldValue;
    }

    private boolean isMoveValid(double objectiveFunctionChange){
        return objectiveFunctionChange > Utility.noChange;
    }


}