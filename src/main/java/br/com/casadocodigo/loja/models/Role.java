package br.com.casadocodigo.loja.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority{


	private static final long serialVersionUID = 1L;

	@Id
	private String nome;
	@ManyToMany(mappedBy ="roles")
	List <Usuario> usuarios;
	
	public Role(){
	}

	public Role(String nome) {
	  this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String getAuthority() {
		return this.nome;
	}
}
