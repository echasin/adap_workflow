package com.innvo;


import java.time.ZonedDateTime;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import com.innvo.domain.Status;

public class ScoreModel implements Serializable {

	private Long id;
    private Double value;
    private String text;
    private String rulefilename;
    private String rulename;
    private String runid;
    private ZonedDateTime rundate;
    private String ruleversion;
    private String details;
    private Status status;
    private String lastmodifiedby;
    private ZonedDateTime lastmodifieddate;
    private String domain;


    @ManyToOne
    private AssetModel asset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRulename() {
        return rulename;
    }

    public void setRulename(String rulename) {
        this.rulename = rulename;
    }

    public String getRuleversion() {
        return ruleversion;
    }

    public void setRuleversion(String ruleversion) {
        this.ruleversion = ruleversion;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(String lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public ZonedDateTime getLastmodifieddate() {
        return lastmodifieddate;
    }

    public void setLastmodifieddate(ZonedDateTime lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
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

        ScoreModel score = (ScoreModel) o;

        if ( ! Objects.equals(id, score.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Score{" +
            "id=" + id +
            ", value='" + value + "'" +
            ", text='" + text + "'" +
            ", rulename='" + rulename + "'" +
            ", ruleversion='" + ruleversion + "'" +
            ", details='" + details + "'" +
            ", status='" + status + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddate='" + lastmodifieddate + "'" +
            ", domain='" + domain + "'" +
            '}';
    }

    /**
     * @param runid the runid to set
     */
    public void setRunid(String runid) {
        this.runid = runid;
    }

    /**
     * @param rundate the rundate to set
     */
    public void setRundate(ZonedDateTime rundate) {
        this.rundate = rundate;
    }

    /**
     * @return the runid
     */
    public String getRunid() {
        return runid;
    }

    /**
     * @return the rundate
     */
    public ZonedDateTime getRundate() {
        return rundate;
    }


    /**
     * @return the rulefilename
     */
    public String getRulefilename() {
        return rulefilename;
    }

    /**
     * @param rulefilename the rulefilename to set
     */
    public void setRulefilename(String rulefilename) {
        this.rulefilename = rulefilename;
    }
}
