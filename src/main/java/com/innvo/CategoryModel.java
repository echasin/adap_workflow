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

/**
 * A Category.
 */
public class CategoryModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String description;
    private String status;
    private String lastmodifiedby;
    private ZonedDateTime lastmodifieddatetime;
    private String domain;
    private RecordtypeModel recordtype;
    private Set<SubcategoryModel> subcategories = new HashSet<>();
    private Set<AssetModel> assets = new HashSet<>();
    private Set<KeyModel> keys = new HashSet<>();

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

    public RecordtypeModel getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(RecordtypeModel recordtype) {
        this.recordtype = recordtype;
    }

    public Set<SubcategoryModel> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<SubcategoryModel> subcategories) {
        this.subcategories = subcategories;
    }

    public Set<AssetModel> getAssets() {
        return assets;
    }

    public void setAssets(Set<AssetModel> assets) {
        this.assets = assets;
    }

    public Set<KeyModel> getKeys() {
        return keys;
    }

    public void setKeys(Set<KeyModel> keys) {
        this.keys = keys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryModel category = (CategoryModel) o;
        if(category.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddatetime='" + lastmodifieddatetime + "'" +
            ", domain='" + domain + "'" +
            '}';
    }
}
