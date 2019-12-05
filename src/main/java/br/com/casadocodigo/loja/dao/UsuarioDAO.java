package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Role;
import br.com.casadocodigo.loja.models.Usuario;

@Repository
public class UsuarioDAO implements UserDetailsService{

	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
    private ProdutoDAO produtoDao;

	public void gravar(Usuario usuario) {
		manager.persist(usuario);
	}
	
	public Usuario loadUserByUsername(String email) {
		List<Usuario> usuarios = manager.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
				.setParameter("email", email)
				.getResultList();
		
		if(usuarios.isEmpty()) {
			throw new UsernameNotFoundException("Usuario " + email + " não foi encontrado");
		}
		
		return usuarios.get(0);
	}
	public boolean emailExiste(String email) {
		 List<Usuario> usuarios = manager.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
	                .setParameter("email", email).getResultList();

	        if (!usuarios.isEmpty()) {
	            return true;
	        } else
	            return false;
	}
	public Usuario find(String email) {
		return manager.createQuery("select u from Usuario u where u.email = :email ", Usuario.class)
				.setParameter("email", email)
				.getSingleResult();
	}
	public List<Usuario> listar() {
		TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u " , Usuario.class);
			return query.getResultList();		
	}

	@Transactional
	public void update(Usuario usuario ) {
		 manager.merge(usuario);
	}
	



	


}