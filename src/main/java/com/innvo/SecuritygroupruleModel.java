package com.innvo;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.innvo.RuledirectiontypeModel;

import com.innvo.RuletypeModel;

import com.innvo.ProtocolModel;

/**
 * A Securitygrouprule.
 */
@Document(indexName = "securitygrouprule")
public class SecuritygroupruleModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private RuledirectiontypeModel ruledirectiontype;
    private RuletypeModel ruletype;
    private ProtocolModel protocol;
    private String iprange;
    private Integer fromport;
    private Integer toport;
    private String status;
    private String lastmodifiedby;
    private ZonedDateTime lastmodifieddatetime;
    private String domain;

    private SecuritygroupModel securitygroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RuledirectiontypeModel getRuledirectiontype() {
        return ruledirectiontype;
    }

    public void setRuledirectiontype(RuledirectiontypeModel ruledirectiontype) {
        this.ruledirectiontype = ruledirectiontype;
    }

    public RuletypeModel getRuletype() {
        return ruletype;
    }

    public void setRuletype(RuletypeModel ruletype) {
        this.ruletype = ruletype;
    }

    public ProtocolModel getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolModel protocol) {
        this.protocol = protocol;
    }

    public String getIprange() {
        return iprange;
    }

    public void setIprange(String iprange) {
        this.iprange = iprange;
    }

    public Integer getFromport() {
        return fromport;
    }

    public void setFromport(Integer fromport) {
        this.fromport = fromport;
    }

    public Integer getToport() {
        return toport;
    }

    public void setToport(Integer toport) {
        this.toport = toport;
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

    public SecuritygroupModel getSecuritygroup() {
        return securitygroup;
    }

    public void setSecuritygroup(SecuritygroupModel securitygroup) {
        this.securitygroup = securitygroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SecuritygroupruleModel securitygrouprule = (SecuritygroupruleModel) o;
        if(securitygrouprule.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, securitygrouprule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Securitygrouprule{" +
            "id=" + id +
            ", ruledirectiontype='" + ruledirectiontype + "'" +
            ", ruletype='" + ruletype + "'" +
            ", protocol='" + protocol + "'" +
            ", iprange='" + iprange + "'" +
            ", fromport='" + fromport + "'" +
            ", toport='" + toport + "'" +
            ", status='" + status + "'" +
            ", lastmodifiedby='" + lastmodifiedby + "'" +
            ", lastmodifieddatetime='" + lastmodifieddatetime + "'" +
            ", domain='" + domain + "'" +
            '}';
    }
}
