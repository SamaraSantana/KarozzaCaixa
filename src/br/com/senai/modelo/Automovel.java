package br.com.senai.modelo;

public class Automovel {
	private long id;
	private String marca;
	private String modelo;
	private String ano;
	private String motor;
	private String transmissao;
	private String bombaCombustivel;
	private String Grupo;

	public String getGrupo() {
		return Grupo;
	}

	public void setGrupo(String grupo) {
		Grupo = grupo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public String getTransmissao() {
		return transmissao;
	}

	public void setTransmissao(String transmissao) {
		this.transmissao = transmissao;
	}

	public String getBombaCombustivel() {
		return bombaCombustivel;
	}

	public void setBombaCombustivel(String bombaCombustivel) {
		this.bombaCombustivel = bombaCombustivel;
	}
}
