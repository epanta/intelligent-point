package com.epnj.intelligentpoint.api.entities;

import com.epnj.intelligentpoint.api.enums.TypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "launch")
public class Launch implements Serializable {

    private Long id;
    private Date launchDate;
    private String description;
    private String place;
    private Date creationDate;
    private Date lastUpdate;
    private TypeEnum type;
    private Employee employee;

    public Launch() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "launch_date", nullable = false)
    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "place", nullable = false)
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Column(name = "creation_date", nullable = false)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "last_update", nullable = false)
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdate = new Date();
    }

    @PrePersist
    public void prePersist() {
        final Date current = new Date();
        creationDate = current;
        lastUpdate = current;
    }

    @Override
    public String toString() {
        return "Launch{" +
                "id=" + id +
                ", launchDate=" + launchDate +
                ", description='" + description + '\'' +
                ", place='" + place + '\'' +
                ", creationDate=" + creationDate +
                ", lastUpdate=" + lastUpdate +
                ", type=" + type +
                ", employee=" + employee +
                '}';
    }
}
