package com.appRestful.api.component.data;

public class Geography {
	
    private String county;

    public Geography(String county) {
        this.county = county;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Object clone(){
        return new Geography(county);
    }

    @Override
    public String toString() {
        return "Geography{" +
                "county='" + county + '\'' +
                '}';
    }
}