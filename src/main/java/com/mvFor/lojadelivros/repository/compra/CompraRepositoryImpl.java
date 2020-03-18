package com.mvFor.lojadelivros.repository.compra;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.mvFor.lojadelivros.model.Compra;
import com.mvFor.lojadelivros.model.Compra_;

public class CompraRepositoryImpl implements CompraRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Compra> filtrar(Compra compra) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Compra> criteria = builder.createQuery(Compra.class);
		Root<Compra> root = criteria.from(Compra.class);
		
		
		
		Predicate[] predicates = criarRestricoes(compra, builder, root);
		
		criteria.where(predicates);
		
		TypedQuery<Compra> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(Compra compra, CriteriaBuilder builder, Root<Compra> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		
		if(!StringUtils.isEmpty(compra.getQtd()) && compra.getQtd() != 0) {
			
			predicates.add(builder.equal(root.get(Compra_.qtd), compra.getQtd()));
		}
		
		if(!StringUtils.isEmpty(compra.getDescricao())) {
			
			predicates.add(builder.like(builder.lower(root.get(Compra_.descricao)), "%" + compra.getDescricao().toLowerCase() + "%"));
		}
		
		if(compra.getUsuario() != null) {
			
			predicates.add(builder.equal(root.get(Compra_.usuario), compra.getUsuario().getCodigo()));
		}
		
		if(compra.getLivro() != null) {
			
			predicates.add(builder.equal(root.get(Compra_.livro), compra.getLivro().getCodigo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	
}
