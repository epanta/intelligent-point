package com.epnj.intelligentpoint.api.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class RegisterPFDto {

    private Long Id;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private Optional<String> valueHour = Optional.empty();
    private Optional<String> workedHour = Optional.empty();
    private Optional<String> lunchHour = Optional.empty();
    private String cnpj;

    public RegisterPFDto() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @NotEmpty(message = "Cannot be empty")
    @Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters")
    public String getName() {
        return name;
        
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "Cannot be empty")
    @Length(min = 5, max = 200, message = "Email must contain between 5 and 200 characters")
    @Email(message = "Invalid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Cannot be empty")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Cannot be empty")
    @CPF(message = "Invalid CPF")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Optional<String> getValueHour() {
        return valueHour;
    }

    public void setValueHour(Optional<String> valueHour) {
        this.valueHour = valueHour;
    }

    public Optional<String> getWorkedHour() {
        return workedHour;
    }

    public void setWorkedHour(Optional<String> workedHour) {
        this.workedHour = workedHour;
    }

    public Optional<String> getLunchHour() {
        return lunchHour;
    }

    public void setLunchHour(Optional<String> lunchHour) {
        this.lunchHour = lunchHour;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
