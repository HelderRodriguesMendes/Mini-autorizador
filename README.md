## API - Mini Autorizador de Transações Financeiras

### Sobre o projeto
Este projeto foi desenvolvido com a finalidade de atender a um determinado teste prático, que possuía como objetivo realizar verificações dos dados de transações 
e com base nisso autorizar ou não a conclusão da mesma.

As verificações a serem feitas para a autorizar a conclusão de uma transação eram:
- O cartão existir
- A senha do cartão for a correta
- O cartão possuir saldo disponível

#### Detalhes técnicos do projeto
Todo o projeto foi desenvolvido em Java 8, juntamente com Spring Boot, JPA, Hibernate, Banco de dados MySQL, Docker, Postman, Swagger, MySQL Workbench e Intellij.
Sua estrutura é dívida em camadas para facilitar a compreensão do projeto como um todo, incluindo também a leitura dos códigos que foram desenvolvidos tomando certos 
cuidados para uma melhor leitura, incluindo a não utilização de if else em seu meio.

Outros detalhes técnicos do projeto são: 
- A utilização do Design Patterns Strategy, no qual foi um pattern que atendeu bastante as necessidades do projeto, em relação
a compreensão e manutenção do código, e também por ter favorecido em não utilizar if else no projeto.
- Testes unitários com Junit (Somente nas funcionalidades de cadastrar um cartão e consultar saldo).
- Utilização de tratamento de erros.
- Testes via Swagger

![image](https://user-images.githubusercontent.com/29339786/203471312-c6ed7c7e-82fa-4a03-9dd1-ffa8f57c9126.png)


#### Observações
As funcionalidades que ficaram sem implementar foram:
- Testes unitários para a funcionalidade de realizações de transações
- Garantir que 2 transações disparadas ao mesmo tempo não causem problemas relacionados à concorrência


