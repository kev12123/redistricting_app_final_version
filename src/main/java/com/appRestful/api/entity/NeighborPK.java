package com.appRestful.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NeighborPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8735452372037870665L;
	
	
	public NeighborPK() {}
	
	public NeighborPK(long precinctid, long neighborprecinctid) {
		super();
		this.precinctid = precinctid;
		this.neighborprecinctid = neighborprecinctid;
	}

	
	@Column(nullable=false)
	private long precinctid;
	
	@Column(nullable=false)
	private long neighborprecinctid;

	public long getPrecinctid() {
		return precinctid;
	}

	public void setPrecinctid(long precinctid) {
		this.precinctid = precinctid;
	}

	public long getNeighborprecinctid() {
		return neighborprecinctid;
	}

	public void setNeighborprecinctid(long neighborprecinctid) {
		this.neighborprecinctid = neighborprecinctid;
	}

}
