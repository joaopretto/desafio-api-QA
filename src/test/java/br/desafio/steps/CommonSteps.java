package br.desafio.steps;

import br.desafio.config.BaseClient;
import br.desafio.hooks.SetupHook;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import br.desafio.utils.TestContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CommonSteps {
    public TestContext context;
    public BaseClient baseClient;
    private Response response;

    public CommonSteps(TestContext context){
        this.baseClient = new BaseClient(SetupHook.baseUrl);
        this.context = context;
    }

    //Requisições

    @Dado("Faco uma chamada na requisicao POST {string} com o body")
    public void facoUmaChamadaNaRequisicaoPOSTComOBody(String endpoint, DataTable dataTable) {
        Map<String, String> bodyMap = dataTable.asMap(String.class, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonBody = mapper.valueToTree(bodyMap);

        System.out.println("Body enviado:");
        System.out.println(jsonBody);

        response = baseClient.post(jsonBody, endpoint);
        context.setResponse(response);

        System.out.println(response.getBody().asPrettyString());
    }

    @Quando("Faco uma requisicao do tipo POST no endpoint {string} com usuario da variavel")
    public void facoUmaRequisicaoDoTipoPOSTNoEndpointCoUsuarioDaVariavel(String endpoint) {
        Object payload = context.getPayload();

        response = SetupHook.client.login(payload, endpoint);

        context.setResponse(response);

        System.out.println(response.getBody().asPrettyString());
    }

    @Quando("Faco uma chamada na requisicao PUT {string} com o body")
    public void facoUmaChamadaNaRequisicaoPUTComOBody(String endpoint, DataTable dataTable) {
        for(Map.Entry<String, Object> entry : TestContext.variables.entrySet()){
            String placeholder = "{" + entry.getKey() + "}";
            endpoint = endpoint.replace(placeholder, entry.getValue().toString());
        }

        Map<String, String> bodyMap = dataTable.asMap(String.class, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonBody = mapper.valueToTree(bodyMap);

        System.out.println("Body enviado:");
        System.out.println(jsonBody);

        response = baseClient.put(jsonBody, endpoint);

        context.setResponse(response);

        System.out.println(response.getBody().asPrettyString());
    }

    @Quando("Faco uma chamada na requisicao DELETE {string}")
    public void facoUmaChamadaNaRequisicaoDELETE(String endpoint) {
        for(Map.Entry<String, Object> entry : TestContext.variables.entrySet()){
            String placeholder = "{" + entry.getKey() + "}";
            endpoint = endpoint.replace(placeholder, entry.getValue().toString());
        }

        response = baseClient.delete(endpoint);

        context.setResponse(response);

        System.out.println(response.getBody().asPrettyString());
    }

    @Quando("Faco uma chamada na requisicao GET {string}")
    public void facoUmaChamadaNaRequisicaoGET(String endpoint) {
        for(Map.Entry<String, Object> entry : TestContext.variables.entrySet()){
            String placeholder = "{" + entry.getKey() + "}";
            endpoint = endpoint.replace(placeholder, entry.getValue().toString());
        }

        response = baseClient.get(endpoint);

        context.setResponse(response);

        System.out.println(response.getBody().asPrettyString());
    }

    @Quando("Faco uma chamada na requisicao POST {string} com o body de listagem de produtos")
    public void facoUmaChamadaNaRequisicaoPOSTComOBodyDeListagemDeProdutos(String endpoint, DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        List<Map<String, Object>> listaDeProdutos = new ArrayList<>();

        for(Map<String, String> row : rows){
            Map<String, Object> produtoProcessado = new HashMap<>();

            String idInput = row.get("idProduto");
            Object idReal = TestContext.get(idInput);

            if(idReal == null){
                System.out.println("Usando ID literal: " + idInput);
                idReal = idInput;
            } else {
                System.out.println("Usando ID do Contexto (" + idInput + "): " + idReal);
            }

            produtoProcessado.put("idProduto", idReal);
            produtoProcessado.put("quantidade", Integer.parseInt(row.get("quantidade")));

            listaDeProdutos.add(produtoProcessado);
        }

        Map<String, Object> payloadFinal = new HashMap<>();
        payloadFinal.put("produtos", listaDeProdutos);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonBody = mapper.valueToTree(payloadFinal);

        System.out.println("Body Carrinho: " + jsonBody);

        response = baseClient.post(jsonBody, endpoint);
        context.setResponse(response);

        System.out.println(response.getBody().asPrettyString());
    }

    //Validações

    @Então("A resposta deve ser {int}")
    public void aRespostaDeveSer(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @E("A mensagem do campo {string} deve ser {string}")
    public void aMensagemDoCampoDeveSer(String campo, String message) {
        String campoEsperado = response.jsonPath().getString(campo);
        assertEquals(message, campoEsperado);
    }

    @E("Armazeno o valor do campo {string} do usuario cadastrado na variavel {string}")
    public void armazenoOValorDoCampoDoUsuarioCadastradoNaVariavel(String campo, String variavel) {
        Object valor = context.getResponse().jsonPath().get(campo);
        TestContext.set(variavel, valor);

        System.out.println("Variável Armazenada: " + variavel + " = " + valor);
    }

    @E("O valor do campo {string} deve ser {string}")
    public void oValorDoCampoDeveSer(String campo, String valor) {
        String valorRetornadoApi = context.getResponse().jsonPath().getString(campo);
        Object valorNoContexto = TestContext.get(valor);
        String valorParaComparar;

        if(valorNoContexto != null){
            valorParaComparar = valorNoContexto.toString();
        }else{
            valorParaComparar = valor;
        }

        assertEquals(valorParaComparar, valorRetornadoApi, "valores não são iguais!");
    }
}
