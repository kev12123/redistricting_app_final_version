package com.appRestful.api.component;

import java.util.Map;
import java.util.function.Supplier;

import com.appRestful.api.component.data.Data;
import com.appRestful.api.model.request.AlgorithmRequestModel;


public class District extends Cluster {

    public District(Class<? extends Edge> edgeClass) {
        super(edgeClass);
    }

    public District(Class<? extends Edge> edgeClass,Precinct firstPrecinct) {
        super(edgeClass,firstPrecinct);
    }

    public District(Supplier<Precinct> vertexSupplier, Supplier<Edge> edgeSupplier, boolean weighted) {
        super(vertexSupplier, edgeSupplier, weighted);
    }

    public Data getDistrictData(){
        return this.getClusterData();
    }

    public void addPrecinct(Precinct precinct, AlgorithmRequestModel algorithmRequestModel){
        super.addPrecinct(precinct, algorithmRequestModel);

    }

    @Override
    public Object clone() {
        District district = new District(Edge.class);
        district.primaryId = this.primaryId;
        district.data = (Data) data.clone();
        Map<Precinct,Precinct> cloneMapping = clonePrecincts(district);
        cloneEdges(district,cloneMapping);
        cloneBorderPrecincts(district,cloneMapping);
        System.out.println("District " + district.getPrimaryId() + " cloned");
        return district;
    }


    @Override
    public String toString() {
        return "District/" + super.toString();
    }
}
