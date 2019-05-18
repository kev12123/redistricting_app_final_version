package com.appRestful.api.shared.dto;

import java.io.Serializable;

public class StateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3758715065290227405L;
	
	private int id;
	
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
