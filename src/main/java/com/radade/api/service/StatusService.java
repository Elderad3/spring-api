package com.radade.api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.radade.api.model.Status;
import com.radade.api.repository.StatusRepository;


@Service
public class StatusService {
	
	@Autowired
	private StatusRepository repository;
	
	public List<Status> listarTodos(){
			return this.repository.findAll();
		}
	
	public Page<Status> listar(int page, int count){
    Pageable  pages = PageRequest.of(page, count);
		return this.repository.findAll(pages);
	}
	
	public Status salvar(Status status) {
		return repository.save(status);		
	}
	
	public Status buscarPorId(Long id) {
		Optional<Status> status = repository.findById(id);
		return status.get();
	}
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}
	

}
