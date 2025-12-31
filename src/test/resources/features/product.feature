# language: pt
Funcionalidade: Gerenciamento de Produtos
  Como um usuário do sistema
  Eu quero gerenciar produtos
  Para construir um catálogo completo

  Contexto:
    Dado que o sistema está disponível
    E o banco de dados está preparado para produtos

  @smoke @product
  Cenário: Criar um novo produto com sucesso
    Dado que existe uma categoria "Bebidas" para o produto
    E que existem itens disponíveis para o produto
    Quando eu criar um produto com os seguintes dados:
      | nome        | Coca-Cola 2L        |
      | descricao   | Refrigerante 2 litros |
      | preco       | 8.50                |
    Então o produto deve ser criado com sucesso
    E o produto deve ter o nome "Coca-Cola 2L"
    E o preço do produto deve ser 8,50
    E o status da resposta deve ser 200 [product]

  @product
  Cenário: Buscar produto por ID existente
    Dado que existe um produto cadastrado com nome "Guaraná Antarctica"
    Quando eu buscar o produto pelo ID
    Então o produto deve ser retornado com sucesso
    E o nome do produto deve ser "Guaraná Antarctica"
    E o status da resposta deve ser 200 [product]

  @product
  Cenário: Buscar produto por ID inexistente
    Dado que não existe um produto com ID aleatório
    Quando eu buscar o produto pelo ID inexistente
    Então o produto não deve ser encontrado
    E o status da resposta deve ser 404 [product]

  @product
  Cenário: Atualizar um produto existente
    Dado que existe um produto cadastrado com nome "Produto Antigo"
    Quando eu atualizar o produto com nome "Produto Atualizado" e preço 15,00
    Então o produto deve ser atualizado com sucesso
    E o nome do produto deve ser "Produto Atualizado"
    E o preço do produto deve ser 15,00
    E o status da resposta deve ser 200 [product]

  @product
  Cenário: Excluir um produto existente
    Dado que existe um produto cadastrado com nome "Produto Temporário"
    Quando eu excluir o produto
    Então o produto deve ser excluído com sucesso
    E o status da resposta deve ser 204 [product]

  @product @pagination
  Cenário: Listar produtos com paginação
    Dado que existem 12 produtos cadastrados
    Quando eu listar os produtos da página 0 com tamanho 5
    Então devo receber 5 produtos
    E o total de produtos deve ser pelo menos 12
    E o número da página de produtos deve ser 0
    E o status da resposta deve ser 200 [product]

  @product @validation
  Cenário: Criar produto sem categoria deve falhar
    Quando eu tentar criar um produto sem categoria
    Então o produto não deve ser criado
    E o status da resposta deve ser 400 ou 500 [product]
