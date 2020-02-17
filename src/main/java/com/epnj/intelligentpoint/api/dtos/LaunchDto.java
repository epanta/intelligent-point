package com.epnj.intelligentpoint.api.dtos;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class LaunchDto {

    private Optional<Long> id = Optional.empty();
    private String launchDte;
    private String type;
    private String description;
    private String place;
    private Long employeeId;

    public LaunchDto() {

    }

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    @NotEmpty(message = "Date cannot be empty")
    public String getLaunchDte() {
        return launchDte;
    }

    public void setLaunchDte(String launchDte) {
        this.launchDte = launchDte;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "LaunchDto{" +
                "id=" + id +
                ", launchDte='" + launchDte + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", place='" + place + '\'' +
                ", employeeId=" + employeeId +
                '}';
    }
}
