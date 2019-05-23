package com.appRestful.api.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GeneratedDistrict")
public class GeneratedDistrictEntity {
	
	
	@EmbeddedId
	private GeneratedDistrictPK generatedDistrictId;

	public GeneratedDistrictPK getGeneratedDistrictId() {
		return generatedDistrictId;
	}

	public void setGeneratedDistrictId(GeneratedDistrictPK generatedDistrictId) {
		this.generatedDistrictId = generatedDistrictId;
	}
}
