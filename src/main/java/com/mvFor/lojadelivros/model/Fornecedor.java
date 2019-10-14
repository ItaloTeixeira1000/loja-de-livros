package com.mvFor.lojadelivros.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fornecedor")
public class Fornecedor {
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(name="razao_social")
	private String razaoSocial;
	
	@Column(name = "nome_fantasma")
	private String nomeFantasma;
	
	private boolean ativo;
	
	private String cnpj;
	
	@Column(name= "incricao_estadual")
	private Long inscricaoEstadual;
	
	private String telefone;
	
	@Embedded
	private EnderecoFornecedor enderecoFornecedor;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasma() {
		return nomeFantasma;
	}

	public void setNomeFantasma(String nomeFantasma) {
		this.nomeFantasma = nomeFantasma;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Long getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(Long inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public EnderecoFornecedor getEnderecoFornecedor() {
		return enderecoFornecedor;
	}

	public void setEnderecoFornecedor(EnderecoFornecedor enderecoFornecedor) {
		this.enderecoFornecedor = enderecoFornecedor;
	}
}
