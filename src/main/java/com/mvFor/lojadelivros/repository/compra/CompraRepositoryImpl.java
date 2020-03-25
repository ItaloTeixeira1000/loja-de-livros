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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.mvFor.lojadelivros.model.Compra;
import com.mvFor.lojadelivros.model.Compra_;
import com.mvFor.lojadelivros.model.Livro_;
import com.mvFor.lojadelivros.model.Usuario_;
import com.mvFor.lojadelivros.repository.filter.CompraFilter;
import com.mvFor.lojadelivros.repository.projection.ResumoCompra;

public class CompraRepositoryImpl implements CompraRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Compra> filtrar(CompraFilter compra) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Compra> criteria = builder.createQuery(Compra.class);
		Root<Compra> root = criteria.from(Compra.class);
		
		
		
		Predicate[] predicates = criarRestricoes(compra, builder, root);
		
		criteria.where(predicates);
		
		TypedQuery<Compra> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	@Override
	public Page<ResumoCompra> resumir(CompraFilter compra, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoCompra> criteria = builder.createQuery(ResumoCompra.class);
		Root<Compra> root = criteria.from(Compra.class);
		
		criteria.select(builder.construct(ResumoCompra.class, 
						root.get(Compra_.codigo), root.get(Compra_.descricao),
						root.get(Compra_.qtd), root.get(Compra_.usuario).get(Usuario_.nome),
						root.get(Compra_.livro).get(Livro_.titulo)));
		
		Predicate[] predicates = criarRestricoes(compra, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoCompra> query = manager.createQuery(criteria);
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(compra));
	}


	private Long total(CompraFilter compra) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Compra> root = criteria.from(Compra.class);
		
		Predicate[] predicates = criarRestricoes(compra, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
		
	}

	private Predicate[] criarRestricoes(CompraFilter compra, CriteriaBuilder builder, Root<Compra> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(compra.getDescricao())) {
			
			predicates.add(builder.like(builder.lower(root.get(Compra_.descricao)), "%" + compra.getDescricao().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(compra.getQtd()) && compra.getQtd() != 0) {
			
			predicates.add(builder.equal(root.get(Compra_.qtd), compra.getQtd()));
		}
		
		if(!StringUtils.isEmpty(compra.getUsuario())) {
			predicates.add(builder.like(
					builder.lower(root.get(Compra_.usuario).get("nome")), "%" + compra.getUsuario().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(compra.getLivro())) {
			predicates.add(builder.like(
					builder.lower(root.get(Compra_.livro).get("titulo")), "%" + compra.getLivro().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(compra.getDataCompra())) {
			predicates.add(
					builder.equal(root.get(Compra_.dataCompra), compra.getDataCompra()));
		}
		
		if (compra.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Compra_.dataVencimento), compra.getDataVencimentoDe()));
		}
		
		if (compra.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Compra_.dataVencimento),compra.getDataVencimentoAte()));
		}
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	
	
}
