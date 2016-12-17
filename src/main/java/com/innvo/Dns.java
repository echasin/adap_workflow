package com.innvo;

import java.util.List;

public class Dns {
	private String source_computer;
	private String computer_resolved;
	private String Timestamp;
	private Integer epochtime;
	
	private List<Dns> dnsEvents;
	
	public List<Dns> getDnsEvents() {
		return dnsEvents;
	}
	public void setDnsEvents(List<Dns> dnsEvents) {
		this.dnsEvents = dnsEvents;
	}
	public String getSource_computer() {
		return source_computer;
	}
	public void setSource_computer(String source_computer) {
		this.source_computer = source_computer;
	}
	public String getComputer_resolved() {
		return computer_resolved;
	}
	public void setComputer_resolved(String computer_resolved) {
		this.computer_resolved = computer_resolved;
	}
	public String getTimestamp() {
		return Timestamp;
	}
	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}
	public Integer getEpochtime() {
		return epochtime;
	}
	public void setEpochtime(Integer epochtime) {
		this.epochtime = epochtime;
	}

	
	
	
	

}
