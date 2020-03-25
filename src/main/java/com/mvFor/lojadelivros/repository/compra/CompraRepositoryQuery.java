package com.mvFor.lojadelivros.repository.compra;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mvFor.lojadelivros.model.Compra;
import com.mvFor.lojadelivros.repository.filter.CompraFilter;
import com.mvFor.lojadelivros.repository.projection.ResumoCompra;

public interface CompraRepositoryQuery {
	
	public List<Compra> filtrar(CompraFilter compra);
	
	public Page<ResumoCompra> resumir(CompraFilter compra, Pageable pageable);
}
