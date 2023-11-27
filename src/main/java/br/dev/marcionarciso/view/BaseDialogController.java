package br.dev.marcionarciso.view;

import br.dev.marcionarciso.Principal;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class BaseDialogController {

	/**
	 * Stage da janela controlada por esta classe.
	 */
	protected Stage dialogStage;
	/**
	 * Classe principal que controla toda a aplicação.
	 */
	protected Principal appPrincipal;
	
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
	 * Executado quando o botão "Fechar" ou "Cancelar" é clicado.
	 * Fecha a janela.
	 */
	@FXML
	private void handleFechar() {
		this.dialogStage.close();
	}
	
}
