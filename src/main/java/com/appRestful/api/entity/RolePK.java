package com.appRestful.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RolePK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7285884516319979621L;

	public RolePK() {}
	
	public RolePK(String username, String role) {
		super();
		this.username = username;
		this.role = role;
	}

	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
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
