package com.softserve.marathon.repository;

import com.softserve.marathon.entity.Marathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarathonRepository extends JpaRepository<Marathon, Long> {

}
