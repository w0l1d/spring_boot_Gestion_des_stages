package com.storactive.stg.repository;

import com.storactive.stg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameIgnoreCase(String username);
    Optional<User> findByCin(String cin);

    Optional<User> findByUsernameIgnoreCaseOrCinIgnoreCase(String username, String cin);

}
