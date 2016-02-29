package entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by igoryan on 28.02.16.
 */
@Entity
public class Groups {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    String description;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_time")
    Date DateTime;
    @OneToOne
    @JoinColumn(name = "type_id")
    private GroupTypes groupType;

    @OneToOne
    @JoinColumn(name = "place_id")
    private Places Place;

    @OneToOne(mappedBy = "group")
    private Participants participant;

    public Groups() {
    }

    public Groups(String name, String description, Date dateTime, GroupTypes groupType, Places place, Participants participant) {
        this.name = name;
        this.description = description;
        DateTime = dateTime;
        this.groupType = groupType;
        Place = place;
        this.participant = participant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }

    public GroupTypes getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupTypes groupType) {
        this.groupType = groupType;
    }

    public Places getPlace() {
        return Place;
    }

    public void setPlace(Places place) {
        Place = place;
    }

    public Participants getParticipant() {
        return participant;
    }

    public void setParticipant(Participants participant) {
        this.participant = participant;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", DateTime=" + DateTime +
                ", groupType=" + groupType +
                ", Place=" + Place +
                ", participant=" + participant +
                '}';
    }
}
