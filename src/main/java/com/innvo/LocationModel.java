package com.innvo;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
public class LocationModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String recordtype;
    private String addressline1;
    private String addressline2;
    private String statecode;
    private String status;
    private String lastmodifiedby;
    private String lastmodifieddatetime;
    private String domain;
    private AssetModel asset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
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

    public AssetModel getAsset() {
        return asset;
    }

    public void setAsset(AssetModel asset) {
        this.asset = asset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationModel location = (LocationModel) o;
        if(location.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "{" +
            "id=" + id +
            ", recordtype='" + recordtype + "'" +
            ", addressline1='" + addressline1 + "'" +
            ", addressline2='" + addressline2 + "'" +
            ", statecode='" + statecode + "'" +
            ", status='" + status + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddatetime='" + lastmodifieddatetime + "'" +
            ", domain='" + domain + "'" +
            '}';
    }
}
