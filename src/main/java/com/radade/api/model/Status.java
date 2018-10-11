package com.radade.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name="RAD_STATUS")
public class Status extends AbstractEntity<Long> {
	

	@NotNull
	@Size(min = 5, max = 50)
	private String nome;
	
	@Size(max = 100)
	private String descricao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
    

}
