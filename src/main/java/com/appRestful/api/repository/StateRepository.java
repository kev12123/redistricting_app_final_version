package com.appRestful.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appRestful.api.entity.StateEntity;
import com.appRestful.api.entity.UserEntity;

@Repository
public interface StateRepository extends CrudRepository<StateEntity,Integer>  {
	
	StateEntity findByname(String stateName);
}
