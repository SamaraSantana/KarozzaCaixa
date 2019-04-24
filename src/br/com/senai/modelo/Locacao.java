package br.com.senai.modelo;

import java.util.ArrayList;
import java.util.List;

public class Locacao {
	private long id;
	private String local_Reserva;
	private String data_Retirada;
	private String hora_Retirada;
	private String data_Prevista;
	private String hora_Prevista;
	private Protecao protecao;
	private Grupo grupo;
	private Exemplar exemplar;
	private Opcional opcional[];
	private List<ItemDoAluguel> itens = new ArrayList<ItemDoAluguel>();
	private double total;
	private Cliente cliente;
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	public Opcional[] getOpcional() {
		return opcional;
	}

	public void setOpcional(Opcional[] opcional) {
		this.opcional = opcional;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocal_Reserva() {
		return local_Reserva;
	}

	public void setLocal_Reserva(String local_Reserva) {
		this.local_Reserva = local_Reserva;
	}

	public String getData_Retirada() {
		return data_Retirada;
	}

	public void setData_Retirada(String data_Retirada) {
		this.data_Retirada = data_Retirada;
	}

	public String getHora_Retirada() {
		return hora_Retirada;
	}

	public void setHora_Retirada(String hora_Retirada) {
		this.hora_Retirada = hora_Retirada;
	}

	public String getData_Prevista() {
		return data_Prevista;
	}

	public void setData_Prevista(String data_Prevista) {
		this.data_Prevista = data_Prevista;
	}

	public String getHora_Prevista() {
		return hora_Prevista;
	}

	public void setHora_Prevista(String hora_Prevista) {
		this.hora_Prevista = hora_Prevista;
	}

	public Protecao getProtecao() {
		return protecao;
	}

	public void setProtecao(Protecao protecao) {
		this.protecao = protecao;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public double getValorTotal() {
		return grupo.getPreco() + protecao.getPreco();
	}

	public List<ItemDoAluguel> getItens() {
		return itens;
	}

	public void setItens(List<ItemDoAluguel> itens) {
		this.itens = itens;
	}

	public double getTotal() {
		total = 0;
		for (ItemDoAluguel item : itens) {
			total += item.getValorTotal();
		}
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}

}

