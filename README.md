#  FIAP · Global Solution 2025

API RESTful em **Java 21 / Spring Boot 3.3** para apoiar o **Futuro do Trabalho (2030+)** com trilhas de upskilling e reskilling. O domínio conecta-se aos ODS **4, 8, 9 e 10** (educação de qualidade, trabalho decente e crescimento, indústria/inovação, redução de desigualdades).

## 1) Stack e arquitetura

- **Java 21**, **Spring Boot 3.3.x**
- Spring Web · Spring Data JPA · Bean Validation
- **H2 (in-memory)** com console em `/h2`
- **OpenAPI/Swagger UI** em `/docs`
- Camadas: **Controller → Service → Repository**
- Seeds via `data.sql` (com ajuste de `IDENTITY` pós-seed)

Estrutura de pacotes (resumo):
```
src/main/java/br/com/fiap/upskill
├─ controller        # Endpoints REST
├─ service           # Regras de negócio
├─ repository        # Spring Data JPA
├─ domain
│  ├─ entity         # Entidades JPA
│  └─ enums          # Enumerações de domínio
├─ dto               # DTOs de entrada/saída
├─ mapper            # Conversões Entity ↔ DTO
├─ exception         # Exceções + Handler global
└─ config            # OpenAPI etc.
```

## 2) Como executar

Pré-requisitos: **Java 21** e **Maven 3.9+**

```bash
mvn spring-boot:run
# ou
./mvnw spring-boot:run
```

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/docs`
- H2 Console: `http://localhost:8080/h2`
  - JDBC: `jdbc:h2:mem:upskill`
  - user: `sa`
  - password: (vazio)

> Configuração principal em `src/main/resources/application.yml`. O JPA cria as tabelas (`ddl-auto: create`). As cargas iniciais são feitas pelo `data.sql`.

## 3) Modelo de dados (resumo)

Entidades principais:
- **usuarios** (pessoas na plataforma)
- **trilhas** (trilhas de aprendizagem)
- **competencias** (skills do futuro do trabalho)
- **trilha_competencia** (N:N trilha ↔ competência)
- **matriculas** (inscrições de usuários em trilhas)

### Competências seed (IDs de referência)
| ID | Competência               | Categoria        |
|---:|---------------------------|------------------|
|  1 | Inteligência Artificial   | Tecnologia       |
|  2 | Análise de Dados          | Tecnologia       |
|  3 | Segurança Cibernética     | Tecnologia       |
|  4 | Computação em Nuvem       | Tecnologia       |
|  5 | Automação e RPA           | Tecnologia       |
|  6 | Design de Experiência     | Humana           |
|  7 | Comunicação               | Humana           |
|  8 | Pensamento Crítico        | Humana           |
|  9 | Empatia                   | Humana           |
| 10 | Green Tech                | Sustentabilidade |
| 11 | Gestão de Projetos        | Gestão           |
| 12 | Liderança Colaborativa    | Gestão           |

> Nas páginas do site (SPA em `/`), as trilhas mostram **ID #** e as competências pelos **nomes**.

## 4) Endpoints (CRUDs obrigatórios)

### Usuários — `/api/usuarios`
- `GET /api/usuarios`
- `GET /api/usuarios/{id}`
- `POST /api/usuarios`
- `PUT /api/usuarios/{id}`
- `DELETE /api/usuarios/{id}`

Exemplo `POST`:
```json
{
  "nome": "Joana Prado",
  "email": "joana.prado@example.com",
  "areaAtuacao": "Dados",
  "nivelCarreira": "PLENO"
}
```

### Trilhas — `/api/trilhas`
- `GET /api/trilhas`
- `GET /api/trilhas/{id}`
- `POST /api/trilhas`
- `PUT /api/trilhas/{id}`
- `DELETE /api/trilhas/{id}`

Exemplo `POST`:
```json
{
  "nome": "Fundamentos de IA",
  "descricao": "Introdução à IA e ML",
  "nivel": "INICIANTE",
  "cargaHoraria": 60,
  "focoPrincipal": "IA",
  "competenciasIds": [1, 8, 6]
}
```

## 5) Extras 

### Matrículas — `/api/matriculas`
- `POST /api/matriculas` — body: `{"usuarioId": 1, "trilhaId": 2}`
- `GET /api/matriculas/usuario/{usuarioId}`
- `GET /api/matriculas/trilha/{trilhaId}`
- `POST /api/matriculas/{matriculaId}/cancelar`

### Recomendações — `/api/recomendacoes`
- `GET /api/recomendacoes/usuario/{usuarioId}`  
  Ordena trilhas por **score** considerando:
  - Nível do usuário × nível da trilha
  - Foco da trilha × área de atuação
  - Tendências (IA, Dados, Cloud, Segurança, Soft Skills, Green Tech)
  - Cenários de transição de carreira

## 6) Exemplos rápidos (cURL)

Criar usuário:
```bash
curl -X POST http://localhost:8080/api/usuarios   -H "Content-Type: application/json"   -d '{"nome":"Ana Silva","email":"ana.silva@example.com","areaAtuacao":"Dados","nivelCarreira":"PLENO"}'
```

Criar trilha:
```bash
curl -X POST http://localhost:8080/api/trilhas   -H "Content-Type: application/json"   -d '{"nome":"Introdução a Cloud","descricao":"Bases de computação em nuvem","nivel":"INICIANTE","cargaHoraria":40,"focoPrincipal":"Cloud","competenciasIds":[4,11]}'
```

Matricular:
```bash
curl -X POST http://localhost:8080/api/matriculas   -H "Content-Type: application/json"   -d '{"usuarioId":1,"trilhaId":2}'
```

## 7) Validações, erros e status codes

- **Bean Validation** em DTOs/entidades (ex.: `@NotBlank`, `@Email`, `@Min`, níveis válidos)
- **Handler global** com exceções de domínio (404/400/422)
- **Códigos usados**:
  - 200/201/204 — sucesso
  - 400 — erro de validação
  - 404 — não encontrado
  - 422 — violação de integridade (ex.: e-mail único)
  - 500 — erro inesperado

Formato de erro:
```json
{
  "timestamp": "2025-11-12T12:00:00Z",
  "status": 404,
  "error": "Não encontrado",
  "message": "Usuário não encontrado: id=99",
  "path": "uri=/api/usuarios/99"
}
```

## 8) SPA de apoio 

Uma página estática em `/` permite:
- listar/criar/editar/excluir **usuários**,
- criar **trilhas** (com IDs e competências visíveis),
- **matricular** usuários,
- ver **recomendações** por usuário.


## 9) Observações de projeto

- Seeds incluem usuários, trilhas, competências e matrículas.  
  Ao final do `data.sql`, o `IDENTITY` é ajustado para evitar colisões de ID em inserts subsequentes.
- As associações N:N (trilha ↔ competência) são expostas via lista de `competenciasIds` nos DTOs de trilha.
- Os endpoints REST retornam status/mensagens consistentes para facilitar testes via Swagger/Insomnia/cURL.



**Testes rápidos:** utilize o **Swagger UI** em `/docs` para acionar os endpoints com exemplos de payload.
