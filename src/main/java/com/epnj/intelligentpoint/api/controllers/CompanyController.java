package com.epnj.intelligentpoint.api.controllers;

import com.epnj.intelligentpoint.api.dtos.CompanyDto;
import com.epnj.intelligentpoint.api.response.Response;
import com.epnj.intelligentpoint.api.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
public class CompanyController {

    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

    private CompanyService companyService;

    public CompanyController() {

    }

    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<Response<CompanyDto>> findByCnpj(@PathVariable("cnpj") String cpnj) {
        log.info("Finding company by CNPJ: {}", cpnj);
        return null;
    }
}
