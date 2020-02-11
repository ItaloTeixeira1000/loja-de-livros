package com.mvFor.lojadelivros.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvFor.lojadelivros.model.Fornecedor;
import com.mvFor.lojadelivros.model.Livro;
import com.mvFor.lojadelivros.repository.FornecedorRepository;
import com.mvFor.lojadelivros.repository.LivroRepository;
import com.mvFor.lojadelivros.service.exception.FornecedorInexistenteOuInativoException;

@Service
public class LivroService {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired LivroRepository livroRepository;

	public Livro atualizar(@Valid Livro livro, Long codigo) {
		Livro livroSalvo = buscarLivroExistente(codigo);
		
		if(!livro.getFornecedor().equals(livroSalvo.getFornecedor())) {
			validarFornecedor(livro);
		}
		
		BeanUtils.copyProperties(livro, livroSalvo, "codigo");
		
		return livroRepository.save(livroSalvo);
	}

	public Livro salvar(Livro livro) {
		
		validarFornecedor(livro);
		return livroRepository.save(livro);
	}
	
	private void validarFornecedor(Livro livro) {
		Optional<Fornecedor> fornecedor = null;
		
		if(livro.getFornecedor().getCodigo() != null) {
			fornecedor = fornecedorRepository.findById(livro.getFornecedor().getCodigo());
		}
		
		if(!fornecedor.isPresent() || fornecedor.get().isInativo()) {
			throw new FornecedorInexistenteOuInativoException();
		}
		
		
	}
	
	private Livro buscarLivroExistente(Long codigo) {
		Optional<Livro> livroSalvo = livroRepository.findById(codigo);
		
		if(!livroSalvo.isPresent()) {
			throw new IllegalArgumentException();
		}
		return livroSalvo.get();
	}

	
}
