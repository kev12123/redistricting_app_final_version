package com.appRestful.api.model.response;

import java.util.List;

public class DataResponse {
	
	private List<String> districtData;
	private String stage;
	private double objectiveFunctionValue;
	private double initialGerrymanderingValue;
	private double finalGerrymanderingValue;
	private long population;

	public List<String> getDistrictData() {
		return districtData;
	}

	public void setDistrictData(List<String> districtData) {
		this.districtData = districtData;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public double getObjectiveFunctionValue() {
		return objectiveFunctionValue;
	}

	public void setObjectiveFunctionValue(double objectiveFunctionValue) {
		this.objectiveFunctionValue = objectiveFunctionValue;
	}

	public double getInitialGerrymanderingValue() {
		return initialGerrymanderingValue;
	}

	public void setInitialGerrymanderingValue(double initialGerrymanderingValue) {
		this.initialGerrymanderingValue = initialGerrymanderingValue;
	}

	public double getFinalGerrymanderingValue() {
		return finalGerrymanderingValue;
	}

	public void setFinalGerrymanderingValue(double finalGerrymanderingValue) {
		this.finalGerrymanderingValue = finalGerrymanderingValue;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public String toString() {
		String s = "{" + stage + ", ";
		for(String str : districtData) {
			s += str + ", ";
		}
		s.substring(0, s.length() - 1);
		s += "}";
		return s;
	}
}
