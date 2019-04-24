package br.com.senai.modelo;


public class Exemplar {
	private long id;
	private String placa;
	private String renavam;
	private String chassi;
	private String odometro;
	private String cor;
	private String status;
	private byte[] foto;
	private String loja;
	private String automovel;

	public String getLoja() {
		return loja;
	}

	public void setLoja(String loja) {
		this.loja = loja;
	}

	public String getAutomovel() {
		return automovel;
	}

	public void setAutomovel(String automovel) {
		this.automovel = automovel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getRenavam() {
		return renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getOdometro() {
		return odometro;
	}

	public void setOdometro(String odometro) {
		this.odometro = odometro;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	
}
