package com.innvo;

import java.util.List;

public class AssetModel {
	
	private long id;
	private List<String> stateCodesValues;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<String> getStateCodesValues() {
		return stateCodesValues;
	}
	public void setStateCodesValues(List<String> stateCodesValues) {
		this.stateCodesValues = stateCodesValues;
	}
}
