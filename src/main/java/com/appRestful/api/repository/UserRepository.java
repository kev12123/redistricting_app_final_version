package com.appRestful.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appRestful.api.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
	
	UserEntity findByUsername(String username);
}
