package com.appRestful.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GeneratedDistrictPK implements Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -5817599542217157173L;

	@Column(name="runid")
	private String runid;
	
	@Column(name="districtid")
	private Long districtid;
	
	public GeneratedDistrictPK() {}
	
	public GeneratedDistrictPK(String runid, Long districtid) {
		super();
		this.runid = runid;
		this.districtid = districtid;
	}

	public String getRunid() {
		return runid;
	}

	public void setRunid(String runid) {
		this.runid = runid;
	}

	public Long getDistrictid() {
		return districtid;
	}

	public void setDistrictid(Long districtid) {
		this.districtid = districtid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
