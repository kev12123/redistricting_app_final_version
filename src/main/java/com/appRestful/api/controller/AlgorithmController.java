package com.appRestful.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.locationtech.jts.geom.Coordinate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appRestful.api.component.Cluster;
import com.appRestful.api.component.District;
import com.appRestful.api.component.Edge;
import com.appRestful.api.component.Precinct;
import com.appRestful.api.component.State;
import com.appRestful.api.component.data.Data;
import com.appRestful.api.component.data.Demography;
import com.appRestful.api.component.data.ElectionResults;
import com.appRestful.api.component.data.Geography;
import com.appRestful.api.component.data.Result;
import com.appRestful.api.entity.CountyEntity;
import com.appRestful.api.entity.PopulationEntity;
import com.appRestful.api.entity.PrecinctEntity;
import com.appRestful.api.entity.VotingEntity;
import com.appRestful.api.enums.Demographic;
import com.appRestful.api.enums.Measure;
import com.appRestful.api.enums.PoliticalParty;
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
	public ResponseEntity runAlgorithm(@RequestBody AlgorithmRequestModel algorithmData) {
		
		 Map<String,Cluster> clusters = new HashMap();
		 
		 
		
		//1.create objective function with user input in AlgorithmRequestModel
	   
		 Map<Measure,Double>  weights = createWeights(algorithmData);
	     
		 int stateId = 27;
		 State state = new State(Edge.class);
		 List<CountyEntity> counties = countyRepo.findByCountyIdStateid(24);
//		 Set<Long> precincts = new HashSet();
			for(CountyEntity countyt: counties) {
				for(PrecinctEntity pEntity: precinctRepo.findByCountyidAndStateid(countyt.getCountyId().getId() , countyt.getCountyId().getStateid())) {
					
					//Allocate precinct population
					Map<Demographic,Long> demographics = new HashMap();
					long totalPopulation = 0;
					//set population
					PopulationEntity popul = populationRepo.findByPrecinctid(pEntity.getId()); 
					System.out.println(popul.getBlackPopulation());
					demographics.put(Demographic.BLACK, popul.getBlackPopulation());
					totalPopulation+=popul.getBlackPopulation();
					demographics.put(Demographic.CAUCASIAN, popul.getWhitePopulation());
					totalPopulation+=popul.getWhitePopulation();
					demographics.put(Demographic.HISPANIC, popul.getOtherPopulation());
					totalPopulation+=popul.getOtherPopulation();
					
					//Allocate political party data
					Map<PoliticalParty,Long> parties = new HashMap();
					VotingEntity votes = votingRepo.findByPrecinctid(pEntity.getId());
						parties.put(PoliticalParty.DEMOCRAT , (votes != null) ? votes.getDemocraticVote() : 0);
					parties.put(PoliticalParty.REPUBLICAN , (votes != null) ? votes.getRepublicanVote() : 0);
					
					//Create demography object
					Demography demography = new Demography(totalPopulation,demographics,parties,algorithmData);
					
					//Create Result object
					Result result = new Result(2006,(votes != null) ? votes.getDemocraticVote() : 0,(votes != null) ? votes.getRepublicanVote() : 0);
					
					Collection<Result> results = new ArrayList();
					results.add(result);
					
					//create election results object
					ElectionResults electionResults = new ElectionResults(results);
					
					//create geography object
					Coordinate coordinate1 = new Coordinate(0,0);
			        Coordinate coordinate2 = new Coordinate(5,0);
			        Coordinate coordinate4 = new Coordinate(5,5);
			        Coordinate coordinate3 = new Coordinate(0,5);
			        Coordinate coordinate5 = new Coordinate(0,0);
					
			        Geography geography = new Geography(countyt.getName() , new Coordinate[] {coordinate1,coordinate2,coordinate3,coordinate4,coordinate5});
			        
			        Data data = new Data(demography,electionResults,geography);
			        
			        //create new precint
			        Precinct precinct = new Precinct(Long.toString(pEntity.getId()));
			        
			        precinct.initializeData(data);
					
			        //Create cluster
			        Cluster cluster = new District(Edge.class,precinct);
			        state.addCluster(cluster);
					clusters.put(cluster.getPrimaryId(), cluster);
					
					
					
					
				}
			}
	
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
	
	
	public static Map<Measure,Double> createWeights(AlgorithmRequestModel algorithmData) {
		
		 Map<Measure,Double>  weights = new HashMap();
		 weights.put(Measure.EFFICENCY_GAP, algorithmData.getEfficiencyGap());
		 weights.put(Measure.POLSBY_POPPER, algorithmData.getPolsbyPopper());
		 weights.put(Measure.EDGE_CUT, algorithmData.getEdgeCut());
		 weights.put(Measure.CONVEX_HULL, algorithmData.getConvexHull());
		 weights.put(Measure.LENGTH_WIDTH_RATIO, algorithmData.getLengthWithRatio());
		 weights.put(Measure.MAJORITY_MINORITY, (algorithmData.getMajorityMinorityWeight()));
		 weights.put(Measure.MEAN_MEDIAN, algorithmData.getMeanMedian());
		 weights.put(Measure.POPULATION_EQUALITY, algorithmData.getPopulationEquality());
		 
		 return weights;
	}
	


}
