package com.radade.api.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radade.api.model.Usuario;
import com.radade.api.response.Response;
import com.radade.api.service.UsuarioService;

/**
 * Classe de controle e validação de dados recebidos do Front
 * @author Elder
 * 
 */

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Lista todos os dados
	 * @return {@link Response}
	 */
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<Usuario>>> listarTodos() {
		Response<List<Usuario>>response = new Response<List<Usuario>>();
		List<Usuario> listaDeUsuario = this.service.listarTodos();
		response.setData(listaDeUsuario);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Lista os dados limitando-o por página e quantidade de registro por página
	 * @param page
	 * @param count
	 * @return {@link Response}
	 */


	@GetMapping("/{page}/{count}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<Usuario>>> listar(@PathVariable int page, @PathVariable int count ) {
		Response<Page<Usuario>>response = new Response<Page<Usuario>>();
		Page<Usuario> listaDeUsuario = this.service.listar(page, count);
		response.setData(listaDeUsuario);
		return ResponseEntity.ok(response);
	}

	/**
	 * Valida os dados recebidos e realiza a persistência no banco
	 * @param usuario
	 * @param result
	 * @return {@link Response}
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Usuario>> adicionar(@RequestBody @Valid Usuario usuario, BindingResult result) {
		Response<Usuario> response = new Response<Usuario>();
		try {
			if (result.hasErrors()) {
				result.getAllErrors().forEach(erros -> response.getErrors().add(erros.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			Usuario usuarioSalvo = this.service.salvar(usuario);
			response.setData(usuarioSalvo);
		} catch (DuplicateKeyException e) {
			response.getErrors().add("Email já existente");
			return ResponseEntity.badRequest().body(response);
		}catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	/**
	 * Valida os dados recebidos e realiza a alteração no banco
	 * @param usuario
	 * @param result
	 * @return {@link Response}
	 */
	
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Usuario>> atualizar(@Valid @RequestBody  Usuario usuario, BindingResult result) {
		Response<Usuario> response = new Response<Usuario>();
		try {
			this.validarUsuarioAoAlterar(usuario, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(erros -> response.getErrors().add(erros.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			Usuario usuarioSalvo = this.service.salvar(usuario);
			response.setData(usuarioSalvo);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna um registro com base no identificador
	 * @param id
	 * @return  {@link Response}
	 */

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Usuario>> buscarPorId(@PathVariable Long id) {
		Response<Usuario> response = new Response<Usuario>();
		Usuario usuario = this.service.buscarPorId(id);
		if (usuario == null) {
			response.getErrors().add("Registro não encontrado com o Id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(usuario);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Deleta um registro com base no identificador
	 * @param id
	 * @return  {@link Response}
	 */

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> excluir(@PathVariable Long id) {
		Response<String> response = new Response<String>();
		if(id == null) {
			response.getErrors().add("Registro não encontrado para o Id: " + id);
					}
		this.service.excluir(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Validação para identificador não informado
	 * @param usuario
	 * @param result
	 */
	
	public void validarUsuarioAoAlterar(Usuario usuario, BindingResult result) {
		if(usuario.getId() == null ) {
			result.addError(new ObjectError("Usuario", "Id não Informado"));
		}
	}

}

