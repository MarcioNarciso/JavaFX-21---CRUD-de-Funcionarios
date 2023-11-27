package br.dev.marcionarciso.repository;

import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.dev.marcionarciso.model.Funcionario;

public class FuncionarioRepository {

	public List<Funcionario> getAll() throws Exception {
		URL json = getClass().getResource("/funcionarios.json");
		
		ObjectMapper mapper = new ObjectMapper();
		// Necessário registrar este módulo para a lib conseguir converter JSON para LocalDate
		mapper.registerModule(new JavaTimeModule());
		
		try {
			// Lê os funcionários do arquivo JSON e os converte em uma lista de Funcionario
			 return mapper.readValue(json, new TypeReference<List< Funcionario>>() {});
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
