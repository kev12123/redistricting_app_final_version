package com.appRestful.api.model.request;

import com.appRestful.api.enums.Demographic;
import com.appRestful.api.enums.StatesSelected;

public class AlgorithmRequestModel {
	
	    private int goalDistricts;
	    private long targetPopulation;
	    private double majorityMinorityMinPercentage;
	    private double majorityMinorityMaxPercentage;
	    private Demographic majorityMinorityDemographic;
	    private int goalMajorityMinorityDistricts;
	    private StatesSelected stateSelected;
	    
		public int getGoalDistricts() {
			return goalDistricts;
		}
		public void setGoalDistricts(int goalDistricts) {
			this.goalDistricts = goalDistricts;
		}
		public long getTargetPopulation() {
			return targetPopulation;
		}
		public void setTargetPopulation(long targetPopulation) {
			this.targetPopulation = targetPopulation;
		}
		public double getMajorityMinorityMinPercentage() {
			return majorityMinorityMinPercentage;
		}
		public void setMajorityMinorityMinPercentage(double majorityMinorityMinPercentage) {
			this.majorityMinorityMinPercentage = majorityMinorityMinPercentage;
		}
		public double getMajorityMinorityMaxPercentage() {
			return majorityMinorityMaxPercentage;
		}
		public void setMajorityMinorityMaxPercentage(double majorityMinorityMaxPercentage) {
			this.majorityMinorityMaxPercentage = majorityMinorityMaxPercentage;
		}
		public Demographic getMajorityMinorityDemographic() {
			return majorityMinorityDemographic;
		}
		public void setMajorityMinorityDemographic(Demographic majorityMinorityDemographic) {
			this.majorityMinorityDemographic = majorityMinorityDemographic;
		}
		public int getGoalMajorityMinorityDistricts() {
			return goalMajorityMinorityDistricts;
		}
		public void setGoalMajorityMinorityDistricts(int goalMajorityMinorityDistricts) {
			this.goalMajorityMinorityDistricts = goalMajorityMinorityDistricts;
		}
	
}
