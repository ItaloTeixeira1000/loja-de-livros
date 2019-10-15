create table cliente (
	codigo bigint(50) primary key auto_increment,
	nome varchar(50) not null,
	telefone varchar(20),
	sexo varchar(10) not null,
	ativo boolean not null,
	cpf varchar(20) not null,
	cidade varchar(50),
	estado varchar(2),
	bairro varchar(50),
	complemento varchar(50),
	logradouro varchar(50),
	numero varchar(20),
	cep varchar(20)
)Engine=InnoDB DEFAULT CHARSET=utf8;