package br.com.casadocodigo.loja.controllers;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;

@Controller
public class RelatoriosProdutosController {

		
	@Autowired
    private ProdutoDAO produtoDao;

	@RequestMapping(value = "/relatorio-produtos", method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public List<Produto> detalheJson(@RequestParam(value = "data", required = false) String dataLancamento) throws Exception{
		if(dataLancamento != null) {
		SimpleDateFormat formatadorDeData = new SimpleDateFormat("yyyy-MM-dd");
	    Date dataFormatada = formatadorDeData.parse(dataLancamento);
	    Calendar data = Calendar.getInstance();
	    data.setTime(dataFormatada);
		    return produtoDao.findData(data);
		}
		
		return produtoDao.listar();
		}
	
	
    

}

