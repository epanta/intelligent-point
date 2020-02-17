package com.epnj.intelligentpoint.api.services;

import com.epnj.intelligentpoint.api.entities.Launch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LaunchService {

    Page<Launch> findByEmployeeId(Long employeeId, PageRequest pageRequest);

    /**
     * return a launch by id
     *
     * @param id
     * @return Optional<Launch>
     */
    Optional<Launch> findById(Long id);

    /**
     * Persis a launch in database
     *
     * @param launch
     * @return Launch
     */
    Launch persist(Launch launch);

    /**
     * Remove launch by id
     *
     * @param id
     */
    void remove(Long id);
}
