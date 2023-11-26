package br.dev.marcionarciso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.service.FuncionarioService;
import br.dev.marcionarciso.view.FuncionarioFormDialogController;
import br.dev.marcionarciso.view.FuncionarioOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe principal que inicia a aplicação.
 */
public class Principal extends Application {
	
	/**
	 * Stage da tela principal.
	 */
	private Stage mainStage;
	
	/**
	 * Controller da tela principal.
	 */
	private FuncionarioOverviewController funcionarioOverviewController;
	
	/**
	 * Service que lida com a persistência dos funcionários.
	 */
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
    	// Define o ícone da aplicação
    	stage.getIcons().add(new Image("file:/resources/images/app-icon.png"));
    	// Define o título da tela principal da aplicação
    	stage.setTitle("Gerenciador de Funcionários");
    	
    	this.mainStage = stage;
    	
    	initFuncionarioOverview();
    }

    
    /**
     * Inicia a tela principal.
     */
    private void initFuncionarioOverview() {
    		
    	try {
    		// Busca o FXML que descreve a interface da tela principal.
        	URL principalFxml = getClass().getResource("view/FuncionarioOverview.fxml");

        	// Carrega a tela do FXML
    		FXMLLoader viewLoader = new FXMLLoader();
    		viewLoader.setLocation(principalFxml);
    		AnchorPane rootLayout = (AnchorPane) viewLoader.load();
    		
            // Atribui o painel principal à cena principal
            this.mainStage.setScene(new Scene(rootLayout));
            this.mainStage.show();
    		
            // Atribui a aplicação principal ao controller, para ser acessível de lá
    		this.funcionarioOverviewController = viewLoader.getController();
    		this.funcionarioOverviewController.setAppPrincipal(this);

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
     * Abre uma janela com o formulário de cadastro de funcionário.
     * Se o parâmetro "funcionario" for fornecido, o formulário será preenchido
     * com esses dados e o funcionário poderá ser editado.
     * @param funcionario
     * @return true se o usuário clicou no botão "ok". Caso ele tenha clicado no 
     * botão "Cancelar", retorna false.
     */
	public Boolean showFuncionarioFormDialog(Funcionario funcionario) {
		
		try {
			// Carrega o arquivo FXML e cria o seu painel 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/br/dev/marcionarciso/view/FuncionarioFormDialog.fxml"));
			AnchorPane dialogPane = (AnchorPane) loader.load();
			
			// Cria um novo stage para o painel/dialog do formulario
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Editar Funcionário");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.mainStage);
			dialogStage.setScene(new Scene(dialogPane));
			
			// Busca a controller do formulário
			FuncionarioFormDialogController	controller = loader.getController();
			// Passa as informações do formulário para a controller
			controller.setDialogStage(dialogStage);
			controller.setFuncionario(funcionario);
			controller.setAppPrincipal(this);
			
			dialogStage.showAndWait();
			
			Boolean okClicado = controller.isOkClicado();
			
			if (okClicado) {
				this.funcionarioOverviewController.refreshTabelaFuncionarios();
			}
			
			return okClicado; 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
    
    /**
     * Cria uma janela de alerta.
     * @param titulo
     * @param conteudo
     * @param type
     */
	public Alert showAlert(String titulo, String conteudo, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(conteudo);
		
		return alert;
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