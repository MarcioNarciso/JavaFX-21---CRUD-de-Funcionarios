package br.dev.marcionarciso.repository;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.dev.marcionarciso.model.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FuncionarioRepository {
	
	private final static ObservableList<Funcionario> FUNCIONARIOS = loadFuncionarios();
	
	private static ObservableList<Funcionario> loadFuncionarios(){
		if (Objects.nonNull(FUNCIONARIOS)) {
			return FUNCIONARIOS;
		}
		
		URL json = FuncionarioRepository.class.getResource("/funcionarios.json");
		
		ObjectMapper mapper = new ObjectMapper();
		// Necessário registrar este módulo para a lib conseguir converter JSON para LocalDate
		mapper.registerModule(new JavaTimeModule());
		
		try {
			// Lê os funcionários do arquivo JSON e os converte em uma lista de Funcionario
			List<Funcionario> funcionarios = mapper.readValue(json, new TypeReference<List< Funcionario>>() {});
			 
			return FXCollections.observableArrayList(funcionarios); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public ObservableList<Funcionario> getAll(){
		return FUNCIONARIOS;
	}

	public Boolean add(Funcionario f) {
		return FUNCIONARIOS.add(f);
	}
	
	public Boolean remove(Funcionario f) {
		return FUNCIONARIOS.remove(f);
	}
	
}
