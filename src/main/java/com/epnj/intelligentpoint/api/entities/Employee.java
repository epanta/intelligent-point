package com.epnj.intelligentpoint.api.entities;

import com.epnj.intelligentpoint.api.enums.ProfileEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private BigDecimal valueHour;
    private Float workedHours;
    private Float lunchHours;
    private ProfileEnum profile;
    private Date creationDate;
    private Date lastUpdate;
    private Company company;
    private List<Launch> launches;

    public Employee() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "cpf", nullable = false)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Column(name = "value_hour", nullable = true)
    public BigDecimal getValueHour() {
        return valueHour;
    }

    @Transient
    public Optional<BigDecimal> getValueHourOpt(){
        return Optional.ofNullable(valueHour);
    }

    public void setValueHour(BigDecimal valueHour) {
        this.valueHour = valueHour;
    }

    @Column(name = "worked_hours", nullable = true)
    public Float getWorkedHours() {
        return workedHours;
    }

    @Transient
    public Optional<Float> getWorkedHoursOpt() {
        return Optional.ofNullable(workedHours);
    }

    public void setWorkedHours(Float workedHours) {
        this.workedHours = workedHours;
    }

    @Column(name = "lunch_hours", nullable = true)
    public Float getLunchHours() {
        return lunchHours;
    }

    public void setLunchHours(Float lunchHours) {
        this.lunchHours = lunchHours;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "profile", nullable = false)
    public ProfileEnum getProfile() {
        return profile;
    }

    public void setProfile(ProfileEnum profile) {
        this.profile = profile;
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

    @ManyToOne(fetch = FetchType.EAGER)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Launch> getLaunches() {
        return launches;
    }

    public void setLaunches(List<Launch> launches) {
        this.launches = launches;
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
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cpf='" + cpf + '\'' +
                ", valueHour=" + valueHour +
                ", workedHours=" + workedHours +
                ", lunchHours=" + lunchHours +
                ", profile=" + profile +
                ", creationDate=" + creationDate +
                ", lastUpdate=" + lastUpdate +
                ", company=" + company +
                '}';
    }
}
