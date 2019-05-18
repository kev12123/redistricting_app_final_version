package com.appRestful.api.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="County")
public class CountyEntity {
	
	//CREATE TABLE County(id INT NOT NULL, stateid BIGINT NOT NULL, NAME TEXT , 
	//PRIMARY KEY(id) , CONSTRAINT FK_stateid FOREIGN KEY (stateid) REFERENCES State(id))
	
   
	@EmbeddedId
	private CountyPK countyId;
	
	@Column(nullable = false)
	private String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CountyPK getCountyId() {
		return countyId;
	}

	public void setCountyId(CountyPK countyId) {
		this.countyId = countyId;
	}
	


}
