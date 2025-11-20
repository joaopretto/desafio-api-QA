package br.desafio.utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.Arrays;
import java.util.List;

public class FilterLogging implements Filter {
    private final List<String> importantHeaders = Arrays.asList(
            "Content-Type", "Accept"
    );

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx){
        System.out.println("URL: " + requestSpec.getURI());
        System.out.println("Method: " + requestSpec.getMethod());

        if(requestSpec.getBody() != null){
            System.out.println("Body: " + requestSpec.getBody());
        }

        Response response = ctx.next(requestSpec, responseSpec);

        System.out.println("Status code: " + response.getStatusCode());

        importantHeaders.forEach(header -> {
            if(response.getHeaders().hasHeaderWithName(header)){
                System.out.println(header + ": " + response.getHeader(header));
            }
        });
        return response;
    }
}
