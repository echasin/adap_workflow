package com.innvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import com.innvo.ObjecttypeModel;

/**
 * A Recordtype.
 */
@Document(indexName = "recordtype")
public class RecordtypeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private ObjecttypeModel objecttype;
    private String name;
    private String description;
    private String status;
    private String lastmodifiedby;
    private String lastmodifieddatetime;
    private String domain;
    private Set<CategoryModel> categories = new HashSet<>();
    private Set<AssetModel> assets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ObjecttypeModel getObjecttype() {
        return objecttype;
    }

    public void setObjecttype(ObjecttypeModel objecttype) {
        this.objecttype = objecttype;
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

    public Set<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryModel> categories) {
        this.categories = categories;
    }

    public Set<AssetModel> getAssets() {
        return assets;
    }

    public void setAssets(Set<AssetModel> assets) {
        this.assets = assets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecordtypeModel recordtype = (RecordtypeModel) o;
        if(recordtype.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, recordtype.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Recordtype{" +
            "id=" + id +
            ", objecttype='" + objecttype + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddatetime='" + lastmodifieddatetime + "'" +
            ", domain='" + domain + "'" +
            '}';
    }
}
