package br.desafio.utils;

import io.restassured.response.Response;
import lombok.Data;

@Data
public class TestContext {
    private Object payload;
    private Response response;
}
