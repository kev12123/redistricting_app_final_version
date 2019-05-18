package com.appRestful.api.component.data;

public class Data {
    private Demography demography;
    private ElectionResults electionResults;
    private Geography geography;

    public Data(Demography demography, ElectionResults electionResults,Geography geography) {
        this.demography = demography;
        this.electionResults = electionResults;
        this.geography = geography;
    }

    public Data(String county){
        this.demography = new Demography();
        this.geography = new Geography(county);
        this.electionResults = new ElectionResults();
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
        return new Data(demographyClone,electionResultsClone,geographyClone);
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