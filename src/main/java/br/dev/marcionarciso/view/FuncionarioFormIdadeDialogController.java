package br.dev.marcionarciso.view;

import java.time.LocalDate;
import java.util.Objects;

import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.utils.DateUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FuncionarioFormIdadeDialogController extends BaseDialogController{

	/**
	 * Funcionário que será exibido no formulário.
	 */
	private Funcionario funcionario;
	
	@FXML
	private TextField campoNome;
	@FXML
	private TextField campoIdade;

	/**
	 * Recebe o funcionário com a maior idade.
	 * @param funcionario
	 */
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
		
		if (Objects.isNull(funcionario)) {
			return;
		}
		
		this.campoNome.setText(funcionario.getNome());
		
		Long idadeFuncionario = DateUtils.calcularDiferencaEmAnos(funcionario.getDataNascimento(), LocalDate.now());
		
		this.campoIdade.setText(String.valueOf(idadeFuncionario));
	}
	

}
