package com.mvFor.lojadelivros.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.mvFor.lojadelivros.repository.filter.FornecedorFilter;
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
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_FORNECEDOR') and #oauth2.hasScope('read')")
	public Page<Fornecedor> pesquisar(FornecedorFilter fornecedorFilter, Pageable pageable){
		return fornecedorRepository.filtrar(fornecedorFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_FORNECEDOR') and #oauth2.hasScope('read')")
	public ResponseEntity<Optional<Fornecedor>> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Fornecedor> fornecedor = fornecedorRepository.findById(codigo);
		return fornecedor.isPresent() ? ResponseEntity.ok(fornecedor) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_FORNECEDOR') and #oauth2.hasScope('write')")
	public ResponseEntity<Fornecedor> criar(@Valid @RequestBody Fornecedor fornecedor, HttpServletResponse response) {
		Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
	}
	

	
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_FORNECEDOR') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	
	public void remover(@PathVariable Long codigo){
		fornecedorRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_FORNECEDOR') and #oauth2.hasScope('write')")
	public ResponseEntity<Fornecedor> atualizar(@PathVariable Long codigo, @Valid @RequestBody Fornecedor fornecedor) {
		Fornecedor fornecedorSalvo = fornecedorService.atualizar(codigo, fornecedor);
		
		return ResponseEntity.ok(fornecedorSalvo);
	}
	
	@PutMapping("/{codigo}/ativo")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_FORNECEDOR') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarAtivo(@PathVariable Long codigo, @RequestBody boolean ativo){
		 
		fornecedorService.atualizarPropriedadeAtivo(codigo, ativo);
	}

}
