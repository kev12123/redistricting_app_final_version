package com.appRestful.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CountyPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8683470700799862666L;

	public CountyPK() {}
	
	public CountyPK(int id, int stateid) {
		super();
		this.id = id;
		this.stateid = stateid;
	}

	@Column(name="id")
	private int id;
	
	@Column(name="stateid")
	private int stateid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}
}
