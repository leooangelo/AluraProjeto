package br.com.casadocodigo.loja.vo;

import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

public class RoleVO implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String nome;
	
	public RoleVO(){
	}

	public RoleVO(String nome) {
	  this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getAuthority() {
		return this.nome;
	}

}
