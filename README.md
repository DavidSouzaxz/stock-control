# API de Controle de Estoque e Vendas

Um sistema de back-end robusto desenvolvido em **Java com Spring Boot**, projetado para gerenciar todas as operações de um pequeno a médio negócio. A aplicação foca na integridade dos dados, lógica de negócios transacional e na capacidade de gerar relatórios dinâmicos.

---

## ✨ Funcionalidades Principais

* **APIs RESTful:** Endpoints para o CRUD (Criar, Ler, Atualizar, Deletar) completo das seguintes entidades:
    * Produtos (com controle de quantidade)
    * Fornecedores
    * Clientes
    * Pedidos (Vendas)
* **Controle de Estoque Transacional:** Ao registrar um novo Pedido (venda), a lógica de negócios utiliza a anotação `@Transactional` para garantir que o estoque do produto seja abatido atomicamente. Se a atualização do estoque falhar, a venda não é registrada, mantendo a consistência do banco de dados.
* **Upload de Imagens:** Endpoint que aceita `multipart/form-data` para realizar o upload de fotos de produtos, salvando a referência ou o binário no banco de dados.
* **Relatórios e Agregações:** Endpoints de *analytics* que usam consultas JPA (JPQL ou Criteria API) para agregar dados, respondendo perguntas como:
    * Total faturado (R$) em um período.
    * Produtos mais vendidos (quantidade).
    * Clientes com mais pedidos.
* **Exportação de Dados (PDF):** Funcionalidade para gerar e exportar relatórios (ex: um Pedido específico ou um resumo de vendas) em formato PDF, utilizando a biblioteca **OpenPDF**.

---

## 🛠️ Tecnologias e Conceitos Aplicados

* **Back-end:** Java 17+ e Spring Boot 3+
* **Persistência de Dados:** Spring Data JPA (Hibernate)
* **Banco de Dados:** PostgreSQL
* **Geração de PDF:** OpenPDF (`com.github.librepdf:openpdf`)
* **Validação:** Spring Validation (Hibernate Validator)
* **Arquitetura:** Padrão MVC/Camadas (Controller, Service, Repository)
* **Conceitos:** Mapeamento objeto-relacional (ORM), DTOs (Data Transfer Objects) e Gerenciamento de Transações (`@Transactional`).
