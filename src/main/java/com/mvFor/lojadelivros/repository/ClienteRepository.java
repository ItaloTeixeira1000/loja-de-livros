package com.mvFor.lojadelivros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvFor.lojadelivros.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	

}
