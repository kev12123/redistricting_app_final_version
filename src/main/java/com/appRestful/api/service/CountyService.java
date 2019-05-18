package com.appRestful.api.service;

import java.util.List;

import com.appRestful.api.shared.dto.CountyDto;


public interface CountyService {
	
    List<CountyDto> findByCountyIdStateid(int stateid);
}
