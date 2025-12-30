# language: pt
Funcionalidade: Gerenciamento de Itens
  Como um usuário do sistema
  Eu quero gerenciar itens (ingredientes)
  Para compor produtos no catálogo

  Contexto:
    Dado que o sistema está disponível
    E o banco de dados está preparado para itens

  @smoke @item
  Cenário: Criar um novo item com sucesso
    Dado que existe uma categoria "Ingredientes" para o item
    Quando eu criar um item com os seguintes dados:
      | nome      | Açúcar         |
      | descricao | Açúcar refinado |
    Então o item deve ser criado com sucesso
    E o item deve ter o nome "Açúcar"
    E o status da resposta deve ser 200 [item]

  @item
  Cenário: Buscar item por ID existente
    Dado que existe uma categoria "Ingredientes" para o item
    E que existe um item cadastrado com nome "Água Mineral"
    Quando eu buscar o item pelo ID
    Então o item deve ser retornado com sucesso
    E o nome do item deve ser "Água Mineral"
    E o status da resposta deve ser 200 [item]

  @item
  Cenário: Buscar item por ID inexistente
    Dado que não existe um item com ID aleatório
    Quando eu buscar o item pelo ID inexistente
    Então o item não deve ser encontrado
    E o status da resposta deve ser 404 [item]

  @item
  Cenário: Listar todos os itens
    Dado que existe uma categoria "Ingredientes" para o item
    E que existem os seguintes itens cadastrados:
      | nome           |
      | Açúcar         |
      | Sal            |
      | Água           |
      | Gás Carbônico  |
    Quando eu listar todos os itens
    Então devo receber uma lista com pelo menos 4 itens
    E o status da resposta deve ser 200 [item]

  @item
  Cenário: Atualizar um item existente
    Dado que existe uma categoria "Ingredientes" para o item
    E que existe um item cadastrado com nome "Item Antigo"
    Quando eu atualizar o item com nome "Item Atualizado"
    Então o item deve ser atualizado com sucesso
    E o nome do item deve ser "Item Atualizado"
    E o status da resposta deve ser 200 [item]

  @item
  Cenário: Excluir um item existente
    Dado que existe uma categoria "Ingredientes" para o item
    E que existe um item cadastrado com nome "Item Temporário"
    Quando eu excluir o item
    Então o item deve ser excluído com sucesso
    E o status da resposta deve ser 204 [item]

  @item @pagination
  Cenário: Listar itens com paginação
    Dado que existe uma categoria "Ingredientes" para o item
    E que existem 20 itens cadastrados
    Quando eu listar os itens da página 0 com tamanho 8
    Então devo receber 8 itens
    E o total de itens deve ser pelo menos 20
    E o número da página de itens deve ser 0
    E o status da resposta deve ser 200 [item]

  @item @validation
  Cenário: Criar item sem categoria deve falhar
    Quando eu tentar criar um item sem categoria válida
    Então o item não deve ser criado
    E o status da resposta deve ser 400 ou 500 [item]

  @item @category-relationship
  Cenário: Item deve estar associado a uma categoria
    Dado que existe uma categoria "Bebidas" para o item
    E que existe um item cadastrado com nome "Xarope"
    Quando eu buscar o item pelo ID
    Então o item deve ter uma categoria associada
    E o nome da categoria do item deve ser "Bebidas"
    E o status da resposta deve ser 200 [item]

