package com.appRestful.api.controller;

import java.lang.reflect.Array;
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

import com.appRestful.api.algorithm.Algorithm;
import com.appRestful.api.algorithm.ObjectiveFunction;
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
import com.appRestful.api.entity.CoordinateEntity;
import com.appRestful.api.entity.CountyEntity;
import com.appRestful.api.entity.NeighborEntity;
import com.appRestful.api.entity.PopulationEntity;
import com.appRestful.api.entity.PrecinctEntity;
import com.appRestful.api.entity.VotingEntity;
import com.appRestful.api.enums.Demographic;
import com.appRestful.api.enums.Measure;
import com.appRestful.api.enums.PoliticalParty;
import com.appRestful.api.model.request.AlgorithmRequestModel;
import com.appRestful.api.model.request.PopulationRequestModel;
import com.appRestful.api.model.request.RequestQueue;
import com.appRestful.api.model.response.DataResponse;
import com.appRestful.api.model.response.PopulationResp;
import com.appRestful.api.repository.CoordinatesRepository;
import com.appRestful.api.repository.CountyRepository;
import com.appRestful.api.repository.NeighborsRepository;
import com.appRestful.api.repository.PopulationRepository;
import com.appRestful.api.repository.PrecinctRepository;
import com.appRestful.api.repository.VotingRepository;
import com.appRestful.api.service.CountyService;
import com.appRestful.api.service.StateService;
import com.appRestful.api.service.UserService;
import com.appRestful.api.shared.dto.CountyDto;
import com.appRestful.api.shared.dto.StateDto;
import com.appRestful.api.utility.Utility;

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
	
	@Autowired
	CoordinatesRepository coordinateRepo;
	
	@Autowired
	NeighborsRepository neighborRepo;
	
  
	
	@PostMapping("/runAlgorithm")
	public ResponseEntity runAlgorithm(@RequestBody AlgorithmRequestModel algorithmData) {
		long start = System.nanoTime();
		long count = 0;
		
		 Map<String,Cluster> clusters = new HashMap();
		 
		 
		
		//1.create objective function with user input in AlgorithmRequestModel
	   
		 Map<Measure,Double>  weights = createWeights(algorithmData);
	     
		 int stateId = algorithmData.getStateid();
		 System.out.println("The State ID: "+ stateId);
		 State state = new State(Edge.class);
		 List<CountyEntity> counties = countyRepo.findByCountyIdStateid(27);
		 List<PrecinctEntity> precincts = new ArrayList();
		 List<List<String>> data  = new ArrayList();
		 Set UniquePrecincts = new HashSet();
		 for(CountyEntity countyt : counties) {
				for(PrecinctEntity pEntity: precinctRepo.findByCountyidAndStateid(countyt.getCountyId().getId() , countyt.getCountyId().getStateid())) 
				{
					precincts.add(pEntity);
					
				}
				
				
		 }
		 
		 int numOfDistricts =4;
		 int precinctsInDistrict = (precincts.size() /4);
		 
		 System.out.println(precinctsInDistrict + "rr");
		 System.out.println(precincts.size() + "rr");
		 
		 int j = 0;
		 for(int i = 1 ; i <= 4 ; i++) {
			 int districtCount = 0;
			 ArrayList<String> precinctsToColor = new ArrayList<String>();
			 System.out.println(i);
			 for(  ; j < precincts.size() ; j++) {
				 if(districtCount == precinctsInDistrict)
					 break;
				 
				 precinctsToColor.add(Long.toString(precincts.get(j).getId()));
				 UniquePrecincts.add(precincts.get(j).getId());
			
				 districtCount++;
				 
			 }
			 DataResponse resp = new DataResponse();
			 resp.setDistrictData(precinctsToColor);
			 resp.setStage("DUMMY PHASE");
			 RequestQueue.requestQueue.add(resp);
			 
		 }
		 
		 System.out.print(UniquePrecincts.size());
	
//		 Map<String,Precinct> precinctIds = new HashMap<>();
////		 Set<Long> precincts = new HashSet();
//			for(CountyEntity countyt: counties) {
//				for(PrecinctEntity pEntity: precinctRepo.findByCountyidAndStateid(countyt.getCountyId().getId() , countyt.getCountyId().getStateid())) {
//					
//					//Allocate precinct population
//					Long totalPopulation = 0L;
//					Map<Demographic,Long> demographics = new HashMap();
//					PopulationEntity popul = populationRepo.findByPrecinctid(pEntity.getId()); 
//					demographics.put(Demographic.BLACK, popul.getBlackPopulation());
//					totalPopulation+=popul.getBlackPopulation();
//					demographics.put(Demographic.CAUCASIAN, popul.getWhitePopulation());
//					totalPopulation+=popul.getWhitePopulation();
//					demographics.put(Demographic.HISPANIC, popul.getOtherPopulation());
//					totalPopulation+=popul.getOtherPopulation();
//					
//					//Allocate political party data
//					VotingEntity votes = votingRepo.findByPrecinctid(pEntity.getId());
//					Map<PoliticalParty,Long> parties = createElectionData(votes , pEntity.getId());
//					
//					//Create demography object
//					Demography demography = new Demography(totalPopulation,demographics,parties,algorithmData);
//					
//					//Create Result object
//					Result result = new Result(2006,(votes != null) ? votes.getDemocraticVote() : 0,(votes != null) ? votes.getRepublicanVote() : 0);
//					
//					Collection<Result> results = new ArrayList<Result>();
//					results.add(result);
//					
//					//create election results object
//					ElectionResults electionResults = new ElectionResults(results);
//				
//					List<Coordinate> coordinatesData =  createPrecinctCoordinates(coordinateRepo ,pEntity.getId());
//					
//					
//			        Geography geography = new Geography(countyt.getName() ,coordinatesData);
//			        
//			        Data data = new Data(demography,electionResults,geography);
//			        
//			        //create new precint
//			        Precinct precinct = new Precinct(Long.toString(pEntity.getId()));
//			        precinctIds.put(precinct.getPrecinctID(), precinct);
//			          
//			        precinct.initializeData(data);
//					
//			        //Create cluster
//			        Cluster cluster = new District(Edge.class,precinct);
//			        state.addCluster(cluster);
//					clusters.put(cluster.getPrimaryId(), cluster);
//					System.out.println("finished cluster " + count++);
//					
//					//iterate through precincts 
//					
//					//add neighbors list of neighbors 
//					
//					
//				}
//			}
//		
//	
//		for(Precinct p : precinctIds.values()) {
//			
//			List <NeighborEntity> neighbors = neighborRepo.findByNeighboridPrecinctid(Long.parseLong(p.getPrecinctID()));
//			for(NeighborEntity neighbor : neighbors) {
//				
//				String neighborPrecinctId = neighbor.getNeighborPK().getNeighborprecinctid() + "";
//				Precinct pNeighbor = precinctIds.get(neighborPrecinctId);
//				
//				p.addMutualNeighbor(pNeighbor);
//				Cluster cluster = p.getParentCluster();
//				Cluster neighborCluster = pNeighbor.getParentCluster();
//				if(!(state.containsEdge(cluster,neighborCluster) || cluster.equals(neighborCluster))) {
//					
//					Edge edge = new Edge(cluster,neighborCluster,algorithmData);
//					state.addEdge(cluster, neighborCluster , edge);
//				}
//				
//				
//			}
//			count++;
//			System.out.println(count);
//			
//		}
//		
//		
//		for(Precinct p : precinctIds.values()) 
//			p.initilizeBorder();
//		
//		
//		Long end = System.nanoTime();
//		System.out.println((end - start)/1000000000.);
//		
//		
//		algorithmData.setTargetPopulation(state.getTotalPopulation()/algorithmData.getGoalDistricts());
//		algorithmData.setIterationQuantity(Utility.iterationQuantity);
//		System.out.println("Target population" + algorithmData.getTargetPopulation());
//		
//		
//		ObjectiveFunction objectiveFunction  = new ObjectiveFunction(weights,algorithmData,state);
//		Algorithm algorithm = new Algorithm(state);
//		algorithm.initializeAlgorithm(objectiveFunction, algorithmData);
//		algorithm.run();
//		System.out.println("DONE");
//		System.out.println(algorithm.getNewState());
		
		
		
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
	
	
	@GetMapping("/getData")
	public ResponseEntity getIncrementalData() {
		
		if(!RequestQueue.requestQueue.isEmpty()){
			
	
	
			return ResponseEntity.ok(RequestQueue.requestQueue.remove(0));
		
		}
		else {
			
			return ResponseEntity.badRequest().build();
		}
		
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
	
	
	public static 	Map<PoliticalParty,Long> createElectionData(VotingEntity votes , Long precinctId) {
		
		Map<PoliticalParty,Long> parties = new HashMap();
		parties.put(PoliticalParty.DEMOCRAT , (votes != null) ? votes.getDemocraticVote() : 0);
		parties.put(PoliticalParty.REPUBLICAN , (votes != null) ? votes.getRepublicanVote() : 0);
		
		return parties;
		
	}
	
	
	public static List<Coordinate> createPrecinctCoordinates(CoordinatesRepository coordinateRepo , Long precinctId) {
		
		List<CoordinateEntity> coordinates = coordinateRepo.findByPrecinctid(precinctId);
		List<Coordinate> coordinatesData = new ArrayList();
		
		for(CoordinateEntity coordinate:coordinates) {
			coordinatesData.add(new Coordinate(coordinate.getCoordinateX(),coordinate.getCoordinateY()));
			 
		}
		
		return coordinatesData;
	}
	



	


}
