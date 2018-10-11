package com.radade.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.radade.api.model.Usuario;
import com.radade.api.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario pesquisarPorEmail(String email) {
		return repository.findByEmail(email);
	}
	
	public List<Usuario> listarTodos(){
			return this.repository.findAll();
		}
	
	public Page<Usuario> listar(int page, int count){
    Pageable  pages = PageRequest.of(page, count);
		return this.repository.findAll(pages);
	}
	
	public Usuario salvar(Usuario usuario) {
		return repository.save(usuario);		
	}
	
	public Usuario buscarPorId(Long id) {
		Optional<Usuario> usuario = repository.findById(id);
		return usuario.get();
	}
	
	public void excluir(Long id) {
		repository.deleteById(id);
	}
	

}
