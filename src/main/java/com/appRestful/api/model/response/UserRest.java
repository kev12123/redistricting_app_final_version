 package com.appRestful.api.model.response;

public class UserRest { 
	//contains details we want to return in the http response
	private String username;
	private String role;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
