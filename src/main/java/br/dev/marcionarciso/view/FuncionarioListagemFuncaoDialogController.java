package br.dev.marcionarciso.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import br.dev.marcionarciso.Principal;
import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.utils.BigDecimalUtils;
import br.dev.marcionarciso.utils.DateUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

public class FuncionarioListagemFuncaoDialogController extends BaseDialogController {
	
	/**
	 * Funcionários agrupados por função.
	 */
	private Map<String, List<Funcionario>> funcionarios;
	
	@FXML
	private TreeTableView<Funcionario> tabelaFuncionarios;
	@FXML
	private TreeTableColumn<Funcionario, String> colunaNome;
	@FXML
	private TreeTableColumn<Funcionario, String> colunaDataNascimento;
	@FXML
	private TreeTableColumn<Funcionario, String> colunaSalario;
	@FXML
	private TreeTableColumn<Funcionario, String> colunaFuncao;
	
	@FXML
	private void initialize() {
		initTabelaFuncionarios();
	}
	
	private void initTabelaFuncionarios() {
		/*
		 * Define qual propriedade de Funcionario está em qual coluna e
		 * realiza algumas tratativas nos valores das células. 
		 */
		this.colunaNome.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getNome()));
		
		this.colunaDataNascimento.setCellValueFactory(cellData -> {
			LocalDate dataNascimento = cellData.getValue().getValue().getDataNascimento();
			if (Objects.isNull(dataNascimento)) {
				return null;
			}
			return new ReadOnlyStringWrapper(DateUtils.formatarPadraoBrasileiro(dataNascimento));
		});
		
		this.colunaSalario.setCellValueFactory(cellData -> {
			BigDecimal salario = cellData.getValue().getValue().getSalario();
			if (Objects.isNull(salario)) {
				return null;
			}
			return new ReadOnlyStringWrapper("R$ "+BigDecimalUtils.formatarEmMoeda(salario));	
		});
		
		this.colunaFuncao.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getFuncao()));	
	}
	
	public void setFuncionarios(Map<String, List<Funcionario>> funcionarios) {
		this.funcionarios = funcionarios;
		
		/*
		 * Cria o item raiz e todos os outros itens filhos para popular a tabela. 
		 */
		TreeItem raiz = new TreeItem(new Funcionario("Funções"));
		
		funcionarios.forEach((funcao, listFuncionarios) -> {
			// Cria o divisor da categoria/função
			TreeItem divisorFuncao = new TreeItem(new Funcionario(funcao));	
			
			// Cria os funcionários e os atribui a respectiva categoria
			listFuncionarios.forEach(funcionario -> {
				TreeItem itemFuncionario = new TreeItem(funcionario);
				
				divisorFuncao.getChildren().add(itemFuncionario);
			});
		
			// Adiciona o dividor e seus filhos no elemento raiz da TreeTableView
			raiz.getChildren().add(divisorFuncao);
		});
		
		// Adiciona as informaçẽos à tabela
		this.tabelaFuncionarios.setRoot(raiz);
	}

}
