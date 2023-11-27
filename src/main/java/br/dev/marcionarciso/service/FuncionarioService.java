package br.dev.marcionarciso.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.repository.FuncionarioRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class FuncionarioService {
	
	private FuncionarioRepository repository;
	private ObservableList<Funcionario> funcionarios;
	
	public FuncionarioService() throws Exception {
		this.repository = new FuncionarioRepository();
		// Converte a List de funcionários vinda do repositório em uma ObservableList
		this.funcionarios = FXCollections.observableArrayList(this.repository.getAll());
	}

	public ObservableList<Funcionario> getAll() {
		return this.funcionarios;
	}
	
	/**
	 * Dado um nome, exclui todos os funcionários com o nome correspondente.
	 * @param nome
	 * @return
	 */
	public Boolean removeFuncionarioByNome(String nome){
		return this.funcionarios.removeIf(funcionario -> funcionario.getNome().trim().equals(nome.trim()));
	}
	
	/**
	 * Aumenta o salário de todos os funcionários com base em uma porcentagem.
	 * @param porcentagem Valor de 0 a 100.
	 */
	public void aumentarSalarioDeTodosEmPorcentagem(Double porcentagem) {
		BigDecimal porcentagemConvertida = new BigDecimal((porcentagem / 100) + 1);
		
		this.funcionarios.stream()
			.forEach(funcionario -> {
				funcionario.setSalario(funcionario.getSalario().multiply(porcentagemConvertida));
			});
	}
	
	/**
	 * Agrupa os funcionários por função em um MAP: sendo a chave do MAP o nome 
	 * da função e o valor uma LIST com os funcionários.
	 * @return
	 */
	public Map<String, List<Funcionario>> getFuncionariosAgrupadosPorFuncao() {
		Map<String, List<Funcionario>> funcionariosAgrupados = new HashMap();
		
		this.funcionarios
			.forEach(funcionario -> {
				String funcao = funcionario.getFuncao();
				
				// Obtém a lista de funcionários da função
				List<Funcionario> funcionariosFuncao = funcionariosAgrupados.get(funcao);
				
				// Verifica se a lista existe 
				if (Objects.isNull(funcionariosFuncao)) {
					// Se não existir, a lista de funcionários é criada
					funcionariosFuncao = new ArrayList();
					funcionariosAgrupados.put(funcao, funcionariosFuncao);
				}
				
				// Adiciona o funcionário à lista de funcionários da função
				funcionariosFuncao.add(funcionario);
			});
		
		return funcionariosAgrupados;
	}
	
	/**
	 * Retorna todos os funcionários que nasceram em algum mês passado por parâmetro.
	 * @param meses
	 * @return
	 */
	public ObservableList<Funcionario> getFuncionariosByMesNascimento(Integer...meses) {
		List listaDeMeses = Arrays.asList(meses);
		
		return this.funcionarios
				.stream()
				.filter(funcionario -> {
					return listaDeMeses.contains(funcionario.getDataNascimento().getMonthValue());
				})
				.collect(Collectors.toCollection(FXCollections::observableArrayList));
	}
}
