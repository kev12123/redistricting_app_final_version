package com.appRestful.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UserEntity implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5526113701498946608L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable= false)
	private String username;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@Column(nullable = false)
	private String password;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
