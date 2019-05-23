package com.appRestful.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GeneratedVoting")
public class GeneratedPrecinctVotingEntity {
	
	@Id
	private long precinctid;
	
	@Column(nullable=false)
	private String runid;
	
	@Column
	private String districtid;
	
	@Column(nullable=false)
	private Long democraticVote;
	
	@Column(nullable=false)
	private Long republicanVote;
	
	@Column(nullable=false)
	private Long otherVote;

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

	public Long getDemocraticVote() {
		return democraticVote;
	}

	public void setDemocraticVote(Long democraticVote) {
		this.democraticVote = democraticVote;
	}

	public Long getRepublicanVote() {
		return republicanVote;
	}

	public void setRepublicanVote(Long republicanVote) {
		this.republicanVote = republicanVote;
	}

	public Long getOtherVote() {
		return otherVote;
	}

	public void setOtherVote(Long otherVote) {
		this.otherVote = otherVote;
	}
	
}
