package com.appRestful.api.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appRestful.api.model.request.UserDetailsRequestModel;
import com.appRestful.api.model.request.UserLoginRequestModel;
import com.appRestful.api.model.response.UserRest;
import com.appRestful.api.service.UserService;
import com.appRestful.api.shared.dto.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public String getUser(){  
		
		return "get user was called";
	}
	
	@PostMapping("/adduser")
	public ResponseEntity createUser(@RequestBody UserDetailsRequestModel userDetails) {
		
		System.out.println(userDetails.getPassWord());
		UserDto foundUser = null;
		try {
			foundUser = userService.findByUsername(userDetails.getUsername());
		}catch(NullPointerException e){
			
		}
		
		
		if(foundUser == null) {
			//User does not exist ; create new user
			UserRest returnUser = new UserRest();
			
			System.out.println(userDetails.getUsername());
			System.out.println(userDetails.getPassWord());
			UserDto userDto = new UserDto();
			userDto.setUsername(userDetails.getUsername());
			userDto.setPassword(userDetails.getPassWord());
			userDto.setEmail(userDetails.getEmail());
			UserDto createdUser = userService.createUser(userDto);
			returnUser.setUsername(createdUser.getUsername());
			
			return ResponseEntity.ok(returnUser);
			
		}
		
	
		
		return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/login")
	public ResponseEntity loginUser(@RequestBody UserLoginRequestModel loginDetails) {
		
		UserDto foundUser = null;
		try {
			foundUser = userService.findByUsername(loginDetails.getUsername());
		}catch(NullPointerException e){
			
		}
		
		if(foundUser == null) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			
			if(foundUser.getPassword().equals(loginDetails.getPassword())) {
				
				System.out.println("User exists");
				System.out.println(foundUser.getUsername() + " " + foundUser.getEmail() + " " + foundUser.getPassword());
				
				UserRest returnUser = new UserRest();
				BeanUtils.copyProperties(foundUser, returnUser);
				
				return ResponseEntity.ok(returnUser);
			}
			else {
				
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
			}
			

		}
		
		
		
		
	}
		
	
	@PutMapping
	public String updateUser() {
		
		return "update user was called";
	}
	
	@DeleteMapping
	public String deleteUser() {
		
		return "delete user";
	}
}
