package com.appRestful.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Coordinates")
public class CoordinateEntity {
	
	 @Id
	 @GeneratedValue
	 private long id;
	 
	 @Column(nullable=false)
	 private long precinctid;
	 
	 @Column(nullable=false)
	 private double coordinateX;
	 
	 @Column(nullable=false)
	 private double coordinateY;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPrecinctid() {
		return precinctid;
	}

	public void setPrecinctid(long precinctid) {
		this.precinctid = precinctid;
	}

	public double getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(long coordinateX) {
		this.coordinateX = coordinateX;
	}

	public double getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(long coordinateY) {
		this.coordinateY = coordinateY;
	}
	 
	 
}
