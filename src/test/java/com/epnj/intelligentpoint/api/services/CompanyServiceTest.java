package com.epnj.intelligentpoint.api.services;

import com.epnj.intelligentpoint.api.entities.Company;
import com.epnj.intelligentpoint.api.repositories.CompanyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompanyServiceTest {

    @MockBean
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    private static final String CNPJ = "23545843095032";

    @Before
    public void setUp() throws Exception{
        BDDMockito.given(this.companyRepository.findByCnpj(Mockito.anyString())).willReturn(new Company());
        BDDMockito.given(this.companyRepository.save(Mockito.any(Company.class))).willReturn(new Company());
    }

    @Test
    public void testFindCompanyByCnpj() {
        Optional<Company> company = this.companyService.findByCnpj(CNPJ);
        Assert.assertTrue(company.isPresent());
    }

    @Test
    public void testPersistCompany() {
        Company company = this.companyService.persist(new Company());
        Assert.assertNotNull(company);
    }
}
