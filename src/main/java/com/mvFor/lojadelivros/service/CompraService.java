package com.mvFor.lojadelivros.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvFor.lojadelivros.model.Cliente;
import com.mvFor.lojadelivros.model.Compra;
import com.mvFor.lojadelivros.repository.ClienteRepository;
import com.mvFor.lojadelivros.repository.CompraRepository;
import com.mvFor.lojadelivros.service.exception.ClienteInexistenteOuInativoException;

@Service
public class CompraService {
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Compra salvar(Compra compra) {
		
		validarCliente(compra);
		
		return compraRepository.save(compra);
	}

	public Compra atualizar(Long codigo, Compra compra) {
		Compra compraSalva = buscarCompraExistente(codigo);
		
		if(!compra.getCliente().equals(compraSalva.getCliente())) {
			validarCliente(compra);
		}
		
		BeanUtils.copyProperties(compra, compraSalva, "codigo");
		
		return compraRepository.save(compraSalva);
	}

	private void validarCliente(Compra compra) {
		Optional<Cliente> cliente = null;
		
		if(compra.getCliente().getCodigo() != null) {
			cliente = clienteRepository.findById(compra.getCliente().getCodigo());
		}
		
		if(!cliente.isPresent() || cliente.get().isInativo()) {
			throw new ClienteInexistenteOuInativoException();
		}
		
	}

	private Compra buscarCompraExistente(Long codigo) {
		Optional<Compra> compraSalva = compraRepository.findById(codigo);
		
		if(!compraSalva.isPresent()) {
			throw new IllegalArgumentException();
		}
		return compraSalva.get();
	}
}
