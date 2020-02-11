create table compra (
	codigo BIGINT(20) primary key auto_increment,
    codigo_cliente BIGINT(50) not null,
    data_compra DATE not null,
    foreign key (codigo_cliente) references cliente(codigo)
) ENGINE= InnoDB DEFAULT CHARSET=utf8;