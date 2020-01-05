package com.epnj.intelligentpoint.api.repositories;

import com.epnj.intelligentpoint.api.entities.Company;
import com.epnj.intelligentpoint.api.entities.Employee;
import com.epnj.intelligentpoint.api.enums.ProfileEnum;
import com.epnj.intelligentpoint.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private static final String EMAIL = "email@email.com";
    private static final String CPF = "94997276472";

    @Before
    public void setUp() throws Exception {
        Company company = this.companyRepository.save(getCompanyData());
        this.employeeRepository.save(getEmployeeData(company));
    }

    @After
    public final void tearDown() {
        this.companyRepository.deleteAll();
    }

    @Test
    public void testFindEmployeeByEmail() {
        Employee employee = this.employeeRepository.findByEmail(EMAIL);
        Assert.assertEquals(EMAIL, employee.getEmail());
    }

    @Test
    public void testFindEmployeeByCPF() {
        Employee employee = this.employeeRepository.findByCpf(CPF);
        Assert.assertEquals(CPF, employee.getCpf());
    }

    @Test
    public void testFindEmployeeByCPFAndEmail() {
        Employee employee = this.employeeRepository.findByCpfOrEmail(CPF, EMAIL);
        Assert.assertNotNull(employee);
    }

    @Test
    public void testFindEmployeeByEmailOrCpfWithInvalidEmail() {
        Employee employee = this.employeeRepository.findByCpfOrEmail(CPF, "invalid@email.com");
        Assert.assertNotNull(employee);
    }

    @Test
    public void testFindEmployeeByEmailOrCpfWithInvalidCpf() {
        Employee employee = this.employeeRepository.findByCpfOrEmail("123456789", EMAIL);
        Assert.assertNotNull(employee);
    }

    private Employee getEmployeeData(Company company) throws NoSuchAlgorithmException {
        Employee employee = new Employee();
        employee.setName("Edvaldo Panta");
        employee.setProfile(ProfileEnum.ROLE_USER);
        employee.setPassword(PasswordUtils.generateCrypt("123456"));
        employee.setCpf(CPF);
        employee.setEmail(EMAIL);
        employee.setCompany(company);
        return employee;
    }

    private Company getCompanyData() {
        Company company = new Company();
        company.setCorporateName("Example");
        company.setCnpj("2112345345345");
        return company;
    }


}
