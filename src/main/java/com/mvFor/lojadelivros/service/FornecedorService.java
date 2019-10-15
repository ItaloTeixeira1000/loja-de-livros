package com.mvFor.lojadelivros.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mvFor.lojadelivros.model.Fornecedor;
import com.mvFor.lojadelivros.repository.FornecedorRepository;

@Service
public class FornecedorService {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	public Fornecedor atualizar(Long codigo, Fornecedor fornecedor) {
		Fornecedor fornecedorSalvo = buscarPeloCodigo(codigo);
		BeanUtils.copyProperties(fornecedor, fornecedorSalvo, "codigo");
		
		return fornecedorRepository.save(fornecedorSalvo);
	}
	
	public void atualizarPropriedadeAtivo(Long codigo, boolean ativo) {
		Fornecedor fornecedorSalvo = buscarPeloCodigo(codigo);
		fornecedorSalvo.setAtivo(ativo);		
		fornecedorRepository.save(fornecedorSalvo);
	}
	
	private Fornecedor buscarPeloCodigo(Long codigo) {
		Optional<Fornecedor> fornecedorSalvo = fornecedorRepository.findById(codigo);
		if(!fornecedorSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return fornecedorSalvo.get();
	}

	
}
