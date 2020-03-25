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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.mvFor.lojadelivros.model.Livro;
import com.mvFor.lojadelivros.repository.LivroRepository;
import com.mvFor.lojadelivros.service.LivroService;
import com.mvFor.lojadelivros.service.exception.FornecedorInexistenteOuInativoException;

@RestController
@RequestMapping("/livros")
public class LivroResource {
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LivroService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LIVRO') and #oauth2.hasScope('read')")
	public Page<Livro> pesquisar(Livro livro, Pageable pageable){
		return livroRepository.filtrar(livro, pageable);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LIVRO') and #oauth2.hasScope('read')")
	public ResponseEntity<Optional<Livro>> buscarPeloCodigo(@PathVariable Long codigo){
		Optional<Livro> livroSalvo = livroRepository.findById(codigo);
		return livroSalvo.isPresent() ? ResponseEntity.ok(livroSalvo) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LIVRO') and #oauth2.hasScope('write')")
	public ResponseEntity<Livro> criar(@Valid @RequestBody Livro livro, HttpServletResponse response){
		Livro livroSalvo = service.salvar(livro);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, livro.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
	}
	
	@ExceptionHandler({FornecedorInexistenteOuInativoException.class})
	public ResponseEntity<Object> handleFornecedorInexistenteOuInativoException(FornecedorInexistenteOuInativoException ex){
		String mensagemUsuario = messageSource.getMessage("fornecedor.inexistente-ou-inativo",null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LIVRO') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		livroRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LIVRO') and #oauth2.hasScope('write')")
	public ResponseEntity<Livro> atualizar(@Valid @RequestBody Livro livro, @PathVariable Long codigo ){
		
		try {
			
			Livro livroSalvo = service.atualizar(livro, codigo);
			return ResponseEntity.ok(livroSalvo);
			
		} catch (IllegalArgumentException e) {
			
			return ResponseEntity.notFound().build();
		}
		
		
	}
}
