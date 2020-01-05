package com.epnj.intelligentpoint.api.repositories;

import com.epnj.intelligentpoint.api.entities.Company;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private static final String CNPJ = "60.6594.630/001-91";

    @Before
    public void setUp() throws Exception {
        Company company = new Company();
        company.setCorporateName("Companys name");
        company.setCnpj(CNPJ);
        this.companyRepository.save(company);
    }

    @After
    public final void tearDown() {
        this.companyRepository.deleteAll();
    }

    @Test
    public void testBuscarPorCNPJ() {
        Company company = this.companyRepository.findByCnpj(CNPJ);
        Assert.assertEquals(CNPJ, company.getCnpj());
    }
}
