package com.innvo;


import javax.persistence.*;
import java.time.ZonedDateTime;


public class ScoreModel{
    private Long id;
    private String recordtype;
    private String status;
    private String lastmodifiedby;
    private String lastmodifieddatetime;
    private String domain;
    private Long value;
    //private Long assetId;

   @ManyToOne
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

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

	/*public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}*/
    

   public AssetModel getAsset() {
        return asset;
    }

    public void setAsset(AssetModel asset) {
        this.asset = asset;
    }
}
