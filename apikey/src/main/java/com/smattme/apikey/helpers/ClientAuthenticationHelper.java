package com.smattme.apikey.helpers;

import com.smattme.apikey.repositories.ClientCredentialRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientAuthenticationHelper {

    private final ClientCredentialRepository clientCredentialRepository;

    public ClientAuthenticationHelper(ClientCredentialRepository clientCredentialRepository) {
        this.clientCredentialRepository = clientCredentialRepository;
    }

    public boolean validateApiKey(String requestApiKey) {
        //this is a simplistic implementation. Prod
        //implementation will check for expired key and other business logic
        var optionalClientCred = clientCredentialRepository.findByApiKey(requestApiKey);
        return optionalClientCred.isPresent();
    }

}
