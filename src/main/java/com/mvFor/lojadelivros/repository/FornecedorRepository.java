package com.mvFor.lojadelivros.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.mvFor.lojadelivros.model.Fornecedor;
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

}
