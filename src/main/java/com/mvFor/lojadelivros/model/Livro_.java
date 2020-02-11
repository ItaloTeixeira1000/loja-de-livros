package com.mvFor.lojadelivros.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Livro.class)
public abstract class Livro_ {

	public static volatile SingularAttribute<Livro, Double> preco;
	public static volatile SingularAttribute<Livro, Integer> paginas;
	public static volatile SingularAttribute<Livro, Long> codigo;
	public static volatile SingularAttribute<Livro, Long> isbn;
	public static volatile SingularAttribute<Livro, String> titulo;
	public static volatile SingularAttribute<Livro, LocalDate> dataPublicacao;
	public static volatile SingularAttribute<Livro, Fornecedor> fornecedor;
	public static volatile SingularAttribute<Livro, String> autor;
	public static volatile SingularAttribute<Livro, String> descricao;

}

