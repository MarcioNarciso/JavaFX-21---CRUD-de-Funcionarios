package br.dev.marcionarciso.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.repository.FuncionarioRepository;
import br.dev.marcionarciso.utils.DateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class FuncionarioService {
	
	private FuncionarioRepository repository;
	
	public FuncionarioService() {
		this.repository = new FuncionarioRepository();
	}
	
	public Boolean add(Funcionario f) {
		return this.repository.add(f);
	}
	
	public Boolean remove(Funcionario f) {
		return this.repository.remove(f);
	}

	public ObservableList<Funcionario> getAll() {
		return this.repository.getAll();
	}
	
	/**
	 * Dado um nome, exclui todos os funcionários com o nome correspondente.
	 * @param nome
	 * @return
	 */
	public Boolean removeFuncionarioByNome(String nome){
		return this.repository.getAll()
				.removeIf(funcionario -> funcionario.getNome().trim().equals(nome.trim()));
	}
	
	/**
	 * Aumenta o salário de todos os funcionários com base em uma porcentagem.
	 * @param porcentagem Valor de 0 a 100.
	 */
	public void aumentarSalarioDeTodosEmPorcentagem(Double porcentagem) {
		BigDecimal porcentagemConvertida = new BigDecimal((porcentagem / 100) + 1);
		
		this.repository.getAll()
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
		
		this.repository.getAll()
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
		
		return this.repository.getAll()
				.stream()
				.filter(funcionario -> {
					return listaDeMeses.contains(funcionario.getDataNascimento().getMonthValue());
				})
				.collect(Collectors.toCollection(FXCollections::observableArrayList));
	}

	/**
	 * Retorna o funcionário com a maior idade.
	 * @return
	 */
	public Funcionario getFuncionarioComMaiorIdade() {
		LocalDate dataAtual = LocalDate.now();
		
		return this.repository.getAll()
			.stream()
			.sorted((f1, f2) -> {
				Long idadeF1 = DateUtils.calcularDiferencaEmDias(f1.getDataNascimento(), dataAtual);
				Long idadeF2 = DateUtils.calcularDiferencaEmDias(f2.getDataNascimento(), dataAtual);
				
				return idadeF2.compareTo(idadeF1);
			})
			.findFirst()
			.orElse(null);
	}
	
	public void getFuncionariosNomesOrdemAlfabetica(Boolean isCrescente) {
		final Boolean isOrdemCrescente = Objects.isNull(isCrescente) ? true : isCrescente;
		
		/**
		 * Utilizei o método sort de List porque ele ele reordena a própria lista.
		 * O método sorted de Stream cria uma lista nova reordenada, o que 
		 * estava ocasionando a perda da vantagem da ObservableList.
		 */
		
		this.repository.getAll()
			.sort((f1, f2) -> {
				if (isOrdemCrescente) {
					return f1.getNome().compareTo(f2.getNome());
				} else {
					return f2.getNome().compareTo(f1.getNome());
				}
			});
	}
}
