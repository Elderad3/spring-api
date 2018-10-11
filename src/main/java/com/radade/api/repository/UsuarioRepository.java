package com.radade.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radade.api.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

 Usuario findByEmail (String email);
}
