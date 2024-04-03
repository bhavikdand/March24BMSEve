package com.scaler.bmsmarch24evebatch.repositories;

import com.scaler.bmsmarch24evebatch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserById(int id);
    // find -> select
    // user -> table user
    // by id -> where id = {id}
}
