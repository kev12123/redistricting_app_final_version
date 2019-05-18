package com.appRestful.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.service.StateService;
import com.appRestful.api.service.UserService;
import com.appRestful.api.shared.dto.StateDto;

@RestController
@RequestMapping("/map")
public class AlgorithmController {
	
	@Autowired
	StateService stateService;
	
	@PostMapping("/runAlgorithm")
	public ResponseEntity runAlgorithm(@RequestBody AlgorithmRequestModel algorithmDetails) {
		
		//1.create objective function with user input in AlgorithmRequestModel
		
		//2. AlgorithmObject passing in the state as parameters, data for state will be retrieved from the data
		
		//3. Call initialize method on algorithm 
		
		//4. algorithm run
		
		StateDto state = null;
		try {
			state = stateService.findByname("florida");
		}catch(NullPointerException e){
			
		}
		System.out.println(state.getId());
		System.out.println(state.getName());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
