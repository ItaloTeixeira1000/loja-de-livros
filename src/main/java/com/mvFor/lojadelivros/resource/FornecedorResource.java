package com.mvFor.lojadelivros.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mvFor.lojadelivros.event.RecursoCriadoEvent;
import com.mvFor.lojadelivros.model.Fornecedor;
import com.mvFor.lojadelivros.repository.FornecedorRepository;
import com.mvFor.lojadelivros.service.FornecedorService;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorResource {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private FornecedorService fornecedorService;
	
	@GetMapping
	public List<Fornecedor> pesquisar(){
		return fornecedorRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Fornecedor>> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Fornecedor> fornecedor = fornecedorRepository.findById(codigo);
		return fornecedor.isPresent() ? ResponseEntity.ok(fornecedor) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Fornecedor> criar(@Valid @RequestBody Fornecedor fornecedor, HttpServletResponse response) {
		Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
	}
	

	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	
	public void remover(@PathVariable Long codigo){
		fornecedorRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Fornecedor> atualizar(@PathVariable Long codigo, @Valid @RequestBody Fornecedor fornecedor) {
		Fornecedor fornecedorSalvo = fornecedorService.atualizar(codigo, fornecedor);
		
		return ResponseEntity.ok(fornecedorSalvo);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarAtivo(@PathVariable Long codigo, @RequestBody boolean ativo){
		 
		fornecedorService.atualizarPropriedadeAtivo(codigo, ativo);
	}

}
