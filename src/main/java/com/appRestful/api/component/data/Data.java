package com.appRestful.api.component.data;

import org.locationtech.jts.geom.Coordinate;

public class Data {
    private Demography demography;
    private ElectionResults electionResults;
    private Geography geography;
    private Objective objective;

    public Data(Demography demography, ElectionResults electionResults,Geography geography) {
        this.demography = demography;
        this.electionResults = electionResults;
        this.geography = geography;
        this.objective = new Objective();
    }

    public Data(String county, Coordinate[] polygonBoundary){
        this.demography = new Demography();
        this.geography = new Geography(county,polygonBoundary);
        this.electionResults = new ElectionResults();
        this.objective = new Objective();
    }

    private Data(Demography demography, ElectionResults electionResults,Geography geography,Objective objective){
        this(demography,electionResults,geography);
        this.objective = objective;
    }

    public  Demography getDemographyData(){
        return demography;
    }

    public ElectionResults getElectionResultsByYear(){
        return electionResults;
    }

    public Geography getGeographicData(){
        return geography;
    }

    public Object clone(){
        Demography demographyClone = (Demography) this.demography.clone();
        ElectionResults electionResultsClone = (ElectionResults) this.electionResults.clone();
        Geography geographyClone = (Geography) this.geography.clone();
        Objective objectiveClone = (Objective) this.objective.clone();
        return new Data(demographyClone,electionResultsClone,geographyClone,objectiveClone);
    }

    @Override
    public String toString() {
        return "Data{" +
                "demography=" + demography +
                ", electionResults=" + electionResults +
                ", geography=" + geography +
                '}';
    }
}