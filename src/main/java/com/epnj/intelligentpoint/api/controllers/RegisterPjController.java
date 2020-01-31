package com.epnj.intelligentpoint.api.controllers;

import com.epnj.intelligentpoint.api.dtos.RegisterPJDto;
import com.epnj.intelligentpoint.api.entities.Company;
import com.epnj.intelligentpoint.api.entities.Employee;
import com.epnj.intelligentpoint.api.enums.ProfileEnum;
import com.epnj.intelligentpoint.api.response.Response;
import com.epnj.intelligentpoint.api.services.CompanyService;
import com.epnj.intelligentpoint.api.services.EmployeeService;
import com.epnj.intelligentpoint.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/register-pj")
@CrossOrigin(origins = "*")
public class RegisterPjController {

    private static final Logger log = LoggerFactory.getLogger(RegisterPjController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    public RegisterPjController() {

    }

    /**
     * Create a PJ in the system
     *
     * @param registerPJDto
     * @param result
     * @return ResponseEntity<Response < RegisterPjDto>>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<RegisterPJDto>> create(@Valid @RequestBody RegisterPJDto registerPJDto,
                                                          BindingResult result) throws NoSuchAlgorithmException {
        log.info("Creating PJ: {}", registerPJDto.toString());
        Response<RegisterPJDto> response = new Response<RegisterPJDto>();
        validateData(registerPJDto, result);
        Company company = this.convertDtoToCompany(registerPJDto);
        Employee employee = this.convertDtoToEmployee(registerPJDto, result);

        if (result.hasErrors()) {
            log.error("Error validing PJ register data: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.companyService.persist(company);
        employee.setCompany(company);
        this.employeeService.persist(employee);

        response.setData(this.convertRegisterPjDto(employee));

        return ResponseEntity.ok(response);
    }

    private RegisterPJDto convertRegisterPjDto(Employee employee) {

        RegisterPJDto registerPJDto = new RegisterPJDto();
        registerPJDto.setId(employee.getId());
        registerPJDto.setName(employee.getName());
        registerPJDto.setEmail(employee.getEmail());
        registerPJDto.setCpf(employee.getCpf());
        registerPJDto.setCorporateName(employee.getCompany().getCorporateName());

        return registerPJDto;
    }

    private Company convertDtoToCompany(RegisterPJDto registerPJDto) {
        Company company = new Company();
        company.setCnpj(registerPJDto.getCnpj());
        company.setCorporateName(registerPJDto.getCorporateName());

        return company;

    }

    private Employee convertDtoToEmployee(RegisterPJDto registerPJDto, BindingResult result)
            throws NoSuchAlgorithmException {
        Employee employee = new Employee();
        employee.setName(registerPJDto.getName());
        employee.setEmail(registerPJDto.getEmail());
        employee.setCpf(registerPJDto.getCpf());
        employee.setProfile(ProfileEnum.ROLE_ADMIN);
        employee.setPassword(PasswordUtils.generateCrypt(registerPJDto.getPassword()));

        return employee;
    }

    private void validateData(RegisterPJDto registerPJDto, BindingResult result) {

        this.companyService.findByCnpj(registerPJDto.getCnpj())
                .ifPresent(company -> result.addError(new ObjectError("company", "Company already exist.")));

        this.employeeService.findByCpf(registerPJDto.getCpf())
                .ifPresent(employee -> result.addError(new ObjectError("employee", "CPF already exist.")));

        this.employeeService.findByEmail(registerPJDto.getEmail())
                .ifPresent(employee -> result.addError(new ObjectError("employee", "Email already exist.")));

    }
}
