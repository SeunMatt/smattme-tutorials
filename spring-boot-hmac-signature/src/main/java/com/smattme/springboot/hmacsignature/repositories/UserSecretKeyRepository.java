package com.smattme.springboot.hmacsignature.repositories;

import com.smattme.springboot.hmacsignature.entities.UserSecretKey;
import org.springframework.data.repository.CrudRepository;

public interface UserSecretKeyRepository extends CrudRepository<UserSecretKey, Long> {
    UserSecretKey findFirstByClientId(String clientId);
}
