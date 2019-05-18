package com.appRestful.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.appRestful.api.entity.UserEntity;
import com.appRestful.api.entity.VotingEntity;

public interface VotingRepository extends CrudRepository<VotingEntity,Long> {
	
	VotingEntity findByPrecinctid(Long precinctid);
}
