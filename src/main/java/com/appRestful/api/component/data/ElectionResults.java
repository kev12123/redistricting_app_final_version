package com.appRestful.api.component.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.appRestful.api.enums.Operation;

public class ElectionResults {
	
    private Map<Integer,Result> yearlyResults;

    public ElectionResults(Collection<Result> results) {
        yearlyResults = new TreeMap<>();
       for(Result result:results){
           yearlyResults.put(result.getYear(),result);
       }
    }

    public ElectionResults(){
       yearlyResults = new HashMap<>();
    }

    private ElectionResults(Map<Integer, Result> yearlyResults) {
        this.yearlyResults = yearlyResults;
    }

    public Result getResultFromYear(int year){
        return yearlyResults.get(year);
    }

    //Returns previously held ket or null if any
    public Result addResult(int year,Result result){
        return yearlyResults.put(year,result);
    }

    public Result updateResult(int year, Result result, Operation operation){
        Result currentResult = yearlyResults.get(year);
        long newDemocraticVotes = 0;
        long newRepublicanVotes = 0;
        if(operation == Operation.ADD) {
            newDemocraticVotes = currentResult.getDemocraticVotes() + result.getDemocraticVotes();
            newRepublicanVotes = currentResult.getRepublicanVotes() + result.getRepublicanVotes();
        }
        else if(operation == Operation.REMOVE) {
            newDemocraticVotes = currentResult.getDemocraticVotes() - result.getDemocraticVotes();
            newRepublicanVotes = currentResult.getRepublicanVotes() - result.getRepublicanVotes();
        }
        Result newResult = new Result(year, newDemocraticVotes,newRepublicanVotes);
        yearlyResults.put(year,newResult);
        return newResult;
    }

    public Result removeResult(int year){
        return yearlyResults.remove(year);
    }

    public Set<Integer> getAllYearsPresent(){
        return yearlyResults.keySet();
    }


    public Object clone(){
        Map<Integer,Result> yearlyResultsClone = new HashMap<>();
        for(Integer year:yearlyResults.keySet()){
            yearlyResultsClone.put(year,(Result) yearlyResults.get(year).Clone());
        }
        return new ElectionResults(yearlyResultsClone);
    }

    @Override
    public String toString() {
        return "ElectionResults{" +
                "yearlyResults=" + yearlyResults +
                '}';
    }
}
