package com.appRestful.api.component.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.appRestful.api.enums.Demographic;
import com.appRestful.api.enums.PoliticalParty;
import com.appRestful.api.model.request.AlgorithmRequestModel;

public class Demography {
    private long totalPopulation;
    private Map<Demographic, Long> populationByDemographic;
    private Map<PoliticalParty, Long> populationByParty;
    private boolean hasMajorityMinorityPopulation;
    private boolean wasMajorityMinority;


    public Demography(long populationTotal, Map<Demographic, Long> populationByRace, Map<PoliticalParty, Long> populationByParty, AlgorithmRequestModel algorithmRequestModel) {
        this.totalPopulation = populationTotal;
        this.populationByDemographic = populationByRace;
        this.populationByParty = populationByParty;
        setMajorityMinorityStatus(algorithmRequestModel);
    }

    public Demography(Map<Demographic, Long> populationByRace, Map<PoliticalParty, Long> populationByParty, AlgorithmRequestModel algorithmRequestModel) {
        this.totalPopulation = 0;
        this.populationByDemographic = populationByRace;
        this.populationByParty = populationByParty;

        for(Demographic demographic : populationByRace.keySet()){
            totalPopulation += populationByRace.get(demographic);
        }
        for(PoliticalParty politicalParty : populationByParty.keySet()){
            totalPopulation += populationByParty.get(politicalParty);
        }
        setMajorityMinorityStatus(algorithmRequestModel);
    }

    public Demography(){
        this.totalPopulation = 0;
        populationByDemographic = new HashMap<>();
        populationByParty = new HashMap<>();
    }

    private Demography(long populationTotal,Map<Demographic, Long> populationByRace, Map<PoliticalParty, Long> populationByParty,
                       boolean hasMajorityMinorityPopulation, boolean wasMajorityMinority){
        this.totalPopulation = populationTotal;
        this.populationByDemographic = populationByRace;
        this.populationByParty = populationByParty;
        this.hasMajorityMinorityPopulation = hasMajorityMinorityPopulation;
        this.wasMajorityMinority = this.isMajorityMinority();

    }

    public Long getTotalPopulation() {
        return totalPopulation;
    }

    public Long getDemographicPopulation(Demographic demographic) {
        return populationByDemographic.get(demographic);
    }

    public Long getPopulationByParty(PoliticalParty politicalParty) {
        return populationByParty.get(politicalParty);
    }

    public void changeRacePopulation(Demographic demographic, long newPopulation, AlgorithmRequestModel algorithmRequestModel){
        this.populationByDemographic.put(demographic,newPopulation);
        updateTotalPopulation();
        setMajorityMinorityStatus(algorithmRequestModel);
    }

    public void changePartyPopulation(PoliticalParty politicalParty, long newPopulation){
        this.populationByParty.put(politicalParty,newPopulation);
        updateTotalPopulation();
    }

    public void addToRacePopulation(Demographic demographic, long additionalPopulation, AlgorithmRequestModel algorithmRequestModel){
        long currentPopulation = 0;
        if(this.hasPopulationOfDemographic(demographic)) currentPopulation = this.populationByDemographic.get(demographic);
        this.populationByDemographic.put(demographic,currentPopulation + additionalPopulation);
        updateTotalPopulation();
        setMajorityMinorityStatus(algorithmRequestModel);
    }

    public void addToPartyPopulation(PoliticalParty politicalParty, long additionalPopulation){
        long currentPopulation = 0;
        if(this.hasPopulationOfParty(politicalParty)) currentPopulation = this.populationByParty.get(politicalParty);
        this.populationByParty.put(politicalParty,currentPopulation + additionalPopulation);
        updateTotalPopulation();
    }

    public double getPartyPopulationPercentage(PoliticalParty politicalParty){
        return (double)populationByParty.get(politicalParty) / totalPopulation;
    }

    public double getRacePopulationPercentage(Demographic demographic){
        return (double)populationByDemographic.get(demographic) / totalPopulation;
    }

    public boolean hasPopulationOfDemographic(Demographic demographic){
        return populationByDemographic.containsKey(demographic);
    }

    public boolean hasPopulationOfParty(PoliticalParty politicalParty){
        return populationByParty.containsKey(politicalParty);
    }

    public Set<Demographic> getAllRacesPresent(){
        return populationByDemographic.keySet();
    }

    public Set<PoliticalParty> getAllPartiesPresent(){
        return populationByParty.keySet();
    }

    public boolean isMajorityMinority(){
          return hasMajorityMinorityPopulation;
    }

    public boolean wasMajorityMinority(){
        return wasMajorityMinority;
    }

    private void setMajorityMinorityStatus(AlgorithmRequestModel algorithmRequestModel){
        Demographic majorityMinorityDemographic = algorithmRequestModel.getMajorityMinorityDemographic();
        double lowerBound = algorithmRequestModel.getMajorityMinorityMinPercentage();
        double upperBound = algorithmRequestModel.getMajorityMinorityMaxPercentage();
        double racePopulationPercentage = getRacePopulationPercentage(majorityMinorityDemographic);
        wasMajorityMinority = hasMajorityMinorityPopulation;
        hasMajorityMinorityPopulation = lowerBound <= racePopulationPercentage && racePopulationPercentage <= upperBound;
    }

    public Object clone(){
        Map<PoliticalParty,Long> populationByPartyClone = new HashMap<>();
        Map<Demographic,Long> populationByDemographyClone = new HashMap<>();
        for(PoliticalParty party : populationByParty.keySet()){
            populationByPartyClone.put(party,populationByParty.get(party).longValue());
        }
        for(Demographic demographic:populationByDemographic.keySet()){
            populationByDemographyClone.put(demographic,populationByDemographic.get(demographic).longValue());
        }
        return new Demography(totalPopulation,populationByDemographyClone,populationByPartyClone,
                this.hasMajorityMinorityPopulation, wasMajorityMinority);
    }

    public boolean updateTotalPopulation(){
        long demographicPopulation = 0;
        long partyPopulation = 0;
        for(Demographic demographic: getAllRacesPresent()){
            demographicPopulation += getDemographicPopulation(demographic);
        }
        for(PoliticalParty party:getAllPartiesPresent()){
            partyPopulation += getPopulationByParty(party);
        }
        totalPopulation = Math.max(demographicPopulation,partyPopulation);

        return demographicPopulation == partyPopulation;
    }

    @Override
    public String toString() {
        return "Demography{" +
                "totalPopulation=" + totalPopulation +
                ", populationByDemographic=" + populationByDemographic +
                ", populationByParty=" + populationByParty +
                ", hasMajorityMinorityPopulation=" + hasMajorityMinorityPopulation +
                ", wasMajorityMinority=" + wasMajorityMinority +
                '}';
    }
}