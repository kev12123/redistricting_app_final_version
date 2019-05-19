package com.appRestful.api.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Neighbors")
public class NeighborEntity {
	
	@EmbeddedId
	private NeighborPK neighborid;

	public NeighborPK getNeighborPK() {
		return neighborid;
	}

	public void setNeighborPK(NeighborPK neighborid) {
		this.neighborid = neighborid;
	}
	
	
}
