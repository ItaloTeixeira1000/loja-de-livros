package com.mvFor.lojadelivros.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Compra.class)
public abstract class Compra_ {

	public static volatile SingularAttribute<Compra, Long> qtd;
	public static volatile SingularAttribute<Compra, Cliente> cliente;
	public static volatile SingularAttribute<Compra, Long> codigo;
	public static volatile SingularAttribute<Compra, Livro> livro;
	public static volatile SingularAttribute<Compra, LocalDate> dataVencimento;
	public static volatile SingularAttribute<Compra, LocalDate> dataCompra;
	public static volatile SingularAttribute<Compra, String> descricao;

}

