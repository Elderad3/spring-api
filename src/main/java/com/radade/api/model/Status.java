package com.radade.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="SPA_STATUS")
public class Status extends AbstractEntity<Long> {
	
	//@NotNull
	//@Size(min = 3, max = 20)
	@Column(name="NOM_STATUS")
	private String nome;
	
	@Column(name="DSC_STATUS")
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
