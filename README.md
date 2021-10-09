!!!!!!!!!!!!!projeto ainda nao completo, em breve!!!!!!!!!!!!!!!!!

No momento:
projeto com authenticacao, controle de acesso entre determinados usuarios, CRUD de pessoa com um relacionamento com outra tabela para cadastras varios telefones a esta pessoa

Projeto usa springboot, springsecurity, thymeleaf, PostgreSQL, materialize no ausilio do CSS


-- criacao do banco de dados <br>
CREATE DATABASE "spring-boot-thymeleaf"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Portuguese_Brazil.1252'
       LC_CTYPE = 'Portuguese_Brazil.1252'
       CONNECTION LIMIT = -1;

-- as querys a seguir sao apenas apos o start do sistema, execute uma a uma apos os comentarios

-- deleta o unique do usuariorole para poder depois cria-lo novamente e assim poder ter varios usuarios cadastrados em um role <br>
ALTER TABLE usuarios_role DROP
  CONSTRAINT uk_krvk2qx218dxa3ogdyplk0wxw;

-- para poder cadastrar varios users em um role <br>
ALTER TABLE usuarios_role
  ADD CONSTRAINT uk_krvk2qx218dxa3ogdyplk0wxw UNIQUE(role_id, usuario_id);

-- criando o role para acessos entre admin e user <br>
INSERT INTO role(id, nome_role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role(id, nome_role) VALUES (2, 'ROLE_USER');

-- inserindo dois usuarios bases para poder acessar o sistema <br>
INSERT INTO usuario(id, login, senha) VALUES (1, 'admin', '$2a$10$XQ44XYOBateY8V.sHCgmhOqh8u6PxaUjKgRfIj2Xu1x9.yqHV9cty');
INSERT INTO usuario(id, login, senha) VALUES (2, 'user', '$2a$10$7/RXuG4fbbW1wffaYPnHLeKVtlTrY1gDUlhw2T8AMMyw5Ru4aT2GK');

--inserindo algumas profissoes para serem selcionadas <br>
INSERT INTO profissao(id, nome) VALUES (1, 'Programador Java');
INSERT INTO profissao(id, nome) VALUES (2, 'Programador PHP');
INSERT INTO profissao(id, nome) VALUES (3, 'Programador JavaScript');
INSERT INTO profissao(id, nome) VALUES (4, 'Programador Front-End');
INSERT INTO profissao(id, nome) VALUES (5, 'Programador C / C++');

-- inserindo um usuario criado com admin e outro como user basico <br>
INSERT INTO usuarios_role(usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_role(usuario_id, role_id) VALUES (2, 2);

-- no application pode-se encontrar um main metodo que se executa te retorna-ra varias querys de pessoa para dar insert de uma vez, e uma query para delete destas querys tambem
