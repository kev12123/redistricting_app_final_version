package com.appRestful.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Voting(precinctid , democraticVote  , republicanVote  , otherVote  )

@Entity
@Table(name="Voting")
public class VotingEntity  {
	
	@Id
	private Long precinctid;
	
	@Column(nullable=false)
	private Long democraticVote;
	
	@Column(nullable=false)
	private Long republicanVote;
	
	@Column(nullable=false)
	private Long otherVote;

	public Long getPrecinctid() {
		return precinctid;
	}

	public void setPrecinctid(Long precinctid) {
		this.precinctid = precinctid;
	}

	public Long getDemocraticVote() {
		return democraticVote;
	}

	public void setDemocraticVote(Long democraticVote) {
		this.democraticVote = democraticVote;
	}

	public Long getRepublicanVote() {
		return republicanVote;
	}

	public void setRepublicanVote(Long republicanVote) {
		this.republicanVote = republicanVote;
	}

	public Long getOtherVote() {
		return otherVote;
	}

	public void setOtherVote(Long otherVote) {
		this.otherVote = otherVote;
	}
	
	
	
}
