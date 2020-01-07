package com.epnj.intelligentpoint.api.services;

import com.epnj.intelligentpoint.api.entities.Employee;
import com.epnj.intelligentpoint.api.repositories.EmployeeRepository;
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
public class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Before
    public void setUp() {
        BDDMockito.given(this.employeeRepository.save(Mockito.any(Employee.class))).willReturn(new Employee());
        BDDMockito.given(this.employeeRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Employee()));
        BDDMockito.given(this.employeeRepository.findByEmail(Mockito.anyString())).willReturn(new Employee());
        BDDMockito.given(this.employeeRepository.findByCpf(Mockito.anyString())).willReturn(new Employee());
    }

    @Test
    public void testPersitEmployee() {
        Employee employee = this.employeeService.persist(new Employee());
        Assert.assertNotNull(employee);
    }

    @Test
    public void testFindEmployeeById() {
        Optional<Employee> employee = this.employeeService.findById(1L);
        Assert.assertTrue(employee.isPresent());
    }

    @Test
    public void testFindEmployeeByEmail() {
        Optional<Employee> employee = this.employeeService.findByEmail("edvaldopanta@gmail.com");
        Assert.assertTrue(employee.isPresent());
    }

    @Test
    public void testFindEmployeeByCpf() {
        Optional<Employee> employee = this.employeeService.findByCpf("94997276472");
        Assert.assertTrue(employee.isPresent());
    }
}
