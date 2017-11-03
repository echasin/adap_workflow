package com.innvo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Playbookcomponent.
 */
@Entity
@Table(name = "playbookcomponent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "playbookcomponent")
public class Playbookcomponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "playbookcomponent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Playbookplaybookcomponentmbr> playbookplaybookcomponentmbrs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Playbookcomponent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Playbookplaybookcomponentmbr> getPlaybookplaybookcomponentmbrs() {
        return playbookplaybookcomponentmbrs;
    }

    public Playbookcomponent playbookplaybookcomponentmbrs(Set<Playbookplaybookcomponentmbr> playbookplaybookcomponentmbrs) {
        this.playbookplaybookcomponentmbrs = playbookplaybookcomponentmbrs;
        return this;
    }

    public Playbookcomponent addPlaybookplaybookcomponentmbr(Playbookplaybookcomponentmbr playbookplaybookcomponentmbr) {
        playbookplaybookcomponentmbrs.add(playbookplaybookcomponentmbr);
        playbookplaybookcomponentmbr.setPlaybookcomponent(this);
        return this;
    }

    public Playbookcomponent removePlaybookplaybookcomponentmbr(Playbookplaybookcomponentmbr playbookplaybookcomponentmbr) {
        playbookplaybookcomponentmbrs.remove(playbookplaybookcomponentmbr);
        playbookplaybookcomponentmbr.setPlaybookcomponent(null);
        return this;
    }

    public void setPlaybookplaybookcomponentmbrs(Set<Playbookplaybookcomponentmbr> playbookplaybookcomponentmbrs) {
        this.playbookplaybookcomponentmbrs = playbookplaybookcomponentmbrs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Playbookcomponent playbookcomponent = (Playbookcomponent) o;
        if(playbookcomponent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, playbookcomponent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Playbookcomponent{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
