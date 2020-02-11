package com.mvFor.lojadelivros.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Fornecedor.class)
public abstract class Fornecedor_ {

	public static volatile SingularAttribute<Fornecedor, Long> codigo;
	public static volatile SingularAttribute<Fornecedor, Boolean> ativo;
	public static volatile SingularAttribute<Fornecedor, String> telefone;
	public static volatile SingularAttribute<Fornecedor, String> nomeFantasma;
	public static volatile SingularAttribute<Fornecedor, Long> inscricaoEstadual;
	public static volatile SingularAttribute<Fornecedor, EnderecoFornecedor> enderecoFornecedor;
	public static volatile SingularAttribute<Fornecedor, String> cnpj;
	public static volatile SingularAttribute<Fornecedor, String> razaoSocial;

}

