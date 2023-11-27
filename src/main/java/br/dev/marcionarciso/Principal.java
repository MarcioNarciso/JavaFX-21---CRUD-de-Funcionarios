package br.dev.marcionarciso;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.service.FuncionarioService;
import br.dev.marcionarciso.utils.FXMLLoaderFactory;
import br.dev.marcionarciso.utils.StageFactory;
import br.dev.marcionarciso.view.FuncionarioFormDialogController;
import br.dev.marcionarciso.view.FuncionarioFormIdadeDialogController;
import br.dev.marcionarciso.view.FuncionarioListagemAniversariantesDialogController;
import br.dev.marcionarciso.view.FuncionarioListagemFuncaoDialogController;
import br.dev.marcionarciso.view.FuncionarioOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
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
	 * Service que lida com a coleção dos funcionários.
	 * Os dados virão deste serviço.
	 */
	private FuncionarioService funcionarioService;
	
	/**
	 * Construtor
	 */
	public Principal() {
		this.funcionarioService = new FuncionarioService();
	}
	
	/**
     * Retorna a tela principal.
     * @return
     */
    public Stage getMainStage() {
		return this.mainStage;
	}
    
    public FuncionarioService getFuncionarioService() {
    	return this.funcionarioService;
		
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
    		
            // Atribui o painel principal à cena principal e a exibe
            this.mainStage.setScene(new Scene(rootLayout));
            this.mainStage.show();
    		
            // Busca o controller da tela principal.
    		this.funcionarioOverviewController = viewLoader.getController();
    		/*
    		 * Atribui a classe principal da aplicação ao controller da tela 
    		 * principal, para ser acessível de lá.
    		 * Também realiza o vínculo da TableView com a ObservableList de 
    		 * funcionários.
    		 */
    		this.funcionarioOverviewController.setAppPrincipal(this);
    		this.funcionarioOverviewController.setFuncionarioService(this.funcionarioService);
    		this.funcionarioOverviewController.setFuncionarios(this.funcionarioService.getAll());

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
			FXMLLoader fxmlLoader = FXMLLoaderFactory.create("/br/dev/marcionarciso/view/FuncionarioFormDialog.fxml");
			AnchorPane dialogPane = (AnchorPane) fxmlLoader.load();
			
			// Cria a nova janela que será uma modal
			Stage dialogStage = StageFactory.create(new Scene(dialogPane), // Cria uma nova cena com o conteúdo que deve ser exibido na nova janela
													"Editar Funcionário",
													this.mainStage); // Define a janela principal como "dona" da nova janela
			
			// Busca a controller do formulário
			FuncionarioFormDialogController	controller = fxmlLoader.getController();
			// Passa as informações do formulário para a controller
			controller.setDialogStage(dialogStage);
			controller.setFuncionario(funcionario);
			controller.setAppPrincipal(this);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicado(); 
			
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
	public Optional<ButtonType> showAlert(String titulo, String conteudo, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(conteudo);
		return alert.showAndWait();
	}
	
	/**
	 * Abre uma janela com uma tabela listando todos funcionários agrupados por
	 * funções.
	 */
	public void showFuncionariosListagemFuncaoDialog() {

		try {
			FXMLLoader fxmlLoader = FXMLLoaderFactory.create("/br/dev/marcionarciso/view/FuncionarioListagemFuncaoDialog.fxml");
			// Carrega o painel que é o conteúdo da nova janela
			AnchorPane dialogPane = (AnchorPane) fxmlLoader.load();
			
			// Cria a nova janela que será uma modal
			Stage dialogStage = StageFactory.create(new Scene(dialogPane), // Cria uma nova cena com o conteúdo que deve ser exibido na nova janela
													"Funcionários por Função",
													this.mainStage); // Define a janela principal como "dona" da nova janela
			
			// Busca a classe que controla o conteúdo da nova janela
			FuncionarioListagemFuncaoDialogController controller = fxmlLoader.getController();
			controller.setAppPrincipal(this);
			controller.setFuncionarios(this.funcionarioService.getFuncionariosAgrupadosPorFuncao());
			controller.setDialogStage(dialogStage);
			
			// Exibe/abre a nova janela criada
			dialogStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Abre uma janela com uma tabela listando todos os funcionários que fazem
	 * aniversário nos meses especificados.
	 * @param meses Lista de meses em que os funcionários fazem aniversário.
	 */
	public void showFuncionariosListagemAniversariantesDialog(Integer...meses) {
		
		try {
			FXMLLoader fxmlLoader = FXMLLoaderFactory.create("/br/dev/marcionarciso/view/FuncionarioListagemAniversariantesDialog.fxml");
			// Carrega o painel que é o conteúdo da nova janela
			AnchorPane dialogPane = (AnchorPane) fxmlLoader.load();
			
			// Cria a nova janela que será uma modal
			Stage dialogStage = StageFactory.create(new Scene(dialogPane), // Cria uma nova cena com o conteúdo que deve ser exibido na nova janela
													"Funcionários Aniversariantes",
													this.mainStage); // Define a janela principal como "dona" da nova janela
			
			// Busca a classe que controla o conteúdo da nova janela
			FuncionarioListagemAniversariantesDialogController controller = fxmlLoader.getController();
			controller.setAppPrincipal(this);
			controller.setDialogStage(dialogStage);
			controller.setFuncionariosAniversariantes(this.funcionarioService.getFuncionariosByMesNascimento(meses));
			
			// Exibe/abre a nova janela criada
			dialogStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Abre uma janela com as informações "nome" e "idade" do funcionário.
	 */
	public void showFuncionarioFormIdadeDialog(Funcionario funcionario) {
		
		try {
			FXMLLoader fxmlLoader = FXMLLoaderFactory.create("/br/dev/marcionarciso/view/FuncionarioFormIdadeDialog.fxml");
			AnchorPane dialogPane = fxmlLoader.load(); // Conteúdo da janela
			
			// Cria a nova janela como uma modal
			Stage dialogStage = StageFactory.create(new Scene(dialogPane),
													"Funcionário com a Maior Idade",
													this.mainStage);
			
			FuncionarioFormIdadeDialogController controller = fxmlLoader.getController();
			controller.setAppPrincipal(this);
			controller.setDialogStage(dialogStage);
			controller.setFuncionario(funcionario);
			
			// Exibe a nova janela
			dialogStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}