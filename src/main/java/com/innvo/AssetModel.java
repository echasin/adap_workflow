package com.innvo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class AssetModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String recordtype;
    private String status;
    private String lastmodifiedby;
    private String lastmodifieddatetime;
    private String domain;
    private Set<LocationModel> locations = new HashSet<>();
    private Set<ScoreModel> scores = new HashSet<>();

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

    public String getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype;
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

    public String getLastmodifieddatetime() {
        return lastmodifieddatetime;
    }

    public void setLastmodifieddatetime(String lastmodifieddatetime) {
        this.lastmodifieddatetime = lastmodifieddatetime;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Set<LocationModel> getLocations() {
        return locations;
    }

    public void setLocations(Set<LocationModel> locations) {
        this.locations = locations;
    }

    public Set<ScoreModel> getScores() {
        return scores;
    }

    public void setScores(Set<ScoreModel> scores) {
        this.scores = scores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssetModel asset = (AssetModel) o;
        if(asset.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, asset.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", recordtype='" + recordtype + "'" +
            ", status='" + status + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddatetime='" + lastmodifieddatetime + "'" +
            ", domain='" + domain + "'" +
            '}';
    }
}