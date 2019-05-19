package com.appRestful.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.appRestful.api.entity.CoordinateEntity;
import com.appRestful.api.entity.UserEntity;

public interface CoordinatesRepository  extends CrudRepository<CoordinateEntity,Long> {
	
	List<CoordinateEntity>findByPrecinctid(Long precinctid);
}
