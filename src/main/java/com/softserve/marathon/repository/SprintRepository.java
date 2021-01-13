package com.softserve.marathon.repository;


import com.softserve.marathon.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
    List<Sprint> findAllByMarathonId (Long marathonId);
}
