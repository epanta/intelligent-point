package com.epnj.intelligentpoint.api.services.impl;

import com.epnj.intelligentpoint.api.entities.Employee;
import com.epnj.intelligentpoint.api.repositories.EmployeeRepository;
import com.epnj.intelligentpoint.api.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee persist(Employee employee) {
        log.info("persist employee: {}", employee);
        return this.employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> findByCpf(String cpf) {
        log.info("find employee by cpf: {}", cpf);
        return Optional.ofNullable(this.employeeRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        log.info("find employee by email: {}", email);
        return Optional.ofNullable(this.employeeRepository.findByEmail(email));
    }

    @Override
    public Optional<Employee> findById(Long id) {
        log.info("find employee by id: {}", id);
        return this.employeeRepository.findById(id);
    }
}
