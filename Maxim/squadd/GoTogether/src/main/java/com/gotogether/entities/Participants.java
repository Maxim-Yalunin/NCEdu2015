package com.gotogether.entities;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Максим
 */
@Entity
@Table(name = "participants")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Participants.findAll", query = "SELECT p FROM Participants p"),
        @NamedQuery(name = "Participants.findById", query = "SELECT p FROM Participants p WHERE p.id = :id")})
public class Participants implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "prt_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private UserInfo prtUserId;
    @JoinColumn(name = "prt_role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Roles prtRoleId;
    @JoinColumn(name = "prt_group_id", referencedColumnName = "group_id")
    @ManyToOne(optional = false)
    private Groups prtGroupId;

    public Participants() {
    }

    public Participants(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInfo getPrtUserId() {
        return prtUserId;
    }

    public void setPrtUserId(UserInfo prtUserId) {
        this.prtUserId = prtUserId;
    }

    public Roles getPrtRoleId() {
        return prtRoleId;
    }

    public void setPrtRoleId(Roles prtRoleId) {
        this.prtRoleId = prtRoleId;
    }

    public Groups getPrtGroupId() {
        return prtGroupId;
    }

    public void setPrtGroupId(Groups prtGroupId) {
        this.prtGroupId = prtGroupId;
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
        if (!(object instanceof Participants)) {
            return false;
        }
        Participants other = (Participants) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Participants{" +
                "id=" + id +
                ", prtUserId=" + prtUserId +
                ", prtRoleId=" + prtRoleId +
                ", prtGroupId=" + prtGroupId +
                '}';
    }
}
