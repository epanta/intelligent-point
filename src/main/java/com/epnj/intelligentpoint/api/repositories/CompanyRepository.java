package com.epnj.intelligentpoint.api.repositories;

import com.epnj.intelligentpoint.api.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Transactional(readOnly = true)
    Company findByCnpj(String cnpj);
}
