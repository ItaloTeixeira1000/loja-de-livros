package com.mvFor.lojadelivros.repository.livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mvFor.lojadelivros.model.Livro;

public interface LivroRepositoryQuery {
	
	public Page<Livro> filtrar(Livro livro, Pageable pageable);
}
