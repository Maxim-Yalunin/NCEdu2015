package com.gotogether.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Максим
 */
@Entity
@Table(name = "contacts")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Contacts.findAll", query = "SELECT c FROM Contacts c"),
        @NamedQuery(name = "Contacts.findById", query = "SELECT c FROM Contacts c WHERE c.id = :id"),
        @NamedQuery(name = "Contacts.findByLogin", query = "SELECT c FROM Contacts c WHERE c.login = :login"),
        @NamedQuery(name = "Contacts.findByToken", query = "SELECT c FROM Contacts c WHERE c.token = :token")})
public class Contacts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "login")
    private String login;
    @Column(name = "token")
    private Integer token;
    @JoinColumn(name = "id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private UserInfo userInfo;

    public Contacts() {
    }

    public Contacts(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contacts)) {
            return false;
        }
        Contacts other = (Contacts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", token=" + token +
                ", userInfo=" + userInfo +
                '}';
    }
}
