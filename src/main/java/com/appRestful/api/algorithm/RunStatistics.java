package com.appRestful.api.algorithm;

import com.appRestful.api.utility.Utility;

import net.minidev.json.JSONObject;

public class RunStatistics {
    int runNumber = 0;
    double efficiencyGapValue;
    double stephaMcGeeValue;
    double paulsbyPoperValue;
    int seatsByParty;
    ObjectiveFunction objectiveFunction;

    public RunStatistics(){
        calcualteEfficiencyGapValue();
        calcualteStephaMcGeeValue();
        calcualtePaulsbyPoperValue();
    }

    public JSONObject getStatistics(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Utility.EFFICIENCY_GAP_VALUE, efficiencyGapValue);
        jsonObject.put(Utility.STEPHAMCGEE_VALUE, stephaMcGeeValue);
        jsonObject.put(Utility.PAULSBY_POPPER_VALUE, paulsbyPoperValue);
        jsonObject.put(Utility.SEATS_BY_PARTY, seatsByParty);
        jsonObject.put(Utility.OBJECTIVE_FUNCTION, objectiveFunction);

        return jsonObject;
    }

    public double calcualteEfficiencyGapValue(){

        return 0;
    }

    public double calcualteStephaMcGeeValue(){

        return 0;
    }

    public double calcualtePaulsbyPoperValue(){

        return 0;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public double getEfficiencyGapValue() {
        return efficiencyGapValue;
    }

    public double getStephaMcGeeValue() {
        return stephaMcGeeValue;
    }

    public double getPaulsbyPoperValue() {
        return paulsbyPoperValue;
    }

    public int getSeatsByParty() {
        return seatsByParty;
    }

    public ObjectiveFunction getObjectiveFunction() {
        return objectiveFunction;
    }



}



