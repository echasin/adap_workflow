package com.innvo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A Alert.
 */

public class AlertModel implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Long id;
    private String name;
    private String description;
    private String category;
    private String subcategory;
    private String subtype;
    private String type;
    private ZonedDateTime startdatetime;
    private ZonedDateTime enddatetime;
    private String status;
    private String lastmodifiedby;
    private ZonedDateTime lastmodifieddatetime;
    private String domain;
    private Integer count;

    private Set<IdentifierModel> identifiers = new HashSet<>();
    
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
    
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public ZonedDateTime getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(ZonedDateTime startdatetime) {
        this.startdatetime = startdatetime;
    }

    public ZonedDateTime getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(ZonedDateTime enddatetime) {
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

    public ZonedDateTime getLastmodifieddatetime() {
        return lastmodifieddatetime;
    }

    public void setLastmodifieddatetime(ZonedDateTime lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Set<IdentifierModel> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Set<IdentifierModel> identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AlertModel alert = (AlertModel) o;
        if(alert.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alert.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Alert{" +
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
