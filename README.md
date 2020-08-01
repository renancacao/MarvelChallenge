# Desafio Android com API Marvel.

## Resumo
Utilizar a API Marvel para criar um app que liste os personagens e seus dados, e que tenha a funcionalidade de salvar os personagens favoritos, seguindo as regras propostas.

## Regras do Desafio
[Descrição original](https://github.com/jjfernandes87/Challenge/blob/master/README_Android_Senior.md)

## Requisitos Essenciais
- [X] Usar Kotlin.
- [ ] App universal, desenvolva uma interface que se adapte a telas maiores.
- [ ] Tratamento para falha de conexão.
- [ ] Desenvolver o App em uma arquitetura robusta.
- [ ] O teste não pode apresenter crash.
- [ ] Testes unitários e interface.

## Outras libs usadas e bônus
- Kotlin Extensions Functions
- Detekt
- Retrofit2
- Hilt

## Como executar
Adicione suas chaves pública e privada da [API MARVEL](https://developer.marvel.com/docs) no arquivo gradle.properties:
public_api_key=<chave publica>
private_api_key=<chave privada>

Essas configurações podem ser adicionadas no arquivo <user>/.gradle/gradle.properties para que fiquem apartadas do projeto.