-- Competencias (skills)
INSERT INTO competencias (id, nome, categoria, descricao) VALUES
 (1,'Inteligência Artificial','Tecnologia','Fundamentos de IA, ML e ética'),
 (2,'Análise de Dados','Tecnologia','Exploração e modelagem de dados'),
 (3,'Segurança Cibernética','Tecnologia','Práticas de proteção e resposta a incidentes'),
 (4,'Computação em Nuvem','Tecnologia','Arquiteturas e serviços em nuvem'),
 (5,'Automação e RPA','Tecnologia','Automação de processos com bots'),
 (6,'Design de Experiência','Humana','UX/UI e design centrado no usuário'),
 (7,'Comunicação','Humana','Comunicação clara e assertiva'),
 (8,'Pensamento Crítico','Humana','Análise e tomada de decisão'),
 (9,'Empatia','Humana','Habilidades socioemocionais'),
 (10,'Green Tech','Sustentabilidade','Tecnologias limpas e ESG'),
 (11,'Gestão de Projetos','Gestão','Planejamento e execução ágil'),
 (12,'Liderança Colaborativa','Gestão','Liderança em times híbridos');

-- Trilhas
INSERT INTO trilhas (id, nome, descricao, nivel, carga_horaria, foco_principal) VALUES
 (1,'Trilha de IA Aplicada','Do zero à implantação de modelos','INICIANTE',60,'IA'),
 (2,'Dados para Decisões','Da análise exploratória ao dashboard','INTERMEDIARIO',80,'Dados'),
 (3,'Cibersegurança Essencial','Fundamentos de defesa e resposta','INICIANTE',40,'Segurança'),
 (4,'Arquitetura Cloud','Infraestrutura e serviços na nuvem','AVANCADO',100,'Cloud'),
 (5,'Soft Skills 2030','Comunicação, empatia e liderança','INTERMEDIARIO',50,'Soft Skills');

-- Associação trilha_competencia
INSERT INTO trilha_competencia (trilha_id, competencia_id) VALUES
 (1,1),(1,8),(1,6),
 (2,2),(2,11),(2,8),
 (3,3),(3,8),
 (4,4),(4,11),
 (5,6),(5,7),(5,9),(5,12);

-- Usuarios
INSERT INTO usuarios (id, nome, email, area_atuacao, nivel_carreira, data_cadastro) VALUES
 (1,'Ana Silva','ana.silva@example.com','Marketing','PLENO','2025-11-01'),
 (2,'Bruno Souza','bruno.souza@example.com','TI','JUNIOR','2025-10-15'),
 (3,'Carla Lima','carla.lima@example.com','Finanças','SENIOR','2025-09-10'),
 (4,'Diego Rocha','diego.rocha@example.com','RH','PLENO','2025-10-25'),
 (5,'Eva Santos','eva.santos@example.com','Operações','TRANSICAO','2025-11-05'),
 (6,'Felipe Araujo','felipe.araujo@example.com','Vendas','JUNIOR','2025-10-30');

-- Matriculas
INSERT INTO matriculas (id, usuario_id, trilha_id, data_inscricao, status) VALUES
 (1,1,2,'2025-11-02','ATIVA'),
 (2,2,1,'2025-11-01','ATIVA'),
 (3,3,4,'2025-10-15','CONCLUIDA'),
 (4,4,5,'2025-11-03','ATIVA'),
 (5,5,3,'2025-11-06','ATIVA'),
 (6,6,1,'2025-11-04','CANCELADA');

-- Ajuste dos IDENTITY para continuar após os seeds
ALTER TABLE usuarios ALTER COLUMN id RESTART WITH 7;
ALTER TABLE trilhas ALTER COLUMN id RESTART WITH 6;
ALTER TABLE competencias ALTER COLUMN id RESTART WITH 13;
ALTER TABLE matriculas ALTER COLUMN id RESTART WITH 7;
