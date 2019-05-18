package com.appRestful.api.service;

import com.appRestful.api.repository.UserRepository;
import com.appRestful.api.shared.dto.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto findByUsername(String username);
}
