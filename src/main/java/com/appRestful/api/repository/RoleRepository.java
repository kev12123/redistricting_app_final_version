package com.appRestful.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.appRestful.api.entity.RoleEntity;
import com.appRestful.api.entity.UserEntity;

public interface RoleRepository extends CrudRepository<RoleEntity,String>  {
	
	RoleEntity findByRoleidUsername(String username);
}
