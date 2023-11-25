package br.dev.marcionarciso;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Principal extends Application {
	
	private Stage mainStage;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
    	stage.setTitle("Gerenciador de Funcionários");
    	this.mainStage = stage;

    	initFuncionarioOverview();
    }

    
    /**
     * Inicia a tela principal.
     */
    public void initFuncionarioOverview() {
    		
    	try {
    		// Busca o XML que descreve a interface da tela principal.
        	URL principalFxml = getClass().getResource("/br/dev/marcionarciso/view/FuncionarioOverview.fxml");
        	
            AnchorPane rootLayout = (AnchorPane) FXMLLoader.load(principalFxml);
            
            // Atribui o painel principal à cena principal
            this.mainStage.setScene(new Scene(rootLayout));
            this.mainStage.show();
            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	

	}
    
    /**
     * Retorna a tela principal.
     * @return
     */
    public Stage getMainStage() {
		return this.mainStage;
	}
}