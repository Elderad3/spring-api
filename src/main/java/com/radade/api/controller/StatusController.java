package com.radade.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.radade.api.service.StatusService;

@RestController
@RequestMapping("/status")
@CrossOrigin(origins="*")
public class StatusController {
	
	@Autowired
	private StatusService service;
	
	@GetMapping
	public List<Status> listar(){
		return service.listar();
	}
	
	@PostMapping
	public Status adicionar( @RequestBody Status status) {
		return service.adicionar(status);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Status>> buscar(@PathVariable Long id){
		Optional<Status> status = service.buscar(id);
		if(status == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(status);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Optional<Status>> atualizar(@PathVariable Long id, @Valid @RequestBody Status status){
		Optional<Status> statusExistente = service.buscar(id);
		if(!statusExistente.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(status, statusExistente, "id");
		//statusExistente = service.adicionar(statusExistente);
		return ResponseEntity.ok(statusExistente);
	}
	
	//@DeleteMapping("/{id}")
	//public ResponseEntity<void> excluir(@PathVariable Long id){
	//	Optional<Status> status = service.buscar(id);
	//	if(status == null) {
	//		return ResponseEntity.notFound().build();
	//	}
	//	service.excluir(status);
	//	return ResponseEntity.noContent().build();
	//}

}
