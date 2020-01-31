package com.epnj.intelligentpoint.api.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class RegisterPJDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private String corporateName;
    private String cnpj;

    public RegisterPJDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "Cannot be empty.")
    @Length(min = 3, max = 200, message = "The name must have between 3 and 200 characters.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "Email cannot be empty.")
    @Length(min = 5, max = 200, message = "Email must have between 5 and 200 characters.")
    @Email(message = "Invalid email.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "CPF cannot be empty.")
    @CPF(message = "Invalid CPF")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NotEmpty(message = "Comporate name cannot be empty.")
    @Length(min = 5, max = 200, message = "Corporate name must have between 5 and 200 characters.")
    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    @NotEmpty(message = "CNPJ cannot be empty.")
    @CNPJ(message = "Invalid CNPJ")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "RegisterPJDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cpf='" + cpf + '\'' +
                ", corporateName='" + corporateName + '\'' +
                ", cpnj='" + cnpj + '\'' +
                '}';
    }
}
