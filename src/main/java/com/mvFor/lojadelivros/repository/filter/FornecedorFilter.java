package com.mvFor.lojadelivros.repository.filter;

public class FornecedorFilter {
	
	private String razaoSocial;
	
	private String nomeFantasma;
	
	private String cnpj;
	
	private String ativo;
	
	private Long inscricaoEstadual;

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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public Long getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(Long inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
	
	
}
