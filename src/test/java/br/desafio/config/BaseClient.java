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

    public Response get(String path){
        return ApiConfig.defaultSpec()
                .when()
                .get(baseUrl + path);
    }

    public Response put(Object body, String path){
        return ApiConfig.defaultSpec()
                .body(body)
                .when()
                .put(baseUrl + path);
    }

    public Response delete(String path){
        return ApiConfig.defaultSpec()
                .when()
                .delete(baseUrl + path);
    }
}
