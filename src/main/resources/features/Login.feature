Feature: LoginTest

  Scenario: Gostaria de fazer um login com um usuário valido.
      Given Armazeno o email "fulano@qa.com" e a senha "teste" dentro de uma variavel
       When Faco uma requisicao do tipo POST no endpoint "/login"