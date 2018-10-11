package com.radade.api.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.radade.api.model.Status;
import com.radade.api.response.Response;
import com.radade.api.service.StatusService;

@RestController
@RequestMapping("/status")
@CrossOrigin(origins = "*")
public class StatusController {

	@Autowired
	private StatusService service;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<List<Status>>> listarTodos() {
		Response<List<Status>>response = new Response<List<Status>>();
		List<Status> listaDeStatus = this.service.listarTodos();
		response.setData(listaDeStatus);
		return ResponseEntity.ok(response);
	}


	@GetMapping("/{page}/{count}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Page<Status>>> listar(@PathVariable int page, @PathVariable int count ) {
		Response<Page<Status>>response = new Response<Page<Status>>();
		Page<Status> listaDeStatus = this.service.listar(page, count);
		response.setData(listaDeStatus);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Status>> adicionar(@RequestBody @Valid Status status, BindingResult result) {
		Response<Status> response = new Response<Status>();
		try {
			if (result.hasErrors()) {
				result.getAllErrors().forEach(erros -> response.getErrors().add(erros.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Status statusSalvo = this.service.salvar(status);
			response.setData(statusSalvo);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Status>> atualizar(@Valid @RequestBody  Status status, BindingResult result) {
		Response<Status> response = new Response<Status>();
		try {
			this.validarStatusAoAlterar(status, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(erros -> response.getErrors().add(erros.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Status statusSalvo = this.service.salvar(status);
			response.setData(statusSalvo);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<Status>> buscarPorId(@PathVariable Long id) {
		Response<Status> response = new Response<Status>();
		Status status = this.service.buscarPorId(id);
		if (status == null) {
			response.getErrors().add("Registro não encontrado com o Id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(status);
		return ResponseEntity.ok(response);
	}

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
	
	public void validarStatusAoAlterar(Status status, BindingResult result) {
		if(status.getId() == null ) {
			result.addError(new ObjectError("Status", "Id não Informado"));
		}
	}

}
