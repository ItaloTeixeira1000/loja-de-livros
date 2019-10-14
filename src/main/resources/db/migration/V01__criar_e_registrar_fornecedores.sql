create table fornecedor (
	codigo bigInt(50) primary key auto_increment,
    razao_social varchar(50) not null,
    nome_fantasma varchar(50) not null,
    ativo boolean not null,
    cnpj varchar(50) not null,
    incricao_estadual bigint(50),
    telefone varchar(30),
	logradouro varchar(50),
    numero varchar(50),
    complemento varchar(50),
    bairro varchar(50),
    cep varchar(50),
    cidade varchar(50),
    estado varchar(50)
)Engine=InnoDB DEFAULT CHARSET=utf8;