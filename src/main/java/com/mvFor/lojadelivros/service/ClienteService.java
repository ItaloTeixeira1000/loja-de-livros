package com.mvFor.lojadelivros.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mvFor.lojadelivros.model.Cliente;
import com.mvFor.lojadelivros.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente atualizar(Long codigo, Cliente cliente) {
		Cliente clienteSalvo = buscarPeloCodigo(codigo);
		
		BeanUtils.copyProperties(cliente, clienteSalvo, "codigo");
		return clienteRepository.save(clienteSalvo);
	}
	
	public void atualizarPropriedadeAtivo(Long codigo, boolean ativo) {
		Cliente clienteSalvo = buscarPeloCodigo(codigo);
		clienteSalvo.setAtivo(ativo);
		clienteRepository.save(clienteSalvo);
	}

	private Cliente buscarPeloCodigo(Long codigo) {
		Optional<Cliente> clienteSalvo = clienteRepository.findById(codigo);
		
		if(!clienteSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return clienteSalvo.get();
	}
}
