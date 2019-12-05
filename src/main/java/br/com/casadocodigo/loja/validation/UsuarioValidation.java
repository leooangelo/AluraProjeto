package br.com.casadocodigo.loja.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.dao.UsuarioDAO;
import br.com.casadocodigo.loja.models.Usuario; 

@Component
public class UsuarioValidation implements Validator {


    private UsuarioDAO dao;	
    
    public UsuarioValidation(UsuarioDAO dao) {
    	this.dao = dao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "nome", "field.required");
        ValidationUtils.rejectIfEmpty(errors, "email", "field.required");
        ValidationUtils.rejectIfEmpty(errors, "senha", "field.required");

        Usuario usuario = (Usuario) target;
        String senha = usuario.getSenha();
        String senha2 = usuario.getSenha2();

        String email = usuario.getUsername();

        if (dao.emailExiste(email)) {
            System.out.println("E-mail já existe");
        } else if (!senha.equals(senha2) && !senha.isEmpty()) {
            errors.rejectValue("senha", "field.required.usuario.senha.confere");
        } else if (senha.length() <= 4 && !senha.isEmpty()) {
            errors.rejectValue("senha", "typeMismatch.usuario.senha");
        }

    }

}