package com.appRestful.api.shared.dto;

import java.io.Serializable;

public class CountyDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8164516989513283678L;
	
	private Long id;
	private int stateid;
	private String name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
