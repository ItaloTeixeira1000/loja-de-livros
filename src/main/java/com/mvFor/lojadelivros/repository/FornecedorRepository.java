package com.mvFor.lojadelivros.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.mvFor.lojadelivros.model.Fornecedor;
import com.mvFor.lojadelivros.repository.fornecedor.FornecedorRepositoryQuery;
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>, FornecedorRepositoryQuery {

}
