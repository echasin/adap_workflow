package com.innvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innvo.IdentifierModel;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class AssetModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String nameshort;
    private String description;
    private String status;
    private String lastmodifiedby;
    private String lastmodifieddatetime;
    private String domain;
    private Set<LocationModel> locations = new HashSet<>();
    private Set<ScoreModel> scores = new HashSet<>();
    private Set<CategoryModel> categories = new HashSet<>();
    private Set<SubcategoryModel> subcategories = new HashSet<>();
    private RecordtypeModel recordtype;
    private Set<AssetassetmbrModel> assetparents = new HashSet<>();
    private Set<AssetassetmbrModel> assetassetmbrchildren = new HashSet<>();
    private SecuritygroupModel securitygroup;
    private Set<IdentifierModel> identifiers = new HashSet<>();

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
    
    public String getNameshort() {
 		return nameshort;
 	}

 	public void setNameshort(String nameshort) {
 		this.nameshort = nameshort;
 	}

 	public String getDescription() {
 		return description;
 	}

 	public void setDescription(String description) {
 		this.description = description;
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

    public Set<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryModel> categories) {
        this.categories = categories;
    }

    public Set<SubcategoryModel> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<SubcategoryModel> subcategories) {
        this.subcategories = subcategories;
    }

    public RecordtypeModel getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(RecordtypeModel recordtype) {
        this.recordtype = recordtype;
    }

    public Set<AssetassetmbrModel> getAssetparents() {
        return assetparents;
    }

    public void setAssetparents(Set<AssetassetmbrModel> assetassetmbrs) {
        this.assetparents = assetassetmbrs;
    }

    public Set<AssetassetmbrModel> getAssetassetmbrchildren() {
        return assetassetmbrchildren;
    }

    public void setAssetassetmbrchildren(Set<AssetassetmbrModel> assetassetmbrs) {
        this.assetassetmbrchildren = assetassetmbrs;
    }

    public SecuritygroupModel getSecuritygroup() {
        return securitygroup;
    }

    public void setSecuritygroup(SecuritygroupModel securitygroup) {
        this.securitygroup = securitygroup;
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
        return "Asset{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", status='" + status + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddatetime='" + lastmodifieddatetime + "'" +
            ", domain='" + domain + "'" +
            '}';
    }
}
