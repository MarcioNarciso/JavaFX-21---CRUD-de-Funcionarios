package br.dev.marcionarciso.view;

import br.dev.marcionarciso.Principal;
import br.dev.marcionarciso.service.FuncionarioService;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public abstract class BaseDialogController {

	/**
	 * Stage da janela controlada por esta classe.
	 */
	protected Stage dialogStage;
	/**
	 * Classe principal que controla toda a aplicação.
	 */
	protected Principal appPrincipal;
	/**
	 * Service que lida com a coleção dos funcionários.
	 * Os dados virão deste serviço.
	 */
	protected FuncionarioService funcionarioService;
	
	/**
	 * Recebe o Stage correspondente a esta controller da classe principal para
	 * que o stage possa ser controlado dentro desta classe.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Recebe a classe principal da aplicação.
	 * @param principal
	 */
	public void setAppPrincipal(Principal appPrincipal) {
		this.appPrincipal = appPrincipal;
	}
	
	/**
	 * Recebe o serviço que lida com a coleção dos funcionários.
	 * @param funcionarioService
	 */
	public void setFuncionarioService(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	/**
	 * Executado quando o botão "Fechar" ou "Cancelar" é clicado.
	 * Fecha a janela.
	 */
	@FXML
	private void handleFechar() {
		this.dialogStage.close();
	}
	
}
