# language: pt
Funcionalidade: Gerenciamento de Categorias
  Como um usuário do sistema
  Eu quero gerenciar categorias de produtos
  Para organizar meu catálogo de produtos

  Contexto:
    Dado que o sistema está disponível
    E o banco de dados está limpo

  @smoke @category
  Cenário: Criar uma nova categoria com sucesso
    Quando eu criar uma categoria com nome "Bebidas"
    Então a categoria deve ser criada com sucesso
    E a categoria deve ter o nome "Bebidas"
    E o status da resposta deve ser 200

  @category
  Cenário: Buscar categoria por ID existente
    Dado que existe uma categoria com nome "Alimentos"
    Quando eu buscar a categoria pelo ID
    Então a categoria deve ser retornada com sucesso
    E o nome da categoria deve ser "Alimentos"
    E o status da resposta deve ser 200

  @category
  Cenário: Buscar categoria por ID inexistente
    Dado que não existe uma categoria com ID aleatório
    Quando eu buscar a categoria pelo ID inexistente
    Então a categoria não deve ser encontrada
    E o status da resposta deve ser 404

  @category
  Cenário: Listar todas as categorias
    Dado que existem as seguintes categorias:
      | nome                                          |
      | Bebidas (Água, Sucos e Refri)                 |
      | Alimentos (Lanches, Acompanhamentos e Molhos) |
      | Sobremesas                                    |
    Quando eu listar todas as categorias
    Então devo receber uma lista com 3 categorias
    E o status da resposta deve ser 200

  @category
  Cenário: Atualizar uma categoria existente
    Dado que existe uma categoria com nome "Bebidas Antigas"
    Quando eu atualizar o nome da categoria para "Bebidas Novas"
    Então a categoria deve ser atualizada com sucesso
    E o nome da categoria deve ser "Bebidas Novas"
    E o status da resposta deve ser 200

  @category
  Cenário: Excluir uma categoria existente
    Dado que existe uma categoria com nome "Categoria Temporária"
    Quando eu excluir a categoria
    Então a categoria deve ser excluída com sucesso
    E o status da resposta deve ser 204

  @category @pagination
  Cenário: Listar categorias com paginação
    Dado que existem 15 categorias cadastradas
    Quando eu listar as categorias da página 0 com tamanho 10
    Então devo receber 10 categorias
    E o total de elementos deve ser 15
    E o número da página deve ser 0
    E o status da resposta deve ser 200

