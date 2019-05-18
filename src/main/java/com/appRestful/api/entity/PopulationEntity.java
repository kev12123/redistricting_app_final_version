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
	

}
