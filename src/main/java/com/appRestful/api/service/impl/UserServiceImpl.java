 package com.appRestful.api.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appRestful.api.entity.UserEntity;
import com.appRestful.api.repository.UserRepository;
import com.appRestful.api.service.UserService;
import com.appRestful.api.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		System.out.println(user.getUsername());
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		//setting encrypted password
		userEntity.setPassword("test");
		userEntity.setUserId("testId");
		UserEntity storedUserDetails = userRepo.save(userEntity);
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		
		return returnValue;
	}

	@Override
	public UserDto findByUsername(String username) {
		// TODO Auto-generated method stub
		
		UserEntity user =  userRepo.findByUsername(username);
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(user, returnValue);
		
		return returnValue;
		
	}

}
