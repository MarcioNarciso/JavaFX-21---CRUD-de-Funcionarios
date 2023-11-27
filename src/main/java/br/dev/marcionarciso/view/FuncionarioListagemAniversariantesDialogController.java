package br.dev.marcionarciso.view;

import java.text.DecimalFormat;

import br.dev.marcionarciso.Principal;
import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.utils.BigDecimalUtils;
import br.dev.marcionarciso.utils.DateUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class FuncionarioListagemAniversariantesDialogController extends BaseDialogController {
	
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
			
	/**
	 * Atribui à TableView os funcionários que fazem aniversário.
	 */
	public void setFuncionariosAniversariantes(ObservableList<Funcionario> aniversariantes) {
		this.tabelaFuncionarios.setItems(aniversariantes);
	}
	
	
}
