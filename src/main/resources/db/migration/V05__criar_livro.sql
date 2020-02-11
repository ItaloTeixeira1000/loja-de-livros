create table livro(
	codigo bigint(50) primary key auto_increment,
    titulo varchar(40) not null,
    autor varchar(50),
    descricao varchar(50),
    isbn bigint(20) not null,
    data_publicacao date,
    paginas int,
    codigo_fornecedor bigint(50) not null,
    preco double not null,
    foreign key(codigo_fornecedor) references fornecedor(codigo)
) Engine=InnoDB DEFAULT CHARSET=utf8;