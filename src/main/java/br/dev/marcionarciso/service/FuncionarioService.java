package br.dev.marcionarciso.service;

import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.dev.marcionarciso.model.Funcionario;


public class FuncionarioService {

	public List<Funcionario> getAll() throws Exception {
		URL json = getClass().getResource("/funcionarios.json");
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.registerModule(new JavaTimeModule());
		
		// Lê os funcionários do arquivo JSON e os converte em uma lista de Funcionario
		List<Funcionario> funcionarios = mapper.readValue(json, new TypeReference<List< Funcionario>>() {});
		
		return funcionarios;
	}
	
}
