package com.appRestful.api.model.response;

import javax.persistence.Column;

public class PopulationResp {
	

	
	private Long blackPopulation;
	
	private Long whitePopulation;
	
	private Long otherPopulation;
	
	private Long pacificIslanderPopulation;
	
	private Long asianPopulation;
	
	private Long alaskanNativeAmericanPopulation;
	
	public PopulationResp() {}

	public Long getBlackPopulation() {
		return blackPopulation;
	}
	
	public PopulationResp(Long blackPopulation, Long whitePopulation, Long otherPopulation,
			Long pacificIslanderPopulation, Long asianPopulation, Long alaskanNativeAmericanPopulation) {
		super();
		this.blackPopulation = blackPopulation;
		this.whitePopulation = whitePopulation;
		this.otherPopulation = otherPopulation;
		this.pacificIslanderPopulation = pacificIslanderPopulation;
		this.asianPopulation = asianPopulation;
		this.alaskanNativeAmericanPopulation = alaskanNativeAmericanPopulation;
	}


	public void setBlackPopulation(Long blackPopulation) {
		this.blackPopulation = blackPopulation;
	}

	public Long getWhitePopulation() {
		return whitePopulation;
	}

	public void setWhitePopulation(Long whitePopulation) {
		this.whitePopulation = whitePopulation;
	}

	public Long getOtherPopulation() {
		return otherPopulation;
	}

	public void setOtherPopulation(Long otherPopulation) {
		this.otherPopulation = otherPopulation;
	}

	public Long getPacificIslanderPopulation() {
		return pacificIslanderPopulation;
	}

	public void setPacificIslanderPopulation(Long pacificIslanderPopulation) {
		this.pacificIslanderPopulation = pacificIslanderPopulation;
	}

	public Long getAsianPopulation() {
		return asianPopulation;
	}

	public void setAsianPopulation(Long asianPopulation) {
		this.asianPopulation = asianPopulation;
	}

	public Long getAlaskanNativeAmericanPopulation() {
		return alaskanNativeAmericanPopulation;
	}

	public void setAlaskanNativeAmericanPopulation(Long alaskanNativeAmericanPopulation) {
		this.alaskanNativeAmericanPopulation = alaskanNativeAmericanPopulation;
	}

}
