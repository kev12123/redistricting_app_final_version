package com.appRestful.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appRestful.api.entity.GeneratedDistrictEntity;
import com.appRestful.api.entity.GeneratedPrecinctPopulationEntity;

@Repository
public interface  GeneratedPopulationRepository  extends CrudRepository<GeneratedPrecinctPopulationEntity,Long> {
	
	List<GeneratedPrecinctPopulationEntity> findByRunidAndPrecinctid(String runid , Long precinctid);

}
