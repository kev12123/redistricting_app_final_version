package com.appRestful.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Precinct")
public class PrecinctEntity {
	
	//CREATE TABLE Precinct(id TEXT(50) NOT NULL , stateid BIGINT , countyid INT , PRIMARY KEY(id) , FOREIGN KEY(stateid) 
	//REFERENCES State(id) , FOREIGN KEY(countyid) REFERENCES County(id))
	
	@Id
	private Long id;
	
	
	@Column(nullable=false)
	private int stateid;
	
	@Column(nullable=false)
	private int countyid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}

	public int getCountyid() {
		return countyid;
	}

	public void setCountyid(int countyid) {
		this.countyid = countyid;
	}
	
	
	
}