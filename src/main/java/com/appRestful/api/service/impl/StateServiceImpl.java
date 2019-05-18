package com.appRestful.api.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appRestful.api.entity.StateEntity;
import com.appRestful.api.entity.UserEntity;
import com.appRestful.api.repository.StateRepository;
import com.appRestful.api.service.StateService;
import com.appRestful.api.shared.dto.StateDto;
import com.appRestful.api.shared.dto.UserDto;

@Service
public class StateServiceImpl implements StateService {
	
	@Autowired
	StateRepository stateRepo;

	@Override
	public StateDto findByname(String stateName) {
		// TODO Auto-generated method stub
		StateEntity state =  stateRepo.findByname(stateName);
		System.out.println(state.getId());
		System.out.println(state.getName());
		StateDto returnValue = new StateDto();
		BeanUtils.copyProperties(state, returnValue);
		
		return returnValue;
	}
}
