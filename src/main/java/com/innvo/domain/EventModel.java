package com.innvo.domain;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A Event.
 */
public class EventModel implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Long id;
    private String name;
    private String description;
    private String category;
    private String subcategory;
    private String subtype;
    private String type;
    private Date startdatetime;
    private String enddatetime;
    private String status;
    private String lastmodifiedby;
    private Date lastmodifieddatetime;
    private String domain;

    private List<EventModel> listOfEvent;

    public List<EventModel> getListOfEvent() {
		return listOfEvent;
	}

	public void setListOfEvent(List<EventModel> listOfEvent) {
		this.listOfEvent = listOfEvent;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(Date startdatetime) {

        this.startdatetime = startdatetime;
    }

    public String getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(String enddatetime) {
        this.enddatetime = enddatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public Date getLastmodifieddatetime() {
        return lastmodifieddatetime;
    }

    public void setLastmodifieddatetime(Date lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventModel event = (EventModel) o;
        if(event.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", category='" + category + "'" +
            ", subcategory='" + subcategory + "'" +
            ", subtype='" + subtype + "'" +
            ", type='" + type + "'" +
            ", startdatetime='" + startdatetime + "'" +
            ", enddatetime='" + enddatetime + "'" +
            ", status='" + status + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddatetime='" + lastmodifieddatetime + "'" +
            ", domain='" + domain + "'" +
            '}';
    }
    
}
