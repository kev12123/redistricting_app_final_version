package com.appRestful.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.appRestful.api.entity.PrecinctEntity;
import com.appRestful.api.entity.UserEntity;

public interface PrecinctRepository extends CrudRepository<PrecinctEntity,Long>  {
	
	List<PrecinctEntity> findByCountyidAndStateid(int countyid , int stateid);

}
