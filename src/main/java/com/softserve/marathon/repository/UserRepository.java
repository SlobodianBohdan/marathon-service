package com.softserve.marathon.repository;

import com.softserve.marathon.entity.Role;
import com.softserve.marathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getAllByRole(Role role);

    Optional<User> getByEmail(String email);

    @Query(value =
            "select u.* from users as u\n" +
                    "                    inner join marathon_user as mu\n" +
                    "                    on mu.marathon_id = :marathonId\n" +
                    "                    and u.id = mu.user_id ",
            nativeQuery = true)
    List<User> getAllByMarathonId(Long marathonId);
}
