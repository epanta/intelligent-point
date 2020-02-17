package com.epnj.intelligentpoint.api.controllers;

import com.epnj.intelligentpoint.api.dtos.LaunchDto;
import com.epnj.intelligentpoint.api.entities.Employee;
import com.epnj.intelligentpoint.api.entities.Launch;
import com.epnj.intelligentpoint.api.enums.TypeEnum;
import com.epnj.intelligentpoint.api.response.Response;
import com.epnj.intelligentpoint.api.services.EmployeeService;
import com.epnj.intelligentpoint.api.services.LaunchService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("/api/launches")
@CrossOrigin(origins = "*")
public class LaunchController {

    private static final Logger log = LoggerFactory.getLogger(LaunchController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private LaunchService launchService;

    @Autowired
    private EmployeeService employeeService;

    @Value("${page.total_page}")
    private int totalPerPage;

    public LaunchController() {
    }

    @GetMapping(value = "/employee/{employeeId}")
    public ResponseEntity<Response<Page<LaunchDto>>> listByEmployeeId(
            @PathVariable("employeeId") Long employeeId,
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir) {
        log.info("Finding launches by employee Id: {}, page: {}", employeeId, pag);
        Response<Page<LaunchDto>> response = new Response<Page<LaunchDto>>();

        PageRequest pageRequest = PageRequest.of(pag, this.totalPerPage, Direction.valueOf(dir), ord);
        Page<Launch> launches = this.launchService.findByEmployeeId(employeeId, pageRequest);
        Page<LaunchDto> launcheDto = launches.map(launch -> this.convertLaunchDto(launch));

        response.setData(launcheDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<LaunchDto>> listById(@PathVariable("id") Long id) {
        log.info("Finding launch by id {}", id);
        Response<LaunchDto> response = new Response<LaunchDto>();
        Optional<Launch> launch = this.launchService.findById(id);

        if (!launch.isPresent()) {
            log.info("Launch not found to Id: {}", id);
            response.getErros().add("Launch not found to id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertLaunchDto(launch.get()));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<LaunchDto>> add(@Valid @RequestBody LaunchDto launchDto,
                                                   BindingResult result) throws ParseException {

        log.info("Adding launch: {}", launchDto.toString());
        Response<LaunchDto> response = new Response<LaunchDto>();
        validateEmployee(launchDto, result);
        Launch launch = this.convertDtoToLaunch(launchDto, result);

        if (result.hasErrors()) {
           log.error("Error validating launches {}", result.getAllErrors());
           result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
           return ResponseEntity.badRequest().body(response);
        }

        launch = this.launchService.persist(launch);
        response.setData(this.convertLaunchDto(launch));
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<LaunchDto>> update(@PathVariable("id") Long id,
                                                      @Valid @RequestBody LaunchDto launchDto, BindingResult result) throws ParseException {
        log.info("Updating launch: {}", launchDto.toString());
        Response<LaunchDto> response = new Response<LaunchDto>();
        validateEmployee(launchDto, result);
        launchDto.setId(Optional.of(id));

        Launch launch = this.convertDtoToLaunch(launchDto, result);

        if (result.hasErrors()) {
            log.error("Error validating launch: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        launch = this.launchService.persist(launch);
        response.setData(this.convertLaunchDto(launch));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
        log.info("Removing launch: {}", id);

        Response<String> response = new Response<String>();
        Optional<Launch> launch = this.launchService.findById(id);

        if (!launch.isPresent()) {
            log.info("Error removing launch id: {}", id);
            response.getErros().add("Error removing launch. Register not found to id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.launchService.remove(id);
        return  ResponseEntity.ok(new Response<String>());
    }

    private Launch convertDtoToLaunch(LaunchDto launchDto, BindingResult result) throws ParseException {
        Launch launch = new Launch();

        if (launchDto.getId().isPresent()) {
            Optional<Launch> launch1 = this.launchService.findById(launchDto.getId().get());
            if (launch1.isPresent()) {
                launch = launch1.get();
            } else {
              result.addError(new ObjectError("launch", "Launch not found."));
            }
        } else {
          launch.setEmployee(new Employee());
          launch.getEmployee().setId(launchDto.getEmployeeId());
        }

        launch.setDescription(launchDto.getDescription());
        launch.setPlace(launchDto.getPlace());
        launch.setLaunchDate(this.dateFormat.parse(launchDto.getLaunchDte()));

        if (EnumUtils.isValidEnum(TypeEnum.class, launchDto.getType())) {
            launch.setType(TypeEnum.valueOf(launchDto.getType()));
        } else {
            result.addError(new ObjectError("type", "Invalid type."));
        }
        return launch;
    }

    private void validateEmployee(LaunchDto launchDto, BindingResult result) {
        if (launchDto.getEmployeeId() == null) {
            result.addError(new ObjectError("employee", "Employee not informed."));
            return;
        }

        log.info("Validating employee id {} ", launchDto.getEmployeeId());
        Optional<Employee> employee = this.employeeService.findById(launchDto.getEmployeeId());
        if (!employee.isPresent()) {
            result.addError(new ObjectError("employee", "Employee not found. Id not exist."));
        }
    }

    private LaunchDto convertLaunchDto(Launch launch) {
        LaunchDto launchDto = new LaunchDto();
        launchDto.setId(Optional.of(launch.getId()));
        launchDto.setLaunchDte(this.dateFormat.format(launch.getLaunchDate()));
        launchDto.setType(launch.getType().toString());
        launchDto.setDescription(launch.getDescription());
        launchDto.setPlace(launch.getPlace());
        launchDto.setEmployeeId(launch.getEmployee().getId());
        return launchDto;
    }


}
