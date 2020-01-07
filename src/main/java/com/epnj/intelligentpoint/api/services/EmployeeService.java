package com.epnj.intelligentpoint.api.services;

import com.epnj.intelligentpoint.api.entities.Employee;

import java.util.Optional;

public interface EmployeeService {

    /**
     * Persist an employee in database
     *
     * @param employee
     * @return Employee
     */
    Employee persist(Employee employee);

    /**
     * Find employee by cpf
     *
     * @param cpf
     * @return Optional<Employee>
     */
    Optional<Employee> findByCpf(String cpf);

    /**
     * Find employee by email
     *
     * @param email
     * @return Optional<Employee>
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Find employee by id
     *
     * @param Id
     * @return Optional<Employee>
     */
    Optional<Employee> findById(Long Id);

}
