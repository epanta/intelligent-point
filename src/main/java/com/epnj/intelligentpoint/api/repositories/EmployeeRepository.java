package com.epnj.intelligentpoint.api.repositories;

import com.epnj.intelligentpoint.api.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByCpf(String cpf);
    Employee findByEmail(String email);
    Employee findByCpfOrEmail(String cpf, String email);
}
