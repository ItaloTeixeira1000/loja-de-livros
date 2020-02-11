package com.mvFor.lojadelivros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvFor.lojadelivros.model.Livro;
import com.mvFor.lojadelivros.repository.livro.LivroRepositoryQuery;

public interface LivroRepository extends JpaRepository<Livro, Long>, LivroRepositoryQuery{

}
