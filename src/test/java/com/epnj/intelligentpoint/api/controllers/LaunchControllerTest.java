package com.epnj.intelligentpoint.api.controllers;

import com.epnj.intelligentpoint.api.dtos.LaunchDto;
import com.epnj.intelligentpoint.api.entities.Employee;
import com.epnj.intelligentpoint.api.entities.Launch;
import com.epnj.intelligentpoint.api.enums.TypeEnum;
import com.epnj.intelligentpoint.api.services.EmployeeService;
import com.epnj.intelligentpoint.api.services.LaunchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LaunchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LaunchService launchService;

    @MockBean
    private EmployeeService employeeService;

    private static final String URL_BASE = "/api/launches";
    private static final Long ID_EMPLOYEE = 1L;
    private static final Long ID_LAUNCH = 1L;
    private static final String TYPE = TypeEnum.INIT_WORK.name();
    private static final Date DATE = new Date();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testRegisterLaunch() throws Exception {
        Launch launch = getLaunchData();
        BDDMockito.given(this.employeeService.findById(Mockito.anyLong())).willReturn(Optional.of(new Employee()));
        BDDMockito.given(this.launchService.persist(Mockito.any(Launch.class))).willReturn(launch);

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.getJsonRequestPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID_LAUNCH))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.type").value(TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.date").value(this.dateFormat.format(DATE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.employeeId").value(ID_EMPLOYEE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty());
    }

    @Test
    public void testRegisterLaunchInvalidEmployeeId() throws Exception {
        BDDMockito.given(this.employeeService.findById(Mockito.anyLong())).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.getJsonRequestPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Employee not found. Id does not exist."))
                .andExpect(MockMvcResultMatchers.jsonPath("$data").isEmpty());
    }

    @Test
    public void testRemoveLaunch() throws Exception {
        BDDMockito.given(this.launchService.findById(Mockito.anyLong())).willReturn(Optional.of(new Launch()));

        mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LAUNCH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String getJsonRequestPost() throws JsonProcessingException {
        LaunchDto launchDto = new LaunchDto();
        launchDto.setId(null);
        launchDto.setLaunchDte(this.dateFormat.format(DATE));
        launchDto.setType(TYPE);
        launchDto.setEmployeeId(ID_EMPLOYEE);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(launchDto);

    }

    private Launch getLaunchData() {
        Launch launch = new Launch();
        launch.setId(ID_LAUNCH);
        launch.setLaunchDate(DATE);
        launch.setType(TypeEnum.valueOf(TYPE));
        launch.setEmployee(new Employee());
        launch.getEmployee().setId(ID_EMPLOYEE);
        return launch;
    }
}
