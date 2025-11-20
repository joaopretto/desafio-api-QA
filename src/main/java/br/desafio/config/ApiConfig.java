package br.desafio.config;

import br.desafio.utils.FilterLogging;
import br.desafio.utils.TokenManager;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiConfig {
    public static RequestSpecification loginSpec(){
        return given()
                .filter(new FilterLogging())
                .relaxedHTTPSValidation()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json");
    }

    public static RequestSpecification defaultSpec(){
        return loginSpec()
                .header("Authorization", TokenManager.getToken());
    }
}
