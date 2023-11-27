package br.dev.marcionarciso.model;

import java.time.LocalDate;

public abstract class Pessoa {

	private String nome;
	private LocalDate dataNascimento;
	
	public Pessoa() {}
	
	public Pessoa(String nome) {
		super();
		this.nome = nome;
	}
	
	public Pessoa(String nome, LocalDate dataNascimento) {
		super();
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
}
