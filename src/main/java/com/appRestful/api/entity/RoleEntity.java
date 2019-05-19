package com.appRestful.api.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class RoleEntity {
	
	@EmbeddedId
	private RolePK roleid;
	
	@Column(nullable=false)
	public RolePK getRoleid() {
		return roleid;
	}
	
	@Column(nullable=false)
	public void setRoleid(RolePK roleid) {
		this.roleid = roleid;
	}
}
