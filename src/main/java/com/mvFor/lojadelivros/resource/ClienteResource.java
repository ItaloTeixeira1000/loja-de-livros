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
import com.mvFor.lojadelivros.model.Cliente;
import com.mvFor.lojadelivros.repository.ClienteRepository;
import com.mvFor.lojadelivros.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public List<Cliente> pesquisar(){
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Cliente>> buscarPelodigo(@PathVariable Long codigo) {
		Optional<Cliente> cliente = clienteRepository.findById(codigo);
		return cliente.isPresent() ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
		Cliente clienteSalvo = clienteRepository.save(cliente);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, cliente.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		clienteRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long codigo, @Valid @RequestBody Cliente cliente){
		Cliente clienteSalvo = clienteService.atualizar(codigo, cliente);
		
		return ResponseEntity.ok(clienteSalvo);
		
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @Valid @RequestBody boolean ativo){
		
		clienteService.atualizarPropriedadeAtivo(codigo, ativo);
		
	}
}
