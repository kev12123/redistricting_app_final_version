package com.appRestful.api.component;

import com.appRestful.api.component.data.Data;
import com.appRestful.api.component.data.NoDataException;
import com.appRestful.api.utility.Utility;

public class Precinct {
    private String precinctID;
    private Data data;

    public Precinct(String precinctID) {
        this.precinctID = precinctID;

    }

    public Precinct(String precinctID,Data data){
        this.precinctID = precinctID;
        this.data = data;
    }

    public String getPrecinctID() {
        return precinctID;
    }

    public void initilizeData(Data data){
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

    public Object clone(){
        return new Precinct(precinctID,(Data)data.clone());
    }
}
