Feature: CarrinhoTeste

  Scenario: Gostaria de cadastrar um produto, adicionar em um carrinho, buscar o carrinho efetuar compra e excluir carrinho.
      Given Armazeno o email "beltrano@qa25.com.br" e a senha "teste" dentro de uma variavel
       When Faco uma requisicao do tipo POST no endpoint "/login" com usuario da variavel
       When Faco uma chamada na requisicao POST "/produtos" com o body
            | nome          | Mouse GPRO X Carrinho |
            | preco         | 470                   |
            | descricao     | Mouse                 |
            | quantidade    | 381                   |
       Then A resposta deve ser 201
        And A mensagem do campo "message" deve ser "Cadastro realizado com sucesso"
        And Armazeno o valor do campo "_id" do usuario cadastrado na variavel "idProduct"
       When Faco uma chamada na requisicao POST "/carrinhos" com o body de listagem de produtos
            | idProduto | quantidade |
            | idProduct | 1          |
       Then A resposta deve ser 201
        And A mensagem do campo "message" deve ser "Cadastro realizado com sucesso"
        And Armazeno o valor do campo "_id" do usuario cadastrado na variavel "idCar"
       When Faco uma chamada na requisicao GET "/carrinhos/{idCar}"
       Then A resposta deve ser 200
        And O valor do campo "produtos[0].idProduto" deve ser "idProduct"
       When Faco uma chamada na requisicao DELETE "/carrinhos/concluir-compra"
       Then A resposta deve ser 200
        And A mensagem do campo "message" deve ser "Registro excluído com sucesso"
       When Faco uma chamada na requisicao DELETE "/produtos/{idProduct}"
       Then A resposta deve ser 200
        And A mensagem do campo "message" deve ser "Registro excluído com sucesso"

  Scenario: Gostaria de cadastrar um carrinho com um produto não existente.
      Given Armazeno o email "beltrano@qa25.com.br" e a senha "teste" dentro de uma variavel
       When Faco uma requisicao do tipo POST no endpoint "/login" com usuario da variavel
       When Faco uma chamada na requisicao POST "/carrinhos" com o body de listagem de produtos
            | idProduto  | quantidade |
            | 1234141243 | 1          |
       Then A resposta deve ser 400
        And A mensagem do campo "message" deve ser "Produto não encontrado"