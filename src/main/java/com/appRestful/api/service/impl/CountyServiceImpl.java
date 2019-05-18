package com.appRestful.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.appRestful.api.entity.CountyEntity;
import com.appRestful.api.repository.CountyRepository;
import com.appRestful.api.service.CountyService;
import com.appRestful.api.shared.dto.CountyDto;

public class CountyServiceImpl implements CountyService{
	
	@Autowired
	CountyRepository countyRepo;
	
	@Override
	public List<CountyDto> findByCountyIdStateid(int stateid) {
		// TODO Auto-generated method stub
//		List<CountyEntity> counties = countyRepo.findByStateid(24);
//		System.out.print(counties);
		return null;
	}

}
