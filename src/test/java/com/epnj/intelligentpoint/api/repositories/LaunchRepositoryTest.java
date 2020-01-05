package com.epnj.intelligentpoint.api.repositories;

import com.epnj.intelligentpoint.api.entities.Company;
import com.epnj.intelligentpoint.api.entities.Employee;
import com.epnj.intelligentpoint.api.entities.Launch;
import com.epnj.intelligentpoint.api.enums.ProfileEnum;
import com.epnj.intelligentpoint.api.enums.TypeEnum;
import com.epnj.intelligentpoint.api.utils.PasswordUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LaunchRepositoryTest {

    @Autowired
    private LaunchRepository launchRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Long employeeId;

    @Before
    public void setUp() throws Exception {
        Company company = this.companyRepository.save(getCompanyData());

        Employee employee = this.employeeRepository.save(getEmployeeData(company));
        this.employeeId = employee.getId();

        this.launchRepository.save(getLauchesData(employee));
        this.launchRepository.save(getLauchesData(employee));
    }

    private Launch getLauchesData(Employee employee) {
        Launch launch = new Launch();
        launch.setLaunchDate(new Date());
        launch.setType(TypeEnum.INIT_LUNCH);
        launch.setDescription("Teste");
        launch.setPlace("Recife");
        launch.setEmployee(employee);
        return launch;
    }

    private Company getCompanyData() {
        Company company = new Company();
        company.setCorporateName("Panta S.A.");
        company.setCnpj("2342342352342");
        return company;
    }

    private Employee getEmployeeData(Company company) {
        Employee employee = new Employee();
        employee.setName("Edvaldo Panta");
        employee.setProfile(ProfileEnum.ROLE_USER);
        employee.setPassword(PasswordUtils.generateCrypt("123456"));
        employee.setCpf("23423423566");
        employee.setEmail("edvaldopanta@gmail.com");
        employee.setCompany(company);
        return employee;
    }

    public void tearDown() throws Exception {
        this.companyRepository.deleteAll();
    }

    @Test
    public void testFindLaunchesByEmployeeId() {
        List<Launch> launches = this.launchRepository.findByEmployeeId(employeeId);
        Assert.assertEquals(2, launches.size());
    }

/*    public void testFindLauchesByEmployeeIdPagable() {
        PageRequest pageRequest = new PageRequest(0,10, Sort.sort());
        Page<Launch> lauches = this.launchRepository.findByEmployeeId(employeeId, pageRequest);

        Assert.assertEquals(2, lauches.getTotalElements());
    }*/

}
