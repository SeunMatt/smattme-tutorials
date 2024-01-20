package com.smattme.apikey.repositories;

import com.smattme.apikey.entities.ClientCredential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientCredentialRepository extends CrudRepository<ClientCredential, Integer> {

    Optional<ClientCredential> findByApiKey(String apiKey);
}
