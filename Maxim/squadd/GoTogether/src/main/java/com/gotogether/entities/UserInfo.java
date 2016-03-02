package com.gotogether.entities;


import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "user_info")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "UserInfo.findAll", query = "SELECT u FROM UserInfo u"),
        @NamedQuery(name = "UserInfo.findByUserId", query = "SELECT u FROM UserInfo u WHERE u.userId = :userId"),
        @NamedQuery(name = "UserInfo.findByName", query = "SELECT u FROM UserInfo u WHERE u.name = :name"),
        @NamedQuery(name = "UserInfo.findBySurname", query = "SELECT u FROM UserInfo u WHERE u.surname = :surname"),
        @NamedQuery(name = "UserInfo.findByLogin", query = "SELECT u FROM UserInfo u WHERE u.login = :login"),
        @NamedQuery(name = "UserInfo.findByRating", query = "SELECT u FROM UserInfo u WHERE u.rating = :rating")})
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "user_id")
    private Integer userId;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "surname")
    private String surname;
    @Size(max = 45)
    @Column(name = "login")
    private String login;
    @Column(name = "rating")
    private Integer rating;
    @JoinColumn(name = "place_id", referencedColumnName = "place_id")
    @ManyToOne(optional = false)
    private Places placeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prtUserId")
    private Collection<Participants> participantsCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userInfo")
    private Contacts contacts;

    public UserInfo() {
    }

    public UserInfo(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Places getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Places placeId) {
        this.placeId = placeId;
    }

    @XmlTransient
    public Collection<Participants> getParticipantsCollection() {
        return participantsCollection;
    }

    public void setParticipantsCollection(Collection<Participants> participantsCollection) {
        this.participantsCollection = participantsCollection;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserInfo)) {
            return false;
        }
        UserInfo other = (UserInfo) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", rating=" + rating +
                ", placeId=" + placeId +
                ", participantsCollection=" + participantsCollection +
                ", contacts=" + contacts +
                '}';
    }
}
