package com.appRestful.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.appRestful.api.entity.CountyEntity;
import com.appRestful.api.entity.PrecinctEntity;

public interface CountyRepository  extends CrudRepository<CountyEntity,Integer> {
	
	List<CountyEntity> findByCountyIdStateid(int countyid);
}
