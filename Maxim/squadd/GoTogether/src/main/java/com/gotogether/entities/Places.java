package com.gotogether.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Максим
 */
@Entity
@Table(name = "places")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Places.findAll", query = "SELECT p FROM Places p"),
        @NamedQuery(name = "Places.findByPlaceId", query = "SELECT p FROM Places p WHERE p.placeId = :placeId"),
        @NamedQuery(name = "Places.findByName", query = "SELECT p FROM Places p WHERE p.name = :name")})
public class Places implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "place_id")
    private Integer placeId;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "coordinates")
    private Object coordinates;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placeId")
    private Collection<UserInfo> userInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placeId")
    private Collection<Groups> groupsCollection;

    public Places() {
    }

    public Places(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }

    @XmlTransient
    public Collection<UserInfo> getUserInfoCollection() {
        return userInfoCollection;
    }

    public void setUserInfoCollection(Collection<UserInfo> userInfoCollection) {
        this.userInfoCollection = userInfoCollection;
    }

    @XmlTransient
    public Collection<Groups> getGroupsCollection() {
        return groupsCollection;
    }

    public void setGroupsCollection(Collection<Groups> groupsCollection) {
        this.groupsCollection = groupsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (placeId != null ? placeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Places)) {
            return false;
        }
        Places other = (Places) object;
        if ((this.placeId == null && other.placeId != null) || (this.placeId != null && !this.placeId.equals(other.placeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Places{" +
                "placeId=" + placeId +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", userInfoCollection=" + userInfoCollection +
                ", groupsCollection=" + groupsCollection +
                '}';
    }
}
