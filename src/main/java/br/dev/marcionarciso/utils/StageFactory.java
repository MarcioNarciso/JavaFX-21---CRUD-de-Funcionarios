package br.dev.marcionarciso.utils;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StageFactory {
	
	private StageFactory() {}
	
	public static Stage create(Scene scene, String title, Stage owner) {
		return StageFactory.create(scene, title, owner, Modality.WINDOW_MODAL);
	}

	public static Stage	create(Scene scene, String title, Stage owner, Modality modality ) {
		// Cria a nova janela
		Stage stage = new Stage();
		// Atribui uma nova cena com o conteúdo que deve ser exibido na nova janela
		stage.setScene(scene);
		// Define o título da janela
		stage.setTitle(title);
		// Define a modalidade da janela, por exemplo WINDOW_MODAL
		stage.initModality(modality);
		// Define uma janela como "dona" da nova janela
		stage.initOwner(owner);
		
		return stage;
	}
	
}
