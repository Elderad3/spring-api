package com.radade.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.radade.api.model.Status;
import com.radade.api.repository.StatusRepository;


@Service
public class StatusService {
	
	@Autowired
	private StatusRepository repository;
	
	public List<Status>listar(){
		return repository.findAll();
	}
	
	public Status adicionar(Status status) {
		return repository.save(status);		
	}
	
	public Optional<Status> buscar(Long id) {
		return repository.findById(id);
	}
	
	public void excluir(Status status) {
		repository.delete(status);
	}
	

}
