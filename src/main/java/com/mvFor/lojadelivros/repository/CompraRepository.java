package com.mvFor.lojadelivros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvFor.lojadelivros.model.Compra;
import com.mvFor.lojadelivros.repository.compra.CompraRepositoryQuery;

public interface CompraRepository extends JpaRepository<Compra, Long>, CompraRepositoryQuery{

}
