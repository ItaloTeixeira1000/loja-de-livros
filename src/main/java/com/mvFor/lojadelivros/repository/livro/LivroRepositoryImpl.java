package com.mvFor.lojadelivros.repository.livro;

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

import com.mvFor.lojadelivros.model.Livro;
import com.mvFor.lojadelivros.model.Livro_;

public class LivroRepositoryImpl implements LivroRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Livro> filtrar(Livro livro, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Livro> criteria = builder.createQuery(Livro.class);
		Root<Livro> root = criteria.from(Livro.class);
		
		Predicate[] predicates = criarRestricoes(livro, builder, root);
		
		criteria.where(predicates);
		
		TypedQuery<Livro> query = manager.createQuery(criteria);
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(livro));
	}

	private Long total(Livro livro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Livro> root = criteria.from(Livro.class);
		
		Predicate[] predicates = criarRestricoes(livro, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroPorPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroPorPagina);
		query.setMaxResults(totalRegistrosPorPagina);
		
	}

	private Predicate[] criarRestricoes(Livro livro, CriteriaBuilder builder, Root<Livro> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(livro.getTitulo())) {
			
			predicates.add(builder.like(builder.lower(root.get(Livro_.titulo)), "%" + livro.getTitulo().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(livro.getAutor())) {

			predicates.add(builder.like(builder.lower(root.get(Livro_.autor)), "%" + livro.getAutor().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(livro.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Livro_.descricao)), "%" + livro.getDescricao().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(livro.getIsbn())) {
			predicates.add(builder.equal(root.get(Livro_.isbn), livro.getIsbn()));
		}
		
		if(!StringUtils.isEmpty(livro.getPaginas()) && livro.getPaginas() != 0) {
			predicates.add(builder.equal(root.get(Livro_.paginas), livro.getPaginas() ));
		}
		
		if(!StringUtils.isEmpty(livro.getPreco()) && livro.getPreco() != 0) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Livro_.preco), livro.getPreco()));
		}
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
