package com.binarfud.challenge7.Repository;

import com.binarfud.challenge7.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true,
            value = "select * from users order by user_id asc")
    List<Users> getAllUsersAvailable();
    Optional<Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmailAddress(String email);
}
