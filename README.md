# Desafio Android com API Marvel.

## Resumo
Utilizar a API Marvel para criar um app que liste os personagens e seus dados, e que tenha a funcionalidade de salvar os personagens favoritos, seguindo as regras propostas.

## Regras do Desafio
[Descrição original](https://github.com/jjfernandes87/Challenge/blob/master/README_Android_Senior.md)

## Requisitos Essenciais
- [X] Usar Kotlin.
- [X] App universal, desenvolva uma interface que se adapte a telas maiores.
- [X] Tratamento para falha de conexão.
- [X] Desenvolver o App em uma arquitetura robusta.
- [ ] O teste não pode apresenter crash.
- [ ] Testes unitários e interface.

## Requisitos Tela Personagens
- [X] Listagem dos personagens ordenados por nome.
- [X] Botão para favoritar nas células.
- [X] Barra de busca para filtrar lista por nome.
- [X] Pull-to-refresh para atualizar a lista.
- [X] Paginação na lista: Carregar 20 personagens por vez, baixando a próxima página ao chegar no fim da lista.
- [X] Interface de lista vazia.
- [X] Interface de erro.
- [X] Interface de sem internet.


## Requisitos Tela Detalhes
- [X] Botão de favorito.
- [X] Foto em tamanho maior
- [X] Nome do personagem na barra de navegação
- [X] Descrição (se houver).
- [X] Lista horizontal de Comics (se houver).
- [X] Lista horizontal de Series (se houver).

## Requisitos Tela Favoritos
- [X] Listagem dos personagens favoritados pelo usuário.
- [X] Favoritos devem ser persistidos para serem acessados offline.
- [X] Interface de lista vazia.
- [X] Interface de erro.

## Outras libs usadas e bônus

- Kotlin Extensions Functions
- Detekt
- Retrofit2
- Hilt
- Coroutines
- Flow
- Room
- Paging 3 Library
- ViewPager2 + TabLayout
- ViewModel KTX
- DataBinding
- BindingAdapter
- Glide
- TextInputLayout
- Swipe Refresh

## Como executar
Adicione suas chaves pública e privada da [API MARVEL](https://developer.marvel.com/docs) no arquivo gradle.properties:
public_api_key=<chave publica>
private_api_key=<chave privada>

Essas configurações podem ser adicionadas no arquivo <user>/.gradle/gradle.properties para que fiquem apartadas do projeto.