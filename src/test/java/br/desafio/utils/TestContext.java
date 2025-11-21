package br.desafio.utils;

import io.restassured.response.Response;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class TestContext {
    private Object payload;
    public Response response;

    public static Map<String, Object> variables = new HashMap<>();

    public static void set(String key, Object value){
        variables.put(key, value);
    }

    public static Object get(String key){
        return variables.get(key);
    }
}
