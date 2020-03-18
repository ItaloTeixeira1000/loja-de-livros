package com.mvFor.lojadelivros.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mvFor.lojadelivros.event.RecursoCriadoEvent;
import com.mvFor.lojadelivros.exceptionHandler.LojaDeLivrosExceptionHandler.Erro;
import com.mvFor.lojadelivros.model.Compra;
import com.mvFor.lojadelivros.repository.CompraRepository;
import com.mvFor.lojadelivros.service.CompraService;
import com.mvFor.lojadelivros.service.exception.ClienteInexistenteOuInativoException;

@RestController
@RequestMapping("/compras")
public class CompraResource {
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CompraService compraService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_COMPRA') and #oauth2.hasScope('read')")
	public List<Compra> pesquisar(Compra compra){
		
		return compraRepository.filtrar(compra);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_COMPRA') and #oauth2.hasScope('read')")
	public ResponseEntity<Optional<Compra>> buscarPeloCodigo(@PathVariable Long codigo){
		Optional<Compra> compra = compraRepository.findById(codigo);
		
		return compra.isPresent() ? ResponseEntity.ok(compra) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_COMPRA') and #oauth2.hasScope('write')")
	public ResponseEntity<?> criar(@Valid @RequestBody Compra compra, HttpServletResponse response){
		
		Compra compraSalva = compraService.salvar(compra);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, compra.getUsuario().getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(compraSalva);
		
	}
	
	@ExceptionHandler({ClienteInexistenteOuInativoException.class})
	public ResponseEntity<Object> handleClienteInexistenteOuInativoException(ClienteInexistenteOuInativoException ex){
		String mensagemUsuario = messageSource.getMessage("cliente.inexistente-ou-inativo", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_COMPRA') and #oauth2.hasScope('write')")
	public ResponseEntity<Compra> atualizar(@PathVariable Long codigo, @Valid @RequestBody Compra compra){
		try {
			Compra compraSalva = compraService.atualizar(codigo, compra); 
			return ResponseEntity.ok(compraSalva);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_COMPRA') and #oauth2.hasScope('write')")
	public void deletar(@PathVariable Long codigo){
		
		compraRepository.deleteById(codigo);
	}
}
