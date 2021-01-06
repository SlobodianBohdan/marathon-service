package com.softserve.sprint16.repository;

import com.softserve.sprint16.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    //JPQL
    @Query("select p from Progress p " +
            "join fetch p.task t " +
            "join fetch t.sprint s " +
            "where p.trainee.id = :userId " +
            "and s.marathon.id = :marathonId")

    //SQL
//    @Query(value =
//            "select p.* from progress as p\n" +
//                    "inner join task as t on p.trainee_id = :userId  and p.task_id = t.id\n" +
//                    "inner join sprint s on t.sprint_id = s.id and s.marathon_id = :marathonId",
//            nativeQuery = true)
    List<Progress> findProgressByTraineeIdAndMarathonId(Long userId, Long marathonId);

    @Query(value =
            "select p.* from progress as p " +
                    "inner join task as t " +
                    "on p.trainee_id = :userId " +
                    "and t.id = p.task_id " +
                    "and  t.sprint_id = :sprintId",
            nativeQuery = true)
    List<Progress> findProgressByUserIdAndSprintId(Long userId, Long sprintId);
}


