package com.appRestful.api.model.request;

public class BatchRequestModel {
	
	
    private int stateid;
	private String batchFieldSelect;
	private String batchFieldSelectValue;
	private int numRuns;
	private int batchNumDistricts;
    private double  batchNumGoalDistrictsMinMax;
    private double batchMajorityMinorityMin;
    private double batchMajorityMinorityMax;
    private double batchEdgeCut;
    private int batchtargetDemo;
    private double batchConvexHull;
    private double batchPolspyPopper;
    private double batchMeanMedian;
    private double batchEfficiencyGap;
    private double batchPopulationDeviation;
    
	public String getBatchFieldSelect() {
		return batchFieldSelect;
	}
	public void setBatchFieldSelect(String batchFieldSelect) {
		this.batchFieldSelect = batchFieldSelect;
	}
	public String getBatchFieldSelectValue() {
		return batchFieldSelectValue;
	}
	public void setBatchFieldSelectValue(String batchFieldSelectValue) {
		this.batchFieldSelectValue = batchFieldSelectValue;
	}
	public int getNumRuns() {
		return numRuns;
	}
	public void setNumRuns(int numRuns) {
		this.numRuns = numRuns;
	}
	public int getBatchNumDistricts() {
		return batchNumDistricts;
	}
	public void setBatchNumDistricts(int batchNumDistricts) {
		this.batchNumDistricts = batchNumDistricts;
	}
	public double getBatchNumGoalDistrictsMinMax() {
		return batchNumGoalDistrictsMinMax;
	}
	public void setBatchNumGoalDistrictsMinMax(double batchNumGoalDistrictsMinMax) {
		this.batchNumGoalDistrictsMinMax = batchNumGoalDistrictsMinMax;
	}
	public double getBatchMajorityMinorityMin() {
		return batchMajorityMinorityMin;
	}
	public void setBatchMajorityMinorityMin(double batchMajorityMinorityMin) {
		this.batchMajorityMinorityMin = batchMajorityMinorityMin;
	}
	public double getBatchMajorityMinorityMax() {
		return batchMajorityMinorityMax;
	}
	public void setBatchMajorityMinorityMax(double batchMajorityMinorityMax) {
		this.batchMajorityMinorityMax = batchMajorityMinorityMax;
	}
	public double getBatchEdgeCut() {
		return batchEdgeCut;
	}
	public void setBatchEdgeCut(double batchEdgeCut) {
		this.batchEdgeCut = batchEdgeCut;
	}
	public int getBatchtargetDemo() {
		return batchtargetDemo;
	}
	public void setBatchtargetDemo(int batchtargetDemo) {
		this.batchtargetDemo = batchtargetDemo;
	}
	public double getBatchConvexHull() {
		return batchConvexHull;
	}
	public void setBatchConvexHull(double batchConvexHull) {
		this.batchConvexHull = batchConvexHull;
	}
	public double getBatchPolspyPopper() {
		return batchPolspyPopper;
	}
	public void setBatchPolspyPopper(double batchPolspyPopper) {
		this.batchPolspyPopper = batchPolspyPopper;
	}
	public double getBatchMeanMedian() {
		return batchMeanMedian;
	}
	public void setBatchMeanMedian(double batchMeanMedian) {
		this.batchMeanMedian = batchMeanMedian;
	}
	public double getBatchEfficiencyGap() {
		return batchEfficiencyGap;
	}
	public void setBatchEfficiencyGap(double batchEfficiencyGap) {
		this.batchEfficiencyGap = batchEfficiencyGap;
	}
	public double getBatchPopulationDeviation() {
		return batchPopulationDeviation;
	}
	public void setBatchPopulationDeviation(double batchPopulationDeviation) {
		this.batchPopulationDeviation = batchPopulationDeviation;
	}
	public int getStateid() {
		return stateid;
	}
	public void setStateid(int stateid) {
		this.stateid = stateid;
	}
    
       

}
