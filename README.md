<h1>Projeto de Estudo em Spring com Thymeleaf</h1>


<h2>Tecnologias Usadas:</h2>
<h3>SpringBoot, SpringSecurity, Thymeleaf, PostgesSQL, Materialize</h3>

<h2>Sistema Web não responsivo</h2>

<h1>O Que Você Encontra Aqui?:</h1>
<h3>Role entre tabelas para definir quem tem acesso a quais partes do sistema;</h3><br>
<h3>Authenticacao direto do banco com criptografia na senha;</h3><br>

<h3>CRUD de pessoa com estas funcionalidades:</h3>
<h4>Inserir:</h4>
<h5>Arquivo(apenas para adicionar mesmo);</h5>
<h5>Data de nascimento que ate faz automanticamente o calculo de idade dado a data de nascimento(esta idade e validada no back-end com a regra que so pode ter entre 18 a 100 anos);</h5>
<h5>E-Mail(apenas com uma valicao bem simples pro gMail usando expressao regular);</h5>
<h5>Sexo(por selecionador que carrega direto do Front-End);</h5>
<h5>Nome e Sobrenome(com um tamanho maximo definido de caracteres);</h5>
<h5>Profissao(por selecionador que carrega do banco de dados em uma tabela relacionada);</h5>
<h5>Cargo(por selecionador que carrega por um enum);</h5>
<h5>Cep e Derivados, Usando aqui inclusive a WebService do cep que se adicionado um cep valido completa os outros campos automanticamente(tem im link no form pra caso queira gerar um cep valido);</h5>





<h1>as querys a seguir apos o primeiro start</h1>

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
