package com.unict.riganozito.videomanagementservice.entity;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByName(String name);

    public User findByEmail(String email);
}