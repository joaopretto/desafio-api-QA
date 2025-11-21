Feature: LoginTeste

  Scenario: Gostaria de fazer um login com um usuário valido.
      Given Armazeno o email "fulano@qa.com" e a senha "teste" dentro de uma variavel
       When Faco uma requisicao do tipo POST no endpoint "/login" com usuario da variavel
       Then A resposta deve ser 200
        And A mensagem do campo "message" deve ser "Login realizado com sucesso"

  Scenario: Gostaria de fazer um login com usuário inválido.
      Given Armazeno o email "fulano" e a senha "teste" dentro de uma variavel
       When Faco uma requisicao do tipo POST no endpoint "/login" com usuario da variavel
       Then A resposta deve ser 400
        And A mensagem do campo "email" deve ser "email deve ser um email válido"