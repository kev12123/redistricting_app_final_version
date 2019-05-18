package com.appRestful.api.model.request;

public class UserLoginRequestModel {
	
	private String username;
	private String passWord;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return passWord;
	}
	public void setPassword(String password) {
		this.passWord = password;
	}
}
