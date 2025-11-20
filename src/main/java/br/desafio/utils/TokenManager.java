package br.desafio.utils;

import br.desafio.config.ApiConfig;
import br.desafio.hooks.SetupHook;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TokenManager {
    private static String token;

    public static String getToken(){
        if(token == null){
            generateToken();
        }
        return token;
    }

    private static void generateToken(){
        Object loginPayload = TestContext.get("loginPayload");

        if(loginPayload == null){
            throw new RuntimeException(
                    "Nenhum payload encontrado no contexto atual"
            );
        }

        Response response = given()
                .spec(ApiConfig.loginSpec())
                .body(loginPayload)
                .when()
                .post("/login");

        if(response.statusCode() != 200){
            throw new RuntimeException("Falha ao fazer login: " + response.asString());
        }

        token = response.jsonPath().getString("authorization");

        if(token == null){
            throw new RuntimeException("Campo 'authorization' não encontrado no Json.");
        }

        TestContext.set("authToken", token);
        System.out.println("Token armazenado!");
    }
}
