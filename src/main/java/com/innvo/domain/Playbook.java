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
 * A Playbook.
 */
@Entity
@Table(name = "playbook")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "playbook")
public class Playbook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "playbook")
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

    public Playbook name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Playbookplaybookcomponentmbr> getPlaybookplaybookcomponentmbrs() {
        return playbookplaybookcomponentmbrs;
    }

    public Playbook playbookplaybookcomponentmbrs(Set<Playbookplaybookcomponentmbr> playbookplaybookcomponentmbrs) {
        this.playbookplaybookcomponentmbrs = playbookplaybookcomponentmbrs;
        return this;
    }

    public Playbook addPlaybookplaybookcomponentmbr(Playbookplaybookcomponentmbr playbookplaybookcomponentmbr) {
        playbookplaybookcomponentmbrs.add(playbookplaybookcomponentmbr);
        playbookplaybookcomponentmbr.setPlaybook(this);
        return this;
    }

    public Playbook removePlaybookplaybookcomponentmbr(Playbookplaybookcomponentmbr playbookplaybookcomponentmbr) {
        playbookplaybookcomponentmbrs.remove(playbookplaybookcomponentmbr);
        playbookplaybookcomponentmbr.setPlaybook(null);
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
        Playbook playbook = (Playbook) o;
        if(playbook.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, playbook.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Playbook{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
