package br.dev.marcionarciso.view;

import br.dev.marcionarciso.Principal;
import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.utils.BigDecimalUtils;
import br.dev.marcionarciso.utils.DateUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import static br.dev.marcionarciso.utils.StringUtils.isEmpty;

import java.math.BigDecimal;
import java.util.Objects;

public class FuncionarioFormDialogController {
	
	/**
	 * Class principal da aplicação.
	 */
	private Principal appPrincipal;
	
	@FXML
	private TextField campoNome;
	@FXML
	private TextField campoDataNascimento;
	@FXML
	private TextField campoSalario;
	@FXML
	private TextField campoFuncao;
	
	/*
	 * Janela do formulário
	 */
	private Stage dialogStage;
	/*
	 * Funcionário editado no formulário
	 */
	private Funcionario funcionario;
	/*
	 * Se o usuário clicar no botão "Ok", é definido para true.
	 */
	private Boolean okClicado = false;
	
	
	@FXML
	private void initialize() {
		
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Define o funcionário que será editado.
	 * @param funcionario
	 */
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
		
		this.campoNome.setText(funcionario.getNome());
		this.campoDataNascimento.setText(DateUtils.formatarPadraoBrasileiro(funcionario.getDataNascimento()));
		this.campoDataNascimento.setPromptText(DateUtils.PADRAO_DATA_BRASIL);
		this.campoSalario.setText(BigDecimalUtils.formatarEmMoeda(funcionario.getSalario()));
		this.campoFuncao.setText(funcionario.getFuncao());
	}
	
	public Boolean isOkClicado() {
		return this.okClicado;
	}
	
	/**
	 * Executado quando o usuário clicar no botão "Cancelar".
	 */
	@FXML
	private void handleCancelar() {
		this.dialogStage.close();
	}
	
	/**
	 * Executado quando o usuário clicar no botão "Ok".
	 */
	@FXML
	private void handleOk() {
		if (isEntradaValida()) {
			this.funcionario.setNome(this.campoNome.getText());
			this.funcionario.setDataNascimento(DateUtils.converterDeString(this.campoDataNascimento.getText()));
			this.funcionario.setSalario(BigDecimalUtils.converterDeMoeda(this.campoSalario.getText()));
			this.funcionario.setFuncao(this.campoFuncao.getText());
			
			this.okClicado = true;
			this.dialogStage.close();
		}
	}
	
	private Boolean isEntradaValida() {
		String mensagemDeErro = "";
		
		if (isEmpty(this.campoNome.getText())) {
			mensagemDeErro += "Nome inválido!\n";
		}
		
		if (isEmpty(this.campoDataNascimento.getText())) {
			mensagemDeErro += "Data de Nascimento inválida!\n";
		} else {
			if (! DateUtils.isDataValida(this.campoDataNascimento.getText())) {
				mensagemDeErro += "Data de Nascimento inválida!\n";
			}
		}
		
		if (isEmpty(this.campoSalario.getText())) {
			mensagemDeErro += "Salário inválido\n";
		} else {
			BigDecimal salario = BigDecimalUtils.converterDeMoeda(this.campoSalario.getText());
			
			if (Objects.isNull(salario)) {
				mensagemDeErro = "Salário inválido\n";
			}
		}
		
		if (isEmpty(this.campoFuncao.getText())) {
			mensagemDeErro += "Função inválida\n";
		}
		
		if (isEmpty(mensagemDeErro)) {
			return true;
		}
		
		this.appPrincipal.showAlert("Campos inválidos", 
									mensagemDeErro, 
									AlertType.ERROR).showAndWait();
		
		return false;
	}

	public void setAppPrincipal(Principal principal) {
		this.appPrincipal = principal;
	}
}
