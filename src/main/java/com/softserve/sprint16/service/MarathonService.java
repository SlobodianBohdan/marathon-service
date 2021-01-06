package com.softserve.sprint16.service;

import com.softserve.sprint16.entity.Marathon;

import java.util.List;

public interface MarathonService {

    List<Marathon> getAll();

    Marathon getMarathonById(Long id);

    Marathon createOrUpdate(Marathon marathon);

    void deleteMarathonById(Long id);
}
