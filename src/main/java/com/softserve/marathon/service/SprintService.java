package com.softserve.marathon.service;

import com.softserve.marathon.entity.Marathon;
import com.softserve.marathon.entity.Sprint;

import java.util.List;

public interface SprintService {

    List<Sprint> getSprintsByMarathonId(Long idMarathon);

    boolean addSprintToMarathon(Sprint sprint, Marathon marathon);

    boolean updateSprint(Sprint sprint);

    Sprint getSprintById(Long idSprint);

    public Sprint create(Sprint sprint);

}
