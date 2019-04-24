package br.com.senai.modelo;

public class Caixa {
	private long id;
	private String dataAbertura;
	private String dataFechamento;
	private String Funcionario;


	
	
	public String getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(String dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public String getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(String dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public String getFuncionario() {
		return Funcionario;
	}

	public void setFuncionario(String funcionario) {
		Funcionario = funcionario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		String retorno = String.format("%-30s| %11s|%10s", Funcionario);
		return retorno;
	}
}
