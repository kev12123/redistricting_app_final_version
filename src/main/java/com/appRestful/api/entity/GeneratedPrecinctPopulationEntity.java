package com.appRestful.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GeneratedPopulation")
public class GeneratedPrecinctPopulationEntity  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2681423849314670398L;

	@Id
	private long precinctid;
	
	@Column(nullable=false)
	private String runid;
	
	@Column(nullable=false)
	private String districtid;
	
	@Column(nullable=false)
	private Long blackPopulation;
	
	@Column(nullable=false)
	private Long whitePopulation;
	
	@Column(nullable=false)
	private Long otherPopulation;
	

	public long getPrecinctid() {
		return precinctid;
	}

	public void setPrecinctid(long precinctid) {
		this.precinctid = precinctid;
	}

	public String getRunid() {
		return runid;
	}

	public void setRunid(String runid) {
		this.runid = runid;
	}

	public String getDistrictid() {
		return districtid;
	}

	public void setDistrictid(String districtid) {
		this.districtid = districtid;
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

	
}

