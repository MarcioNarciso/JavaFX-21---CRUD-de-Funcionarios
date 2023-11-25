package br.dev.marcionarciso;

import java.net.URL;
import java.util.List;

import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.service.FuncionarioService;
import br.dev.marcionarciso.view.FuncionarioOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Classe principal que inicia a aplicação.
 */
public class Principal extends Application {
	
	private Stage mainStage;
	private FuncionarioService funcionarioService;
	
	/**
	 * Dados dos funcionários como uma ObservableList.
	 */
	private ObservableList<Funcionario> dadosFuncionarios = FXCollections.observableArrayList();
	
	/**
	 * Construtor
	 */
	public Principal() {
		this.funcionarioService = new FuncionarioService();
		loadFuncionarios();
	}
	
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
    private void initFuncionarioOverview() {
    		
    	try {
    		// Busca o XML que descreve a interface da tela principal.
        	URL principalFxml = getClass().getResource("/br/dev/marcionarciso/view/FuncionarioOverview.fxml");
        	
        	// Carrega a tela do FXML
    		FXMLLoader viewLoader = new FXMLLoader();
    		viewLoader.setLocation(principalFxml);
    		AnchorPane rootLayout = (AnchorPane) viewLoader.load();
    		
            // Atribui o painel principal à cena principal
            this.mainStage.setScene(new Scene(rootLayout));
            this.mainStage.show();
    		
            // Atribui a aplicação principal ao controller, para ser acessível de lá
    		FuncionarioOverviewController controller = viewLoader.getController();
    		controller.setAppPrincipal(this);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
    
    /**
     * Busca os funcionários e os insere na ObservableList.
     */
    private void loadFuncionarios() {
		try {
			List<Funcionario> funcionarios = this.funcionarioService.getAll();
			
			// Insere os funcionários na ObservableList
			funcionarios
				.stream()
				.forEach(this.dadosFuncionarios::add);
			
		} catch (Exception e) {
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
    
    /**
     * Retorna os funcionários como uma ObservableList.
     * @return
     */
    public ObservableList<Funcionario> getDadosFuncionarios() {
		return this.dadosFuncionarios;
	}
}