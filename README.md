<h1>Projeto de Estudo em Spring com Thymeleaf</h1>


<h2>Tecnologias Usadas:</h2>
<h3>SpringBoot, SpringSecurity, Thymeleaf, PostgesSQL, Materialize e JPA com Hibernate</h3>

<h2>Sistema Web não responsivo</h2>

<h1>O Que Você Encontra Aqui?:</h1>
<h2>Role entre tabelas para definir quem tem acesso a quais partes do sistema;</h2>
<h2>Authenticacao direto do banco com criptografia na senha;</h2>
<h2>CRUD de pessoa com estas funcionalidades:</h2>
<h3>INSERIR:</h3>
<h4>Arquivo(qualquer tipo, salvo no banco de dados em oid, podendo depois baixar ele novamente);</h4>
<h4>Data de nascimento que ate faz automanticamente o calculo de idade dado a data de nascimento(esta idade e validada no back-end com a regra que so pode ter entre 18 a 100 anos);</h4>
<h4>E-Mail apenas com uma valicao bem simples pro gMail (e uma validacao de unique);</h4>
<h4>Sexo(por selecionador que carrega direto do Front-End);</h4>
<h4>Nome e Sobrenome(com um tamanho maximo definido de caracteres);</h4>
<h4>Profissao(por selecionador que carrega do banco de dados em uma tabela relacionada);</h5>
<h4>Cargo(por selecionador que carrega por um enum);</h4>
<h4>Cep e Derivados, Usando aqui inclusive a WebService do cep que se adicionado um cep valido completa os outros campos automanticamente(tem um link no form pra caso queira gerar um cep valido);</h4>
<h4>Telefone com um relacionamento com outra tabela podendo varios telefones pra uma pessoa;</h4>
<h4>Imagem de usuario(em base64 no usuario);</h4>
<h3>ATUALIZAR:</h3>
<h4>todos e qualquer os dado(s);</h4>
<h3>CONSULTA:</h3>
<h4>Todos os dados do pessoa;</h4>
<h4>Todos os telefones de uma pessoa;</h4>
<h4>Consulta de pessoas pelo seu nome, sempre estando ordenado por id(usando trim e upperCase para melhor busca);</h4>
<h3>DELETE:</h3>
<h4>todos e qualquer os dado(s);</h3>
<h2>Paginacao em lista das pessoas;</h2>
<h2>Download de alguns dados de todas as pessoas em PDF por relatorio com jasper;</h2>

<h1>DataBase:</h1>
<h2>crie o banco com o nome spring-boot-thymeleaf e o usuario postgres</h2>
<h2>DICA: não esqueca de no pom.xml colocar o driver de acordo com a versao do teu banco</h2>
<h2>querys a seguir apos o primeiro start do sistema</h2>
-- deleta o unique do usuario role para poder depois cria-lo novamente e assim poder ter varios usuarios cadastrados em um role <br>
ALTER TABLE usuarios_role DROP<br>
  CONSTRAINT uk_krvk2qx218dxa3ogdyplk0wxw;<br><br>

-- para poder cadastrar varios users em um role <br>
ALTER TABLE usuarios_role<br>
  ADD CONSTRAINT uk_krvk2qx218dxa3ogdyplk0wxw UNIQUE(role_id, usuario_id);<br><br>

-- criando o role para acessos entre admin e user <br>
INSERT INTO role(id, nome_role) VALUES (1, 'ROLE_ADMIN');<br>
INSERT INTO role(id, nome_role) VALUES (2, 'ROLE_USER');<br><br>

-- inserindo dois usuarios bases para poder acessar o sistema <br>
INSERT INTO usuario(id, login, senha) VALUES (1, 'admin', '$2a$10$XQ44XYOBateY8V.sHCgmhOqh8u6PxaUjKgRfIj2Xu1x9.yqHV9cty');<br>
INSERT INTO usuario(id, login, senha) VALUES (2, 'user', '$2a$10$7/RXuG4fbbW1wffaYPnHLeKVtlTrY1gDUlhw2T8AMMyw5Ru4aT2GK');<br><br>

--inserindo algumas profissoes para serem selcionadas <br>
INSERT INTO profissao(id, nome) VALUES (1, 'Programador Java');<br>
INSERT INTO profissao(id, nome) VALUES (2, 'Programador PHP');<br>
INSERT INTO profissao(id, nome) VALUES (3, 'Programador JavaScript');<br>
INSERT INTO profissao(id, nome) VALUES (4, 'Programador Front-End');<br>
INSERT INTO profissao(id, nome) VALUES (5, 'Programador C / C++');<br><br>

-- inserindo um usuario criado com admin e outro como user basico <br>
INSERT INTO usuarios_role(usuario_id, role_id) VALUES (1, 1);<br>
INSERT INTO usuarios_role(usuario_id, role_id) VALUES (2, 2);<br><br>

-- no application pode-se encontrar um main metodo que se executa te retorna-ra varias querys de pessoa para dar insert de uma vez, e uma query para delete destas querys tambem
