package com.radade.api.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.radade.api.model.enums.Perfil;

@SuppressWarnings("serial")
@Entity
@Table(name = "RAD_USUARIO")
public class Usuario extends AbstractEntity<Long> {
	
	@Column(unique=true, nullable=false)
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@Column(unique=true, nullable=false)
	@NotBlank(message = "Email é obrigatório")
	@NotNull(message = "Email não pode ser nulo")
	@Email(message = "Email inválido")
	private String email;
	
	@NotNull(message = "Senha não pode ser nula")
	@NotBlank(message = "A senha é obrigatória")
	@Size(min = 6, message="O mínimo requerido para a senha é 6 caracters")
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Perfil perfil;



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Perfil getPerfil() {
		return perfil;
	}



	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}


	}
