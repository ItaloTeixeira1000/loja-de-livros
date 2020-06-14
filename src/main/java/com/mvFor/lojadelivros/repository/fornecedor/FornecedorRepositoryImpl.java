package com.mvFor.lojadelivros.repository.fornecedor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.mvFor.lojadelivros.model.Fornecedor;
import com.mvFor.lojadelivros.model.Fornecedor_;
import com.mvFor.lojadelivros.repository.filter.FornecedorFilter;

public class FornecedorRepositoryImpl implements FornecedorRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Fornecedor> filtrar(FornecedorFilter fornecedor, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Fornecedor> criteria = builder.createQuery(Fornecedor.class);
		Root<Fornecedor> root = criteria.from(Fornecedor.class);
		
		Predicate[] predicates = criarRestricoes(fornecedor, builder, root);
		
		criteria.where(predicates);
		
		TypedQuery<Fornecedor> query = manager.createQuery(criteria);
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(fornecedor));
	}

	private Predicate[] criarRestricoes(FornecedorFilter fornecedor, CriteriaBuilder builder, Root<Fornecedor> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(fornecedor.getNomeFantasma())) {
			predicates.add(builder.like(builder.lower(root.get(Fornecedor_.nomeFantasma)), "%" + fornecedor.getNomeFantasma().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(fornecedor.getRazaoSocial())) {
			predicates.add(builder.like(builder.lower(root.get(Fornecedor_.razaoSocial)), "%" + fornecedor.getRazaoSocial().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(fornecedor.getCnpj())) {
			predicates.add(builder.like(builder.lower(root.get(Fornecedor_.cnpj)), "%" + fornecedor.getCnpj().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(fornecedor.getAtivo())) {
			predicates.add(builder.equal(root.get(Fornecedor_.ativo), Integer.parseInt(fornecedor.getAtivo())));
		}
		
		if(!StringUtils.isEmpty(fornecedor.getInscricaoEstadual()) && fornecedor.getInscricaoEstadual() != 0) {
			predicates.add(builder.equal(root.get(Fornecedor_.inscricaoEstadual), fornecedor.getInscricaoEstadual()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(FornecedorFilter fornecedor) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Fornecedor> root = criteria.from(Fornecedor.class);
		
		Predicate[] predicates = criarRestricoes(fornecedor, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}
	
}
