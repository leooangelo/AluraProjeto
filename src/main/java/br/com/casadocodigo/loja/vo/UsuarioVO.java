package br.com.casadocodigo.loja.vo;

import java.util.ArrayList;
import java.util.List;

import br.com.casadocodigo.loja.models.Role;

public class UsuarioVO {	
	
	private String nome;
	private	String email;
	

	private List<RoleVO> roles = new ArrayList<>();
	
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
	public List<RoleVO> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}
	

}
