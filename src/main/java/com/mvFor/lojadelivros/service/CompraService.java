package com.mvFor.lojadelivros.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvFor.lojadelivros.model.Compra;
import com.mvFor.lojadelivros.model.Usuario;
import com.mvFor.lojadelivros.repository.CompraRepository;
import com.mvFor.lojadelivros.repository.UsuarioRepository;
import com.mvFor.lojadelivros.service.exception.ClienteInexistenteOuInativoException;

@Service
public class CompraService {
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Compra salvar(Compra compra) {
		
		validarUsuario(compra);
		
		return compraRepository.save(compra);
	}

	public Compra atualizar(Long codigo, Compra compra) {
		Compra compraSalva = buscarCompraExistente(codigo);
		
		if(!compra.getUsuario().equals(compraSalva.getUsuario())) {
			validarUsuario(compra);
		}
		
		BeanUtils.copyProperties(compra, compraSalva, "codigo");
		
		return compraRepository.save(compraSalva);
	}
	
	
	public void validarUsuario(Compra compra) {
		Optional<Usuario> usuario = null;
		
		if(compra.getUsuario().getCodigo() != null) {
			usuario = usuarioRepository.findById(compra.getUsuario().getCodigo());
		}
		
		if(!usuario.isPresent()) {
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
