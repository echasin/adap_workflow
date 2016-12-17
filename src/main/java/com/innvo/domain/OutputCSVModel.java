package com.innvo.domain;

import java.util.Date;
import java.util.List;

public class OutputCSVModel {
	
	private long alertId;
	private long eventId;
	private String Status;
	private String lastModifiedBy;
	private Date lastModifiedDateTime;
	private String domain;
	
	private List<OutputCSVModel> event;
	
	public long getAlertId() {
		return alertId;
	}
	public void setAlertId(long alertId) {
		this.alertId = alertId;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}
	public void setLastModifiedDateTime(Date lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public List<OutputCSVModel> getEvent() {
		return event;
	}
	public void setEvent(List<OutputCSVModel> event) {
		this.event = event;
	}
}
