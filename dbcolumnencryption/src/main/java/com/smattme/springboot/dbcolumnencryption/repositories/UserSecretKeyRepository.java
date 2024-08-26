package com.smattme.springboot.dbcolumnencryption.repositories;

import com.smattme.springboot.dbcolumnencryption.entities.UserSecretKey;
import org.springframework.data.repository.CrudRepository;

public interface UserSecretKeyRepository extends CrudRepository<UserSecretKey, Long> {
    UserSecretKey findFirstByClientId(String clientId);
}
