package com.epnj.intelligentpoint.api.controllers;

import com.epnj.intelligentpoint.api.dtos.EmployeeDto;
import com.epnj.intelligentpoint.api.entities.Employee;
import com.epnj.intelligentpoint.api.response.Response;
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
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController() {

    }

    @PutMapping
    public ResponseEntity<Response<EmployeeDto>> update(@PathVariable("id") Long id,
            @Valid @RequestBody EmployeeDto employeeDto, BindingResult result) throws NoSuchAlgorithmException {
        log.info("Updating employee: {}", employeeDto.toString());
        Response<EmployeeDto> response = new Response<EmployeeDto>();

        Optional<Employee> employee = this.employeeService.findById(id);
        if (!employee.isPresent()) {
            result.addError(new ObjectError("employee", "Employee not found."));
        }
        this.updatingEmployeeData(employee.get(), employeeDto, result);

        if (result.hasErrors()) {
            log.error("Error validating employee: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        
        this.employeeService.persist(employee.get());
        response.setData(this.convertEmployeeDto(employee.get()));
        return ResponseEntity.ok(response);
    }

    private EmployeeDto convertEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setName(employee.getName());
        employee.getLunchHoursOpt().ifPresent(
                hoursLunch -> employeeDto.setLunchHours(Optional.of(Float.toString(hoursLunch))));
        employee.getWorkedHoursOpt().ifPresent(
                workedHours -> employeeDto.setWorkedHours(Optional.of(Float.toString(workedHours))));
        employee.getValueHourOpt()
                .ifPresent(valueHours -> employeeDto.setValueHour(Optional.of(valueHours.toString())));
        return employeeDto;
    }

    private void updatingEmployeeData(Employee employee, EmployeeDto employeeDto, BindingResult result) throws NoSuchAlgorithmException {
        employee.setName(employeeDto.getName());

        if(!employee.getEmail().equals(employeeDto.getEmail())) {
            this.employeeService.findByEmail(employeeDto.getEmail()).ifPresent(emp -> result.addError(new ObjectError("email", "This e-mail already exist")));
            employee.setEmail(employeeDto.getEmail());

            employee.setLunchHours(null);
            employeeDto.getLunchHours().ifPresent(hoursLunch -> employee.setLunchHours(Float.valueOf(hoursLunch)));

            employee.setWorkedHours(null);
            employeeDto.getWorkedHours().ifPresent(workedHours -> employee.setWorkedHours(Float.valueOf(workedHours)));

            if (employeeDto.getPassword().isPresent()) {
                employee.setPassword(PasswordUtils.generateCrypt(employeeDto.getPassword().get()));
            }
        }
    }
}
