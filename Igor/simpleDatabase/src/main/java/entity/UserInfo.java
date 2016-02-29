package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by igoryan on 28.02.16.
 */
@Entity(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String surname;
    private String login;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private float rating;

    @OneToMany(mappedBy = "user")
    private List<Participants> participant;

    @OneToMany
    private List<Places> places;

    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Contacts contact;

    public UserInfo() {
    }

    public UserInfo(String name, String surname, String login, Date birthday, float rating, List<Participants> participant, List<Places> places) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.birthday = birthday;
        this.rating = rating;
        this.participant = participant;
        this.places = places;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Participants> getParticipant() {
        return participant;
    }

    public void setParticipant(List<Participants> participant) {
        this.participant = participant;
    }

    public List<Places> getPlaces() {
        return places;
    }

    public void setPlaces(List<Places> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", birthday=" + birthday +
                ", rating=" + rating +
                ", participant=" + participant +
                ", places=" + places +
                '}';
    }
}
