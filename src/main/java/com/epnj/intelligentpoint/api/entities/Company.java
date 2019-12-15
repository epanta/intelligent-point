package com.epnj.intelligentpoint.api.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "company")
public class Company implements Serializable {
    private Long id;
    private String corporateName;
    private String cnpj;
    private Date creationDate;
    private Date lastUpdate;
    private List<Employee> employees;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "corporate_name", nullable = false)
    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    @Column(name = "cpnj", nullable = false)
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
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
        return "Company{" +
                "id=" + id +
                ", corporateName='" + corporateName + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", creationDate=" + creationDate +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
