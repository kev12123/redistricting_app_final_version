package com.appRestful.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Population")
public class PopulationEntity {
	
	//INSERT INTO population(precinctid ,  blackPopulation  , whitePopulation  , 
	//other  , pacificIslanderPoplation  , asianPopulation  , alaskanAndNativeAmericanPopulation) VALUES (%s, %s, %s , %s , %s,%s,%s)"
	
	@Id
	private Long precinctid;
	
	@Column(nullable=false)
	private Long blackPopulation;
	
	@Column(nullable=false)
	private Long whitePopulation;
	
	@Column(nullable=false)
	private Long otherPopulation;
	
	@Column(nullable=false)
	private Long pacificIslanderPopulation;
	
	@Column(nullable=false)
	private Long asianPopulation;
	
	@Column(nullable=false)
	private Long alaskanNativeAmericanPopulation;

	public Long getPrecinctid() {
		return precinctid;
	}

	public void setPrecinctid(Long precinctid) {
		this.precinctid = precinctid;
	}

	public Long getBlackPopulation() {
		return blackPopulation;
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
