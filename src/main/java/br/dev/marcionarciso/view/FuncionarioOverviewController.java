package br.dev.marcionarciso.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

import br.dev.marcionarciso.Principal;
import br.dev.marcionarciso.model.Funcionario;
import br.dev.marcionarciso.utils.BigDecimalUtils;
import br.dev.marcionarciso.utils.DateUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FuncionarioOverviewController extends BaseDialogController{
	
	private static final Double VALOR_SALARIO_MINIMO = 1212D;
	
	/**
	 * O vínculo da TableView com sua respectiva ObservableList é feito pelo
	 * método setAppPrincipal.
	 * Os registros removidos da TableView também são removidos da ObservableList
	 * e vice-versa.
	 */
	@FXML
	private TableView<Funcionario> tabelaFuncionarios;
	@FXML
	private TableColumn<Funcionario, String> colunaNome;
	@FXML
	private TableColumn<Funcionario, String> colunaDataNascimento;
	@FXML
	private TableColumn<Funcionario, String> colunaSalario;
	@FXML
	private TableColumn<Funcionario, String> colunaFuncao;
	/**
	 * Coluna que exibirá a quantidade de salários mínimos que o funcionário recebe.
	 */
	@FXML
	private TableColumn<Funcionario, String> colunaSalariosMinimos;
	
	/**
	 * Label no rodapé da janela que exibe o valor da somatória dos salários dos
	 * funcionários.
	 */
	@FXML
	private Label labelValorTotalSalarios;
	
	
	/**
	 * Método executado após o carregamento do arquivo FXML.
	 */
	@FXML
	private void initialize() {
		initTabelaFuncionarios();
	}
	
	private void initTabelaFuncionarios() {
		/*
		 * Define qual propriedade de Funcionario está em qual coluna. 
		 */
		this.colunaNome.setCellValueFactory(cellData ->  new ReadOnlyStringWrapper(cellData.getValue().getNome()));
		this.colunaDataNascimento.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtils.formatarPadraoBrasileiro(cellData.getValue().getDataNascimento())));
		this.colunaSalario.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("R$ "+BigDecimalUtils.formatarEmMoeda(cellData.getValue().getSalario())));
		this.colunaFuncao.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFuncao()));
		this.colunaSalariosMinimos.setCellValueFactory(cellData -> {
			Double salario = cellData.getValue().getSalario().doubleValue();
			Double quantidadeSalariosMinimos = salario / VALOR_SALARIO_MINIMO;
			
			return new ReadOnlyStringWrapper(new DecimalFormat("0.0").format(quantidadeSalariosMinimos));
		});
	}
		
	public void setAppPrincipal(Principal principal) {
		this.appPrincipal = principal;
	}

	/*
	 * Adiciona os dados da ObservableList à tabela de funcionários
	 */
	public void setFuncionarios(ObservableList<Funcionario> funcionarios) {
		this.tabelaFuncionarios.setItems(funcionarios);
		this.updateValorTotalSalarios();
	}
	
	/**
	 * Atualiza a visualização da TableView de funcionários.
	 */
	public void refreshTabelaFuncionarios() {
		this.tabelaFuncionarios.refresh();
		this.updateValorTotalSalarios();
	}
	
	/**
	 * Atualiza o valor da label com o valor total do somatório dos salários.
	 */
	private void updateValorTotalSalarios() {
		BigDecimal valorTotalSalarios = this.tabelaFuncionarios.getItems()
									.stream()
									.map(funcionario -> funcionario.getSalario())
									.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		this.labelValorTotalSalarios.setText("R$ "+BigDecimalUtils.formatarEmMoeda(valorTotalSalarios));
	}
		
	/**
	 * Executado quando o usuário clicar no botão "Excluir".
	 */
	@FXML
	private void handleExclusaoFuncionario() {
		int selectedIndex = this.tabelaFuncionarios.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex >= 0) {
			// Remove o funcionário da ObservableList
			this.tabelaFuncionarios.getItems().remove(selectedIndex);
			this.refreshTabelaFuncionarios();
			
			return;
		}
		
		this.appPrincipal.showAlert("Atenção!", 
									"Por favor, selecione um funcionário.", 
									AlertType.WARNING);
	}
	
	/**
	 * Executado quando o usuário clicar no botão "Novo".
	 * Abre uma janela com o formulário para criar um novo funcionário.
	 */
	@FXML
	private void handleNovoFuncionario() {
		Funcionario f = new Funcionario();
		
		Boolean okClicado = this.appPrincipal.showFuncionarioFormDialog(f);
		
		if (okClicado) {
			// Adiciona o funcionário à ObservableList
			this.appPrincipal.getDadosFuncionarios().add(f);
			this.refreshTabelaFuncionarios();
		}
	}
	
	/**
	 * Executado quando o usuário clicar no botão "Editar".
	 * Abre uma janela com o formulário preenchido com os dados do funcionário
	 * para a edição.
	 */
	@FXML
	private void handleEditarFuncionario() {
		Funcionario funcionarioSelecionado = this.tabelaFuncionarios.getSelectionModel().getSelectedItem();
		
		if (Objects.isNull(funcionarioSelecionado)) {
			this.appPrincipal.showAlert("Atenção!", 
										"Por favor, selecione um funcionário.", 
										AlertType.WARNING);
			return;
		}
		
		Boolean okClicado = this.appPrincipal.showFuncionarioFormDialog(funcionarioSelecionado);
		
		if (okClicado) {
			// Atualiza a TableView de funcionários para exibir as alterações realizadas.
			this.refreshTabelaFuncionarios();
		}
	}
	
	/**
	 * Executado quando o botão "Remover João" é clicado.
	 * Remove o funcionário de nome "João" da lista de funcionários.
	 */
	@FXML
	private void handleExclusaoFuncionarioJoao() {
		Boolean isFuncionarioRemovido = this.appPrincipal.getFuncionarioService().removeFuncionarioByNome("João");
		
		if (! isFuncionarioRemovido) {
			this.appPrincipal.showAlert("Atenção!", 
					"Funcionário já removido ou inexistente.", 
					AlertType.WARNING);
		}
		
		this.refreshTabelaFuncionarios();
	}

	/**
	 * Executado quando o botão "Aumentar 10%" é clicado.
	 * Realiza o aumento de 10% no salário de todos funcionários.
	 */
	@FXML
	private void handleAumentarSalario10Porcento() {
		this.appPrincipal.getFuncionarioService().aumentarSalarioDeTodosEmPorcentagem(10D);
		this.refreshTabelaFuncionarios();
	}
	
	/**
	 * Executado quando o botão "Exibir por Função" é clicado.
	 * Abre uma janela exibindo uma lista de funcionário agrupados por funções.
	 */
	@FXML
	private void handleExibirFuncionariosPorFuncao() {
		this.appPrincipal.showFuncionariosListagemFuncaoDialog();
	}
	
	/**
	 * Executado quando o botão "Exibir Aniversariantes" é clicado.
	 * Abre uma janela exibindo uma lista de funcionários que fazem aniversário
	 * nos meses 10 e 12.
	 */
	@FXML
	private void handleExibirAniversariantes() {
		this.appPrincipal.showFuncionariosListagemAniversariantesDialog(10, 12);
	}
	
	/**
	 * Executado quando o botão "Exibir Maior Idade" é clicado.
	 * Abre uma janela com as informações "nome" e "idade" do funcionário de 
	 * maior idade.
	 */
	@FXML
	private void handleExibirFuncionarioComMaiorIdade() {
		Funcionario funcionario = this.appPrincipal.getFuncionarioService().getFuncionarioComMaiorIdade();
		this.appPrincipal.showFuncionarioFormIdadeDialog(funcionario);
	}
}
