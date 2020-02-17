package com.epnj.intelligentpoint.api.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class EmployeeDto {

    private Long id;
    private String name;
    private String email;
    private Optional<String> password = Optional.empty();
    private Optional<String> valueHour = Optional.empty();
    private Optional<String> workedHours = Optional.empty();
    private Optional<String> lunchHours = Optional.empty();

    public EmployeeDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long Id){
        this.id = id;
    }

    @NotEmpty(message = "Name cannot be empty")
    @Length(min = 3, max = 200, message = "Name must to cotain between 3 and 200 characters")
    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "Email cannot be empty")
    @Length(min = 3, max = 200, message = "Email must to cotain between 3 and 200 characters")
    @Email(message = "Invallid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(Optional<String> password) {
        this.password = password;
    }

    public Optional<String> getValueHour() {
        return valueHour;
    }

    public void setValueHour(Optional<String> valueHour) {
        this.valueHour = valueHour;
    }

    public Optional<String> getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(Optional<String> workedHours) {
        this.workedHours = workedHours;
    }

    public Optional<String> getLunchHours() {
        return lunchHours;
    }

    public void setLunchHours(Optional<String> lunchHours) {
        this.lunchHours = lunchHours;
    }
}
