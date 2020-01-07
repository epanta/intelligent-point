package com.epnj.intelligentpoint.api.services;

import com.epnj.intelligentpoint.api.entities.Company;

import java.util.Optional;

public interface CompanyService {

    /**
     * Find company by cnpf
     *
     * @param cnpj
     * @return Optional<Company>
     */
    Optional<Company> findByCnpj(String cnpj);

    /**
     * Persist company
     *
     * @param company
     * @return Company
     */
    Company persist(Company company);
}
