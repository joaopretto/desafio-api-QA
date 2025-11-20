package br.desafio.config;

import br.desafio.utils.FilterLogging;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiConfig {
    public static RequestSpecification defaultSpec(){
        return given()
                .filter(new FilterLogging())
                .relaxedHTTPSValidation()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json");
    }
}
