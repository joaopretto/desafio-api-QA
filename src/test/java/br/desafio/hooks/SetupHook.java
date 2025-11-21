package br.desafio.hooks;

import br.desafio.config.AuthClient;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

import java.io.InputStream;
import java.util.Properties;

public class SetupHook {
    public static AuthClient client;
    public static String baseUrl;
    public static Properties props = new Properties();

    @Before
    public static void setup() throws Exception{
        if(client != null) return;

        try(InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.properties")){

            props.load(is);
            baseUrl = props.getProperty("base.url");
        }

        RestAssured.baseURI = baseUrl;
        client = new AuthClient(baseUrl);

        System.out.println("--- Configuração Inicial: Base URI definida para " + baseUrl + " ---");
    }
}
