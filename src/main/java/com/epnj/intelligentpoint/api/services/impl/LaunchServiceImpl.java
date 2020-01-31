package com.epnj.intelligentpoint.api.services.impl;

import com.epnj.intelligentpoint.api.entities.Launch;
import com.epnj.intelligentpoint.api.repositories.LaunchRepository;
import com.epnj.intelligentpoint.api.services.LaunchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaunchServiceImpl implements LaunchService {

    private static final Logger log = LoggerFactory.getLogger(LaunchServiceImpl.class);

    @Autowired
    private LaunchRepository launchRepository;

    @Override
    public Optional<Launch> findById(Long id) {
        log.info("Find employee by id: {}", id);
        return this.launchRepository.findById(id);
    }

    @Override
    public Launch persist(Launch launch) {
        log.info("persist launch: {}", launch);
        return this.launchRepository.save(launch);
    }

    @Override
    public void remove(Long id) {
        log.info("Delete employee by id: {}", id);
        this.launchRepository.deleteById(id);
    }
}
