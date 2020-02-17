package com.epnj.intelligentpoint.api.controllers;

import com.epnj.intelligentpoint.api.entities.Company;
import com.epnj.intelligentpoint.api.services.CompanyService;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompanyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    private static final String FIND_COMPANY_CNPJ_URL = "/api/companies/cnpj/";
    private static final Long ID = Long.valueOf(1);
    private static final String CNPJ = "51463645000100";
    private static final String CORPORATE_NAME = "Company XYZ";

    @Test
    public void testFindCompanyInvalidCnpj() throws Exception {
        BDDMockito.given(this.companyService.findByCnpj(Mockito.anyString())).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get(FIND_COMPANY_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").value("Company not found to cnpj " +CNPJ));
    }

    @Test
    public void testFindCompanyValidCnpj() throws Exception {
        BDDMockito.given(this.companyService.findByCnpj(Mockito.anyString())).willReturn(Optional.of(this.getCompanyData()));

        mvc.perform(MockMvcRequestBuilders.get(FIND_COMPANY_CNPJ_URL + CNPJ)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.corporateName", CoreMatchers.equalTo(CORPORATE_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cnpj", CoreMatchers.equalTo(CNPJ)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isEmpty());
    }

    private Company getCompanyData() {
        Company company = new Company();
        company.setId(ID);
        company.setCorporateName(CORPORATE_NAME);
        company.setCnpj(CNPJ);
        return company;
    }


}
