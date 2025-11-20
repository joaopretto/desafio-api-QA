Feature: ProdutosTeste

  Scenario: Gostaria de cadastrar um produto, depois edita-lo, buscar na lista e então excluir o mesmo.
      Given Armazeno o email "fulano@qa.com" e a senha "teste" dentro de uma variavel
       When Faco uma requisicao do tipo POST no endpoint "/login" com usuario da variavel
       When Faco uma chamada na requisicao POST "/produtos" com o body
            | nome          | Mouse GPRO X |
            | preco         | 470          |
            | descricao     | Mouse        |
            | quantidade    | 381          |
       Then A resposta deve ser 201
        And A mensagem do campo "message" deve ser "Cadastro realizado com sucesso"
        And Armazeno o valor do campo "_id" do usuario cadastrado na variavel "idProduct"
       When Faco uma chamada na requisicao GET "/produtos/{idProduct}"
       Then A resposta deve ser 200
        And O valor do campo "nome" deve ser "Mouse GPRO X"
       When Faco uma chamada na requisicao PUT "/produtos/{idProduct}" com o body
            | nome          | Mouse GPRO X turbo |
            | preco         | 470                |
            | descricao     | Mouse              |
            | quantidade    | 381                |
       Then A resposta deve ser 200
        And A mensagem do campo "message" deve ser "Registro alterado com sucesso"
        And Faco uma chamada na requisicao GET "/produtos/{idProduct}"
        And A resposta deve ser 200
        And O valor do campo "nome" deve ser "Mouse GPRO X turbo"
       When Faco uma chamada na requisicao DELETE "/produtos/{idProduct}"
       Then A resposta deve ser 200
        And A mensagem do campo "message" deve ser "Registro excluído com sucesso"

  Scenario: Gostaria de cadastrar um usuario com email já cadastrado.
      Given Armazeno o email "fulano@qa.com" e a senha "teste" dentro de uma variavel
       When Faco uma requisicao do tipo POST no endpoint "/login" com usuario da variavel
        And Faco uma chamada na requisicao POST "/produtos" com o body
            | nome          | Logitech MX Vertical |
            | preco         | 470                  |
            | descricao     | Mouse                |
            | quantidade    | 381                  |
       Then A resposta deve ser 400
        And A mensagem do campo "message" deve ser "Já existe produto com esse nome"