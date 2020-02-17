package com.epnj.intelligentpoint.api.controllers;

import com.epnj.intelligentpoint.api.dtos.CompanyDto;
import com.epnj.intelligentpoint.api.entities.Company;
import com.epnj.intelligentpoint.api.response.Response;
import com.epnj.intelligentpoint.api.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
public class CompanyController {

    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

    private CompanyService companyService;

    public CompanyController() {

    }

    /**
     * Return a company given a cnpj
     *
     * @param cnpj
     * @return ResponseEntity
     */
    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<Response<CompanyDto>> findByCnpj(@PathVariable("cnpj") String cnpj) {
        log.info("Finding company by CNPJ: {}", cnpj);
        Response<CompanyDto> response = new Response<CompanyDto>();
        Optional<Company> company = companyService.findByCnpj(cnpj);
        
        if (!company.isPresent()){
            log.info("Company not found to cnpj: {}", cnpj);
            response.getErros().add("Company not found to cnpj " + cnpj);
            return ResponseEntity.badRequest().body(response);
        }
        
        response.setData(this.convertCompanyDto(company.get()));
        return ResponseEntity.ok(response);
    }

    private CompanyDto convertCompanyDto(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setCnpj(company.getCnpj());
        companyDto.setCorporateName(company.getCorporateName());
        return companyDto;
    }
}
