package entity;

import javax.persistence.*;

/**
 * Created by igoryan on 28.02.16.
 */
@Entity
public class Participants {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @OneToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Roles role;

    public Participants() {
    }

    public Participants(UserInfo user, Groups group, Roles role) {
        this.user = user;
        this.group = group;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Participants{" +
                "id=" + id +
                ", user=" + user +
                ", group=" + group +
                ", role=" + role +
                '}';
    }
}
