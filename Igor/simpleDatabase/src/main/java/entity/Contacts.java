package entity;

import javax.persistence.*;

/**
 * Created by igoryan on 28.02.16.
 */
@Entity
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String login;
    private long token;

    @OneToOne
    @PrimaryKeyJoinColumn
    private UserInfo user;

    public Contacts() {
    }

    public Contacts(String login, long token) {

        this.login = login;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public long getToken() {
        return token;
    }

    public String getLogin() {
        return login;
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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setToken(long token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", token=" + token +
                '}';
    }
}
