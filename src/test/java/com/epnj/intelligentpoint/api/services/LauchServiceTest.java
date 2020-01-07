package com.epnj.intelligentpoint.api.services;

import com.epnj.intelligentpoint.api.entities.Launch;
import com.epnj.intelligentpoint.api.repositories.LaunchRepository;
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
public class LauchServiceTest {

    @MockBean
    private LaunchRepository launchRepository;

    @Autowired
    private LaunchService launchService;

    @Before
    public void setUp() throws Exception {
        BDDMockito.given(this.launchRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Launch()));
        BDDMockito.given(this.launchRepository.save(Mockito.any(Launch.class))).willReturn(new Launch());
    }

    @Test
    public void testFindLaunchByEmployeeId() {
        Optional<Launch> launch = this.launchService.findById(1L);
        Assert.assertNotNull(launch);
    }

    @Test
    public void testFindLaunchById() {
        Optional<Launch> launch = this.launchService.findById(1L);
        Assert.assertTrue(launch.isPresent());
    }

    @Test
    public void testPersistLaunch() {
        Launch launch = this.launchService.persist(new Launch());
        Assert.assertNotNull(launch);
    }

}
