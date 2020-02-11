package com.mvFor.lojadelivros.repository.livro;

import java.util.List;

import com.mvFor.lojadelivros.model.Livro;

public interface LivroRepositoryQuery {
	
	public List<Livro> filtrar(Livro livro);
}
