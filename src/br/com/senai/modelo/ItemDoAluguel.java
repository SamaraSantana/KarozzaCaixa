package br.com.senai.modelo;

public class ItemDoAluguel {
	private long id;
	private Grupo grupo;
	private Protecao protecao;
	private Opcional opcional;
	private Exemplar exemplar;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Protecao getProtecao() {
		return protecao;
	}

	public void setProtecao(Protecao protecao) {
		this.protecao = protecao;
	}

	public Opcional getOpcional() {
		return opcional;
	}

	public void setOpcional(Opcional opcional) {
		this.opcional = opcional;
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	public double getValorTotal() {
		return grupo.getPreco() + protecao.getPreco() + opcional.getPreco();
	}

}
