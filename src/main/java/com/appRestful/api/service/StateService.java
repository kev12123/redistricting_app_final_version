package com.appRestful.api.service;

import com.appRestful.api.shared.dto.StateDto;

public interface StateService {
	
	StateDto findByname(String stateName);
}
