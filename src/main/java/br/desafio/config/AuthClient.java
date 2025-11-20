package br.desafio.config;

import io.restassured.response.Response;

public class AuthClient extends BaseClient{
    public AuthClient(String baseUrl){
        super(baseUrl);
    }

    public Response login(Object payload, String endpoint){
        return post(payload, endpoint);
    }
}
