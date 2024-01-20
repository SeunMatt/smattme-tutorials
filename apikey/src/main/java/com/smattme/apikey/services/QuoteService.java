package com.smattme.apikey.services;

import com.github.javafaker.Faker;
import com.smattme.apikey.response.Response;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuoteService {

    public Response<Map<String, Object>> chuckNorrisFacts() {
        var quote = Faker.instance().chuckNorris().fact();
        return Response.successfulResponse("Operation Successful",
                Map.of("quote", quote));
    }

}
