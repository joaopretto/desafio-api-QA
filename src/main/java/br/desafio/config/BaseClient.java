package br.desafio.config;

import io.restassured.response.Response;

public class BaseClient {
    protected final String baseUrl;

    public BaseClient(String baseUrl){
        this.baseUrl = baseUrl;
    }

    public Response post(Object body, String path){
        return ApiConfig.defaultSpec()
                .body(body)
                .when()
                .post(baseUrl + path);
    }
}
