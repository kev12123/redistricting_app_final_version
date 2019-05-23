package com.appRestful.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appRestful.api.entity.CoordinateEntity;
import com.appRestful.api.entity.GeneratedDistrictEntity;

@Repository
public interface GeneratedDistrictRepository extends CrudRepository<GeneratedDistrictEntity,Long> {
	
	List<GeneratedDistrictEntity> findByGeneratedDistrictIdRunid(String runid);

}
