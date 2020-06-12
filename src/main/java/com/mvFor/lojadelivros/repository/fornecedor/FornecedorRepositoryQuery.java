package com.mvFor.lojadelivros.repository.fornecedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mvFor.lojadelivros.model.Fornecedor;
import com.mvFor.lojadelivros.repository.filter.FornecedorFilter;

public interface FornecedorRepositoryQuery {

	public Page<Fornecedor> filtrar(FornecedorFilter fornecedorFilter, Pageable pageable);
}
