# Upskilling/Reskilling API – FIAP Global Solution 2025

API RESTful moderna (Java 21, Spring Boot 3.3.x) que apoia o **Futuro do Trabalho (2030+)** com trilhas de upskilling e reskilling, alinhada aos ODS 4, 8, 9 e 10.

## 🏗️ Tech Stack
- Java 21, Spring Boot 3.3.x
- Spring Web, Spring Data JPA, Bean Validation
- H2 (memória) – com **console em `/h2`**
- **OpenAPI/Swagger UI em `/docs`** (springdoc)
- Arquitetura em camadas **Controller → Service → Repository**
- Seeds com `data.sql`

## ▶️ Como executar localmente
> Pré-requisitos: Java 21 e Maven 3.9+

```bash
mvn spring-boot:run
# ou
./mvnw spring-boot:run
```

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/docs
- H2 Console: http://localhost:8080/h2 (JDBC: `jdbc:h2:mem:upskill`, user: `sa`, password vazio)

## 🗃️ Banco de Dados
Configuração em `src/main/resources/application.yml`:
- `ddl-auto: create` – tabelas geradas a partir das entidades
- Seeds automáticas via `data.sql` (usuários, trilhas, competências e matrículas)

## 📦 Recursos (CRUDs completos)
### Usuários `/api/usuarios`
- `GET /api/usuarios`
- `GET /api/usuarios/{id}`
- `POST /api/usuarios`
- `PUT /api/usuarios/{id}`
- `DELETE /api/usuarios/{id}`

**Exemplo de POST**
```json
{
  "nome": "Joana Prado",
  "email": "joana.prado@example.com",
  "areaAtuacao": "Dados",
  "nivelCarreira": "PLENO"
}
```

### Trilhas `/api/trilhas`
- `GET /api/trilhas`
- `GET /api/trilhas/{id}`
- `POST /api/trilhas`
- `PUT /api/trilhas/{id}`
- `DELETE /api/trilhas/{id}`

**Exemplo de POST**
```json
{
  "nome": "Fundamentos de IA",
  "descricao": "Introdução à IA e ML",
  "nivel": "INICIANTE",
  "cargaHoraria": 60,
  "focoPrincipal": "IA",
  "competenciasIds": [1,8,6]
}
```

## ➕ Extras (bônus)
### Matrículas `/api/matriculas`
- `POST /api/matriculas` – matricular um usuário em uma trilha
  - Body: `{ "usuarioId": 1, "trilhaId": 2 }`
- `GET /api/matriculas/usuario/{usuarioId}`
- `GET /api/matriculas/trilha/{trilhaId}`
- `POST /api/matriculas/{matriculaId}/cancelar`

## ✅ Validações e Erros
- Bean Validation com mensagens claras (400)
- Conflitos de integridade (422), ex.: e-mail duplicado
- Entidades não encontradas (404)
- Resposta de erro padronizada:
```json
{
  "timestamp": "2025-11-12T12:00:00Z",
  "status": 404,
  "error": "Não encontrado",
  "message": "Usuário não encontrado: id=99",
  "path": "uri=/api/usuarios/99"
}
```

## 🗂️ Organização do Código
```
src/main/java/br/com/fiap/upskill
├── UpskillApplication.java
├── config/OpenApiConfig.java
├── controller (REST Controllers)
├── domain
│   ├── entity (JPA Entities)
│   └── enums
├── dto (DTOs de entrada/saída)
├── exception (Exceções + Handler)
├── mapper (conversores Entity ↔ DTO)
└── repository / service
```

## 🌱 Seeds
- 12 competências, 5 trilhas, 6 usuários e 6 matrículas
- Vínculos N:N entre trilhas e competências (`trilha_competencia`)

## 🔒 Status Codes
- 200/201/204 para sucesso
- 400 para validações inválidas
- 404 quando não encontrado
- 422 para conflitos (e.g., e-mail único)
- 500 para erros inesperados

## 📝 Entrega
- Inclua este repositório no GitHub/GitLab e envie o link no Teams
- Na raiz, mantenha:
  - `README.md` (este arquivo)
  - Lista de integrantes e RMs
  - Código-fonte organizado

---

> Dica: Use o **Swagger UI** para testar rapidamente os endpoints (inclui `try-it-out`).

Feito com 💡 para a Global Solution 2025.


## 🎨 Front-end leve (SPA estática)
Página de demonstração em `/` para listar/crear usuários, ver trilhas e criar matrículas.