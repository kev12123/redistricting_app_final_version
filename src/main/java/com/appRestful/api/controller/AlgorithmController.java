package com.appRestful.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appRestful.api.entity.CountyEntity;
import com.appRestful.api.entity.PopulationEntity;
import com.appRestful.api.entity.PrecinctEntity;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.model.request.PopulationRequestModel;
import com.appRestful.api.model.response.PopulationResp;
import com.appRestful.api.repository.CountyRepository;
import com.appRestful.api.repository.PopulationRepository;
import com.appRestful.api.repository.PrecinctRepository;
import com.appRestful.api.repository.VotingRepository;
import com.appRestful.api.service.CountyService;
import com.appRestful.api.service.StateService;
import com.appRestful.api.service.UserService;
import com.appRestful.api.shared.dto.CountyDto;
import com.appRestful.api.shared.dto.StateDto;

@RestController
@RequestMapping("/map")
public class AlgorithmController {
	
	@Autowired
	StateService stateService;
	
	@Autowired
	CountyRepository countyRepo;
	
	@Autowired
	PrecinctRepository precinctRepo;
	
	@Autowired
	PopulationRepository populationRepo;
	
	@Autowired
	VotingRepository votingRepo;
	
	@PostMapping("/runAlgorithm")
	public ResponseEntity runAlgorithm(@RequestBody AlgorithmRequestModel algorithmDetails) {
		
		//1.create objective function with user input in AlgorithmRequestModel
		
		//2. AlgorithmObject passing in the state as parameters, data for state will be retrieved from the data
		
		//3. Call initialize method on algorithm 
		
		//4. algorithm run
		
		StateDto state = null;
		CountyDto county = null;
		Set<Long> precincts =  new HashSet();
		try {
			state = stateService.findByname("florida");
		
	    //getting all counties for state selected
		List<CountyEntity> c = countyRepo.findByCountyIdStateid(24);
		
		//getting precincts for each county
		for(CountyEntity countyt: c) {
			System.out.println(countyt.getName());
			for(PrecinctEntity pEntity: precinctRepo.findByCountyidAndStateid(countyt.getCountyId().getId() , countyt.getCountyId().getStateid())) {
				precincts.add(pEntity.getId());
			}
		}
		
		//getting population for each precinct
		
		for(Long precinctId: precincts) {
			System.out.println(precinctId);
			PopulationEntity popul = populationRepo.findByPrecinctid(precinctId); 
			System.out.println( "White Population : "  + popul.getWhitePopulation());
		}
		
		
		
		
		}catch(NullPointerException e){
			
		}
		System.out.println(state.getId());
		System.out.println(state.getName());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/getPopulation")
	public ResponseEntity getPopulation(@RequestBody PopulationRequestModel req){
		
		
		PopulationEntity  popul = populationRepo.findByPrecinctid(req.getPrecinctid());
		PopulationResp returnPopul = new PopulationResp();
		returnPopul.setAlaskanNativeAmericanPopulation(popul.getAlaskanNativeAmericanPopulation());
		returnPopul.setAsianPopulation(popul.getAsianPopulation());
		returnPopul.setBlackPopulation(popul.getBlackPopulation());
		returnPopul.setOtherPopulation(popul.getOtherPopulation());
		returnPopul.setWhitePopulation(popul.getWhitePopulation());
		returnPopul.setPacificIslanderPopulation(popul.getPacificIslanderPopulation());
		
		return ResponseEntity.ok(returnPopul);
	}

}
