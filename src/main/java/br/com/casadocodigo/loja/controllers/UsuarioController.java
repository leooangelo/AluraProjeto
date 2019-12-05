package br.com.casadocodigo.loja.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.RoleDAO;
import br.com.casadocodigo.loja.dao.UsuarioDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Role;
import br.com.casadocodigo.loja.models.Usuario;
import br.com.casadocodigo.loja.validation.UsuarioValidation;
import br.com.casadocodigo.loja.vo.RoleVO;
import br.com.casadocodigo.loja.vo.UsuarioVO;


@Controller
public class UsuarioController {	
	
	@Autowired
	private RoleDAO roleDao;
	
	@Autowired
    private UsuarioDAO dao;
	
	@Autowired
    private FileSaver fileSaver;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new UsuarioValidation(dao));
    }
    
    @RequestMapping("usuarios/form")
    private ModelAndView form(Usuario usuario) {
    	ModelAndView modelAndView = new ModelAndView("usuarios/form");
    	return modelAndView;
    }

    @RequestMapping(value = "usuarios/form", method = RequestMethod.POST)
    @Transactional
        public ModelAndView gravar(@Valid Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes) {

            if (result.hasErrors()) {
                return form(usuario);
            }

            Role roleUser = new Role("ROLE_USER");
            usuario.setRoles(Arrays.asList(roleUser));
            dao.gravar(usuario);

            redirectAttributes.addFlashAttribute("message", "Usuário " + usuario.getNome() + " cadastrado com sucesso!");

            return new ModelAndView("redirect:/usuarios");
        }
    
    @RequestMapping("/usuarios")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("/usuarios");
		modelAndView.addObject("usuarios", traduzirParaVo(dao.listar()));
		return modelAndView;
	}
    private List<UsuarioVO> traduzirParaVo(List<Usuario> listar) {
    	List<UsuarioVO> usuarios = new ArrayList<>();
    	for(Usuario o : listar) {
    		UsuarioVO uvo = new UsuarioVO();
    		uvo.setNome(o.getNome());
    		uvo.setEmail(o.getEmail());
    		uvo.setRoles(traduzirRoleVO(o.getRoles()));
    		
    		usuarios.add(uvo);	
    	}
    	return usuarios;
    	
    }
    
    @GetMapping("/roles")
    public ModelAndView listarRoles(@RequestParam String email){
    	List<RoleVO> role = traduzirRoleVO(roleDao.listarRole());
    	ModelAndView modelAndView = new ModelAndView("/roles");
    	modelAndView.addObject("role", role);
    	modelAndView.addObject("emailLogado", email);
    	return modelAndView;
    }
    	 
    private List<RoleVO> traduzirRoleVO(List<Role> listar){
    	List<RoleVO> role = new ArrayList<>();
    	for(Role r : listar) {
    	RoleVO ro = new RoleVO();
    	ro.setNome(r.getNome());
    	role.add(ro);
    	}
    	return role;	  	

    }
    
    @PostMapping("/roles")
    public ModelAndView rolesAlterar(@RequestParam String email, @RequestParam List<String> rolesCheck ) {
        email= email.split(",")[1];
    	Usuario usuario = dao.find(email);
        List<Role> roles = new ArrayList<>();
        ModelAndView modelAndView = new ModelAndView("/roles");
        for(String r : rolesCheck) {
        	Role roleUser = new Role();
        	roleUser.setNome(r);
        	roles.add(roleUser);
        }
        
        usuario.setRoles(roles);
        dao.update(usuario);
        
        System.out.println(usuario);
        modelAndView.addObject("usuario", usuario);
        return new ModelAndView("redirect:/usuarios");
    }
    
    
}




