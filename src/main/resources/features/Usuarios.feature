Feature: UsuárioTeste

  Scenario: Gostaria de cadastrar um usuário, depois edita-lo, buscar na lista e então excluir o mesmo.
      Given Faco uma chamada na requisicao POST "/usuarios" com o body
            | nome          | Fulano da Silva    |
            | email         | beltrano@qa.com.br |
            | password      | teste              |
            | administrador | true               |
       Then A resposta deve ser 201
        And A mensagem do campo "message" deve ser "Cadastro realizado com sucesso"
        And Armazeno o valor do campo "_id" do usuario cadastrado na variavel "idUser"
       When Faco uma chamada na requisicao GET "/usuarios/{idUser}"
       Then A resposta deve ser 200
        And O valor do campo "nome" deve ser "Fulano da Silva"
       When Faco uma chamada na requisicao PUT "/usuarios/{idUser}" com o body
            | nome          | Fulano da Silva Teste |
            | email         | beltrano@qa.com.br    |
            | password      | teste                 |
            | administrador | true                  |
       Then A resposta deve ser 200
        And A mensagem do campo "message" deve ser "Registro alterado com sucesso"
        And Faco uma chamada na requisicao GET "/usuarios/{idUser}"
        And A resposta deve ser 200
        And O valor do campo "nome" deve ser "Fulano da Silva Teste"
       When Faco uma chamada na requisicao DELETE "/usuarios/{idUser}"
       Then A resposta deve ser 200
        And A mensagem do campo "message" deve ser "Registro excluído com sucesso"
    
    Scenario: Gostaria de cadastrar um usuario com email já cadastrado.
        Given Faco uma chamada na requisicao POST "/usuarios" com o body
              | nome          | Fulano da Silva    |
              | email         | fulano@qa.com      |
              | password      | teste              |
              | administrador | true               |
         Then A resposta deve ser 400
          And A mensagem do campo "message" deve ser "Este email já está sendo usado"