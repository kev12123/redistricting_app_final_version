package com.appRestful.api.component.data;

public class Result {
	
    private int year;
    private long democraticVotes;
    private long republicanVotes;

    public Result(int year, long democraticVotes, long republicanVotes) {
        this.year = year;
        this.democraticVotes = democraticVotes;
        this.republicanVotes = republicanVotes;
    }

    public int getYear() {
        return year;
    }

    public long getDemocraticVotes() {
        return democraticVotes;
    }

    public long getRepublicanVotes() {
        return republicanVotes;
    }

    public Object Clone(){
        return new Result(this.year,this.democraticVotes,this.republicanVotes);
    }

    @Override
    public String toString() {
        return "Result{" +
                "year=" + year +
                ", democraticVotes=" + democraticVotes +
                ", republicanVotes=" + republicanVotes +
                '}';
    }
}
