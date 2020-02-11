package com.mvFor.lojadelivros.repository.compra;

import java.util.List;

import com.mvFor.lojadelivros.model.Compra;

public interface CompraRepositoryQuery {
	
	public List<Compra> filtrar(Compra compra);
}
