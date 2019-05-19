package com.appRestful.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.appRestful.api.entity.NeighborEntity;
import com.appRestful.api.entity.UserEntity;

public interface NeighborsRepository extends CrudRepository<NeighborEntity,Long> {
	
	List<NeighborEntity> findByNeighboridNeighborprecinctid(Long precinctid);

}
