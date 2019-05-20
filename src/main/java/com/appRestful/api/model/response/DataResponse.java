package com.appRestful.api.model.response;

import java.util.List;

public class DataResponse {
	
	private List<String> districtData;
	private String stage;

	public List<String> getDistrictData() {
		return districtData;
	}

	public void setDistrictData(List<String> districtData) {
		this.districtData = districtData;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String toString() {
		String s = "{" + stage + ", ";
		for(String str : districtData) {
			s += str + ", ";
		}
		s.substring(0, s.length() - 1);
		s += "}";
		return s;
	}
}
