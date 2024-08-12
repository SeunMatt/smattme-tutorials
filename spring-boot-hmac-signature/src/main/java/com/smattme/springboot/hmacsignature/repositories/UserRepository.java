package com.smattme.springboot.hmacsignature.repositories;

import com.smattme.springboot.hmacsignature.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
