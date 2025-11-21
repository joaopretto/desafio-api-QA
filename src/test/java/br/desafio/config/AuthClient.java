package br.desafio.config;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient extends BaseClient{
    public AuthClient(String baseUrl){
        super(baseUrl);
    }

    public Response login(Object payload, String endpoint){
        return given()
                .spec(ApiConfig.loginSpec())
                .body(payload)
                .when()
                .post(baseUrl + endpoint);
    }
}
