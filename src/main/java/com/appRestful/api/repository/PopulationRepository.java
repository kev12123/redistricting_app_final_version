package com.appRestful.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.appRestful.api.entity.PopulationEntity;
import com.appRestful.api.entity.UserEntity;

public interface PopulationRepository extends CrudRepository<PopulationEntity,Long> {
	
	PopulationEntity findByPrecinctid(Long precinctid);
	

}
