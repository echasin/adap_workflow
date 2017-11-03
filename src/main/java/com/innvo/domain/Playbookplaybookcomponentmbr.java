package com.innvo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Playbookplaybookcomponentmbr.
 */
@Entity
@Table(name = "playbookplaybookcomponentmbr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "playbookplaybookcomponentmbr")
public class Playbookplaybookcomponentmbr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 100)
    @Column(name = "comment", length = 100)
    private String comment;

    @ManyToOne
    @NotNull
    private Playbook playbook;

    @ManyToOne
    @NotNull
    private Playbookcomponent playbookcomponent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public Playbookplaybookcomponentmbr comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Playbook getPlaybook() {
        return playbook;
    }

    public Playbookplaybookcomponentmbr playbook(Playbook playbook) {
        this.playbook = playbook;
        return this;
    }

    public void setPlaybook(Playbook playbook) {
        this.playbook = playbook;
    }

    public Playbookcomponent getPlaybookcomponent() {
        return playbookcomponent;
    }

    public Playbookplaybookcomponentmbr playbookcomponent(Playbookcomponent playbookcomponent) {
        this.playbookcomponent = playbookcomponent;
        return this;
    }

    public void setPlaybookcomponent(Playbookcomponent playbookcomponent) {
        this.playbookcomponent = playbookcomponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Playbookplaybookcomponentmbr playbookplaybookcomponentmbr = (Playbookplaybookcomponentmbr) o;
        if(playbookplaybookcomponentmbr.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, playbookplaybookcomponentmbr.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Playbookplaybookcomponentmbr{" +
            "id=" + id +
            ", comment='" + comment + "'" +
            '}';
    }
}
