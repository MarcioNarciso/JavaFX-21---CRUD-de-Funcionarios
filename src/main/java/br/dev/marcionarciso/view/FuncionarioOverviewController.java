package br.dev.marcionarciso.view;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import br.dev.marcionarciso.Principal;
import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.utils.BigDecimalUtils;
import br.dev.marcionarciso.utils.DateUtils;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FuncionarioOverviewController {
	
	/**
	 * Classe principal da aplicação.
	 */
	private Principal appPrincipal;

	@FXML
	private TableView<Funcionario> tabelaFuncionarios;
	@FXML
	private TableColumn<Funcionario, String> colunaNome;
	@FXML
	private TableColumn<Funcionario, String> colunaDataNascimento;
	@FXML
	private TableColumn<Funcionario, String> colunaSalario;
	@FXML
	private TableColumn<Funcionario, String> colunaFuncao;
	
	/**
	 * Método executado após o carregamento do arquivo FXML.
	 */
	@FXML
	private void initialize() {
		initTabelaFuncionarios();
		
		// Reseta a seleção
		selecionarFuncionario(null);
		
//		initListeners();
	}
	
	private void initTabelaFuncionarios() {
		/*
		 * Define qual propriedade de Funcionario está em qual coluna. 
		 */
		this.colunaNome.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(cellData.getValue().getNome()));
		
		
		this.colunaDataNascimento.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtils.formatarPadraoBrasileiro(cellData.getValue().getDataNascimento())));
		
		this.colunaSalario.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(BigDecimalUtils.formatarEmMoeda(cellData.getValue().getSalario())));
		this.colunaFuncao.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFuncao()));
	}
	
	/*
	private void initListeners() {
		//Detecta a mudança na seleção de funcionários na tabela.
		this.tabelaFuncionarios.getSelectionModel().selectedItemProperty()
			.addListener((observable, oldValue, newValue) -> selecionarFuncionario(newValue));
	}
	*/
	
	/**
	 * Define a ação dos botões "Excluir" e "Editar" com base em um Funcionario.
	 * @param funcionario
	 */
	private void selecionarFuncionario(Funcionario funcionario) {
		
	}
	
	
	@FXML
	private void handleExclusaoFuncionario() {
		int selectedIndex = this.tabelaFuncionarios.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex >= 0) {
			this.tabelaFuncionarios.getItems().remove(selectedIndex);
			return;
		}
		
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Atenção!");
		alert.setHeaderText(null);
		alert.setContentText("Por favor, selecione um funcionário.");
		alert.show();
		
	}
	
	public void setAppPrincipal(Principal principal) {
		this.appPrincipal = principal;
		
		// Adiciona os dados da ObservableList à tabela de funcionários
		this.tabelaFuncionarios.setItems(principal.getDadosFuncionarios());
	}
}
