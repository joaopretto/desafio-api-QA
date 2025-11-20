package br.desafio.steps;

import br.desafio.hooks.SetupHook;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import br.desafio.utils.TestContext;

public class CommonSteps {
    public TestContext context;

    public CommonSteps(TestContext context){
        this.context = context;
    }

    @Quando("Faco uma requisicao do tipo POST no endpoint {string}")
    public void facoUmaRequisicaoDoTipoPOSTNoEndpoint(String endpoint) {
        Object payload = context.getPayload();

        Response response = SetupHook.client.login(payload, endpoint);

        context.setResponse(response);
    }
}
