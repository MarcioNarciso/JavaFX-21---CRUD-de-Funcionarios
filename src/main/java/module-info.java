/*
 * É necessário exportar todos os pacotes.
 */
module br.dev.marcionarciso {
    requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
    exports br.dev.marcionarciso;
    exports br.dev.marcionarciso.model;
    exports br.dev.marcionarciso.service;
    opens br.dev.marcionarciso.view;
}