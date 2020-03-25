package com.mvFor.lojadelivros.repository.projection;

public class ResumoCompra {
	private Long codigo;
	private String descricao;
	private long qtd;
	private String usuario;
	private String livro;
	
	public ResumoCompra(Long codigo, String descricao, long qtd, String usuario, String livro) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.qtd = qtd;
		this.usuario = usuario;
		this.livro = livro;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getQtd() {
		return qtd;
	}

	public void setQtd(long qtd) {
		this.qtd = qtd;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getLivro() {
		return livro;
	}

	public void setLivro(String livro) {
		this.livro = livro;
	}
	
	
	
	
	
}
