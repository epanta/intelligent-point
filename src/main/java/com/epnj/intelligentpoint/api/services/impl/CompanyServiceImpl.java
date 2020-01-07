package com.epnj.intelligentpoint.api.services.impl;

import com.epnj.intelligentpoint.api.entities.Company;
import com.epnj.intelligentpoint.api.repositories.CompanyRepository;
import com.epnj.intelligentpoint.api.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Optional<Company> findByCnpj(String cnpj) {
        log.info("Finding a company to CNPJ {}", cnpj);
        return Optional.ofNullable(companyRepository.findByCnpj(cnpj));
    }

    @Override
    public Company persist(Company company) {
        log.info("Persist company: {}", company);
        return this.companyRepository.save(company);
    }
}
