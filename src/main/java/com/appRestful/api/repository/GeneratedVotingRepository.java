package com.appRestful.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appRestful.api.entity.GeneratedPrecinctPopulationEntity;
import com.appRestful.api.entity.GeneratedPrecinctVotingEntity;

@Repository
public interface GeneratedVotingRepository  extends CrudRepository<GeneratedPrecinctVotingEntity,Long> {
	
	List<GeneratedPrecinctVotingEntity> findByRunidAndPrecinctid(String runid , Long precinctid);
}
