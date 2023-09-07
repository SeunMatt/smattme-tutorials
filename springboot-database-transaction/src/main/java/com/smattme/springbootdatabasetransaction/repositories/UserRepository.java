package com.smattme.springbootdatabasetransaction.repositories;

import com.smattme.springbootdatabasetransaction.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);
}
