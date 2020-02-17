package com.epnj.intelligentpoint.api.services.impl;

import com.epnj.intelligentpoint.api.entities.Launch;
import com.epnj.intelligentpoint.api.repositories.LaunchRepository;
import com.epnj.intelligentpoint.api.services.LaunchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaunchServiceImpl implements LaunchService {

    private static final Logger log = LoggerFactory.getLogger(LaunchServiceImpl.class);

    @Autowired
    private LaunchRepository launchRepository;

    public Page<Launch> findByEmployeeId(Long employeeId, PageRequest pageRequest) {
        log.info("Finding launches to employee Id {}", employeeId);
        return this.launchRepository.findByEmployeeId(employeeId, pageRequest);
    }

    @Override
    public Optional<Launch> findById(Long id) {
        log.info("Find a launch by id: {}", id);
        return this.launchRepository.findById(id);
    }

    @Override
    public Launch persist(Launch launch) {
        log.info("Persisting the launch: {}", launch);
        return this.launchRepository.save(launch);
    }

    @Override
    public void remove(Long id) {
        log.info("Deleting the launch by id: {}", id);
        this.launchRepository.deleteById(id);
    }
}
