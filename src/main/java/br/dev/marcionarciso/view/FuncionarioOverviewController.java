package br.dev.marcionarciso.view;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
	}
	
	private void initTabelaFuncionarios() {
		/*
		 * Define qual propriedade de Funcionario está em qual coluna. 
		 */
		this.colunaNome.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(cellData.getValue().getNome()));
		
		
		this.colunaDataNascimento.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtils.formatarPadraoBrasileiro(cellData.getValue().getDataNascimento())));
		
		this.colunaSalario.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("R$ "+BigDecimalUtils.formatarEmMoeda(cellData.getValue().getSalario())));
		this.colunaFuncao.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFuncao()));
	}
		
	public void setAppPrincipal(Principal principal) {
		this.appPrincipal = principal;
		
		// Adiciona os dados da ObservableList à tabela de funcionários
		this.tabelaFuncionarios.setItems(principal.getDadosFuncionarios());
	}
	
	public void refreshTabelaFuncionarios() {
		this.tabelaFuncionarios.refresh();
	}
		
	/**
	 * Executado quando o usuário clicar no botão "Excluir".
	 */
	@FXML
	private void handleExclusaoFuncionario() {
		int selectedIndex = this.tabelaFuncionarios.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex >= 0) {
			this.tabelaFuncionarios.getItems().remove(selectedIndex);
			return;
		}
		
		this.appPrincipal.showAlert("Atenção!", 
									"Por favor, selecione um funcionário.", 
									AlertType.WARNING).show();
		
	}
	
	/**
	 * Executado quando o usuário clicar no botão "Novo".
	 * Abre uma janela com o formulário para criar um novo funcionário.
	 */
	@FXML
	private void handleNovoFuncionario() {
		Funcionario f = new Funcionario();
		
		Boolean okClicado = this.appPrincipal.showFuncionarioFormDialog(f);
		
		if (okClicado) {
			this.appPrincipal.getDadosFuncionarios().add(f);
		}
	}
	
	/**
	 * Executado quando o usuário clicar no botão "Editar".
	 * Abre uma janela com o formulário preenchido com os dados do funcionário
	 * para a edição.
	 */
	@FXML
	private void handleEditarFuncionario() {
		Funcionario funcionarioSelecionado = this.tabelaFuncionarios.getSelectionModel().getSelectedItem();
		
		if (Objects.isNull(funcionarioSelecionado)) {
			this.appPrincipal.showAlert("Atenção!", 
										"Por favor, selecione um funcionário.", 
										AlertType.WARNING).show();
			return;
		}
		
		this.appPrincipal.showFuncionarioFormDialog(funcionarioSelecionado);
	}

}
