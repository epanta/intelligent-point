package com.epnj.intelligentpoint.api.controllers;

import com.epnj.intelligentpoint.api.dtos.RegisterPFDto;
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
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("api/register-pf")
@CrossOrigin(origins = "*")
public class RegisterPFController {
    private static final Logger log = LoggerFactory.getLogger(RegisterPFController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    public RegisterPFController() {
    }

    /**
     * Register a employee PF into the system
     *
     * @param registerPFDto
     * @para result
     * @ResponseEntity<Response<RegisterPFDto>>
     * @throws java.security.NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<RegisterPFDto>> register(@Valid @RequestBody RegisterPFDto registerPFDto,
                                                            BindingResult result) throws NoSuchAlgorithmException {
        log.info("Registering PF: {}", registerPFDto.toString());
        Response<RegisterPFDto> response = new Response<RegisterPFDto>();

        validateData(registerPFDto, result);
        Employee employee = this.convertDtoToEmployee(registerPFDto, result);

        if (result.hasErrors()) {
            log.error("Error validating data from PF record: {}", result.getAllErrors());
            result.getAllErrors().forEach(objectError -> response.getErros().add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Company> company = this.companyService.findByCnpj(registerPFDto.getCnpj());
        company.ifPresent(comp -> employee.setCompany(comp));
        this.employeeService.persist(employee);

        response.setData(this.convertRegisterPFDto(employee));
        return ResponseEntity.ok(response);
    }

    /**
     *
     * @param employee
     * @return
     */
    private RegisterPFDto convertRegisterPFDto(Employee employee) {
        RegisterPFDto registerPFDto = new RegisterPFDto();
        registerPFDto.setId(employee.getId());
        registerPFDto.setName(employee.getName());
        registerPFDto.setEmail(employee.getEmail());
        registerPFDto.setCpf(employee.getCpf());
        registerPFDto.setCnpj(employee.getCompany().getCnpj());
        employee.getWorkedHoursOpt().ifPresent(hours -> registerPFDto.setWorkedHour(Optional.of(Float.toString(hours))));
        employee.getValueHourOpt().ifPresent(hours -> registerPFDto.setValueHour(Optional.of(hours.toString())));
        employee.getLunchHoursOpt().ifPresent(hoursLunch -> registerPFDto.setLunchHour(Optional.of(Float.toString(hoursLunch))));
        return registerPFDto;
    }

    private Employee convertDtoToEmployee(RegisterPFDto registerPFDto, BindingResult result) {
        Employee employee = new Employee();
        employee.setName(registerPFDto.getName());
        employee.setEmail(registerPFDto.getEmail());
        employee.setCpf(registerPFDto.getCpf());
        employee.setProfile(ProfileEnum.ROLE_USER);
        employee.setPassword(PasswordUtils.generateCrypt(registerPFDto.getPassword()));
        registerPFDto.getWorkedHour().ifPresent(s -> employee.setLunchHours(Float.valueOf(s)));
        registerPFDto.getLunchHour().ifPresent(s -> employee.setLunchHours(Float.valueOf(s)));
        registerPFDto.getValueHour().ifPresent(s -> employee.setValueHour(new BigDecimal(s)));
        return employee;
    }

    private void validateData(RegisterPFDto registerPFDto, BindingResult result) {
        Optional<Company> company = this.companyService.findByCnpj(registerPFDto.getCnpj());
        if (!company.isPresent()) {
            result.addError(new ObjectError("company", "Company not registered."));
        }

        this.employeeService.findByCpf(registerPFDto.getCpf())
                .ifPresent(employee -> result.addError(new ObjectError("employee", "CPF already exist")));

        this.employeeService.findByEmail(registerPFDto.getEmail())
                .ifPresent(employee -> result.addError(new ObjectError("employee", "Email already exist")));
    }


}
