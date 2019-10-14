package com.mvFor.lojadelivros.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mvFor.lojadelivros.model.Fornecedor;
import com.mvFor.lojadelivros.repository.FornecedorRepository;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorResource {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@GetMapping
	public List<Fornecedor> pesquisar(){
		return fornecedorRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Fornecedor> criar(@RequestBody Fornecedor fornecedor, HttpServletResponse response) {
		Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
					.buildAndExpand(fornecedorSalvo.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(fornecedorSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Fornecedor>> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Fornecedor> fornecedor = fornecedorRepository.findById(codigo);
		
		return fornecedor.isPresent() ? ResponseEntity.ok(fornecedor) : ResponseEntity.notFound().build();
	}

}
