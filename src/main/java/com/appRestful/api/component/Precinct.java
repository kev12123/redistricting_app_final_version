package com.appRestful.api.component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.appRestful.api.component.data.Data;
import com.appRestful.api.component.data.NoDataException;
import com.appRestful.api.utility.Utility;
import com.google.common.collect.Sets;

public class Precinct {
    private String precinctID;
    private Data data;
    private Cluster parentCluster;
    private Set<Precinct> allNeighbors;
    private boolean isOnBorder;
    private int borderDegree;
    private boolean isBorderInitialized;

    public Precinct(String precinctID) {
        this.precinctID = precinctID;
        this.allNeighbors = new HashSet<>();
    }

    public Precinct(String precinctID,Data data){
        this.precinctID = precinctID;
        this.data = data;
        this.allNeighbors = new HashSet<>();
    }

    private Precinct(String precinctID,Data data,Set<Precinct> allNeighbors,boolean isOnBorder, int borderDegree){
        this(precinctID,data);
        this.allNeighbors = allNeighbors;
        this.isOnBorder = isOnBorder;
        this.borderDegree = borderDegree;
        this.isBorderInitialized = false;
    }

    public void addMutualNeighbor(Precinct precinct){
        if(!isBorderInitialized && !allNeighbors.contains(precinct)) {
            allNeighbors.add(precinct);
            precinct.allNeighbors.add(this);
        }
    }

    //has imutable border once initialized.
    public void initilizeBorder(){
        borderDegree = allNeighbors.size();
        isOnBorder = borderDegree > Utility.enclosed;
        isBorderInitialized = Utility.borderInitialized;
    }

    public String getPrecinctID() {
        return precinctID;
    }

    public void initializeData(Data data){
        if(this.data == null)
            this.data = data;
        else
            throw new IllegalArgumentException(Utility.dataAlreadyPresentExceptionMessage);
    }

    public Data getPrecinctData() {
        if(data != null)
            return data;
        else
            throw new NoDataException(Utility.noDataExceptionMessage);
    }

    public boolean isOnBorder() {
        return isOnBorder;
    }

    public void setOnBorder(boolean isOnBorder) {
        borderDegree  += (isOnBorder) ? Utility.borderExpand : Utility.borderContract;
        this.isOnBorder = borderDegree > Utility.enclosed;
    }

    public Object clone(){
        Precinct clonePrecinct = new Precinct(precinctID,(Data)data.clone(),allNeighbors,isOnBorder,borderDegree);
        clonePrecinct.isBorderInitialized = this.isBorderInitialized;
        return clonePrecinct;
    }

    public Cluster getParentCluster(){
        return parentCluster;
    }

    public District getParentDistrict() throws InvalidDistrictException{
        try {
            return (District) parentCluster;
        }catch (ClassCastException e){
            throw new InvalidDistrictException(Utility.invalidDistrictException);
        }
    }

    public Set<Precinct> getAllNeighbors(){
        return allNeighbors;
    }

    public void setParentCluster(Cluster parentCluster) {
        Cluster oldParent = this.parentCluster;
        this.parentCluster = parentCluster;
        System.out.printf("old Parent: %s :: new Parent: %s\n",(oldParent != null) ? oldParent.getPrimaryId() : "null",(this.parentCluster != null) ? this.parentCluster.getPrimaryId() : "null");
    }

    //Same as parentCluster used for legibility
    public void setParentDistrict(District parentDistrict) {
        this.parentCluster = parentDistrict;
    }

    public void resetNeighbors(Map<String, Precinct> newPrecincts){
        Set<Precinct> newNeighbors = new HashSet<>();
        for(Precinct neighbor : allNeighbors){
            Precinct newNeighbor = newPrecincts.get(neighbor.getPrecinctID());
            newNeighbors.add(newNeighbor);
        }
        allNeighbors = newNeighbors;
    }

    public boolean equals(Object o){
        if(o == null || !(o instanceof Precinct))
            return false;

        Precinct candidate = (Precinct)o;
        return this.precinctID.equals(candidate.precinctID);
    }

    public int hashCode(){
        return precinctID.hashCode();
    }

    public Set<Precinct> getAllNeighborsInParentCluster(){
        Set<Precinct> neighborsInCluster = new HashSet<>();
        for(Precinct neighbor : this.getAllNeighbors()){
            if( this != neighbor && this.parentCluster.getPrimaryId().equals(neighbor.getParentCluster().getPrimaryId())) {
                neighborsInCluster.add(neighbor);
            }
        }
        return neighborsInCluster;
    }

    public Set<Precinct> getAllNeighborsNotInParentCluster(){
        Set<Precinct> precinctsInCluster = getAllNeighborsInParentCluster();
        return Sets.difference(allNeighbors,precinctsInCluster);
    }

}
