package com.appRestful.api.model.request;

import com.appRestful.api.enums.Demographic;

public class AlgorithmRequestModel {
	
	private int goalDistricts;
	private int stateid;
    private long targetPopulation;
    private double lengthWithRatio;
    private double majorityMinorityMinPercentage;
    private double polsbyPopper;
    private double edgeCut;
    private double convexHull;
    private double meanMedian;
    private double populationEquality;
    private double efficiencyGap;
    private double majorityMinorityMaxPercentage;
    private double majorityMinorityWeight;
    private String majorityMinorityDemographic;
    private int goalMajorityMinorityDistricts;
    private int iterationQuantity;
    private double allowedPopulationDeviation;

    public long getTargetPopulation() {
        return targetPopulation;
    }

    public void setTargetPopulation(long targetPopulation) {
        this.targetPopulation = targetPopulation;
    }

    public double getMajorityMinorityMinPercentage() {
        return majorityMinorityMinPercentage;
    }

    public void setMajorityMinorityMinPercentage(double majorityMinorityMinPercentage) {
        this.majorityMinorityMinPercentage = majorityMinorityMinPercentage;
    }

    public double getMajorityMinorityMaxPercentage() {
        return majorityMinorityMaxPercentage;
    }

    public void setMajorityMinorityMaxPercentage(double majorityMinorityMaxPercentage) {
        this.majorityMinorityMaxPercentage = majorityMinorityMaxPercentage;
    }

    public Demographic getMajorityMinorityDemographic() {
        return Demographic.valueOf(majorityMinorityDemographic.toUpperCase());
    }

    public void setMajorityMinorityDemographic(String majorityMinorityDemographic) {
        this.majorityMinorityDemographic = majorityMinorityDemographic;
    }

    public int getGoalDistricts() {
        return goalDistricts;
    }

    public void setGoalDistricts(int goalDistricts) {
        this.goalDistricts = goalDistricts;
    }

    public int getGoalMajorityMinorityDistricts() {
        return goalMajorityMinorityDistricts;
    }

    public void setGoalMajorityMinorityDistricts(int goalMajorityMinorityDistricts) {
        this.goalMajorityMinorityDistricts = goalMajorityMinorityDistricts;
    }

    public int getIterationQuantity() {
        return iterationQuantity;
    }

    public void setIterationQuantity(int iterationQuantity) {
        this.iterationQuantity = iterationQuantity;
    }

    public double getAllowedPopulationDeviation() {
        return allowedPopulationDeviation;
    }

    public void setAllowedPopulationDeviation(double allowedPopulationDeviation) {
        this.allowedPopulationDeviation = allowedPopulationDeviation;
    }

	public double getPolsbyPopper() {
		return polsbyPopper;
	}

	public void setPolsbyPopper(double polsbyPopper) {
		this.polsbyPopper = polsbyPopper;
	}

	public double getEdgeCut() {
		return edgeCut;
	}

	public void setEdgeCut(double edgeCut) {
		this.edgeCut = edgeCut;
	}

	public double getEfficiencyGap() {
		return efficiencyGap;
	}

	public void setEfficiencyGap(double efficiencyGap) {
		this.efficiencyGap = efficiencyGap;
	}

	public double getConvexHull() {
		return convexHull;
	}

	public void setConvexHull(double convexHull) {
		this.convexHull = convexHull;
	}

	public double getLengthWithRatio() {
		return lengthWithRatio;
	}

	public void setLengthWithRatio(double lengthWithRatio) {
		this.lengthWithRatio = lengthWithRatio;
	}

	public double getMeanMedian() {
		return meanMedian;
	}

	public void setMeanMedian(double meanMedian) {
		this.meanMedian = meanMedian;
	}

	public double getPopulationEquality() {
		return populationEquality;
	}

	public void setPopulationEquality(double populationEquality) {
		this.populationEquality = populationEquality;
	}

	public double getMajorityMinorityWeight() {
		return majorityMinorityWeight;
	}

	public void setMajorityMinorityWeight(double majorityMinorityWeight) {
		this.majorityMinorityWeight = majorityMinorityWeight;
	}

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}
	
}
