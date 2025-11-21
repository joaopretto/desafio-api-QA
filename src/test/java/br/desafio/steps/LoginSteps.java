package br.desafio.steps;

import io.cucumber.java.pt.Dado;
import br.desafio.model.LoginPayload;
import br.desafio.utils.TestContext;

public class LoginSteps {
    private TestContext context;

    public LoginSteps(TestContext context){
        this.context = context;
    }

    @Dado("Armazeno o email {string} e a senha {string} dentro de uma variavel")
    public void armazeno_o_email_e_a_senha_dentro_de_uma_variavel(String userEmail, String userPassword) {
        LoginPayload loginPayload = new LoginPayload(userEmail, userPassword);

        context.setPayload(loginPayload);

        TestContext.set("loginPayload", loginPayload);
    }
}
