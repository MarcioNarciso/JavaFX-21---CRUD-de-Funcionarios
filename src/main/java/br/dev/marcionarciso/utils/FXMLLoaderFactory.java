package br.dev.marcionarciso.utils;

import javafx.fxml.FXMLLoader;

public class FXMLLoaderFactory {
	
	private FXMLLoaderFactory() {}
	
	public static FXMLLoader create(String fxmlFilePath) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(FXMLLoaderFactory.class.getResource(fxmlFilePath));
		
		return fxmlLoader;
	}

}
