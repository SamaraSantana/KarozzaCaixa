package br.com.senai.model;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.modelo.ItemDoAluguel;
import br.com.senai.modelo.Locacao;

public class ItemDoAluguelTableModel extends AbstractTableModel {

	// lsita de clientes que 'alimentará
	// a tabela

	List<Locacao> itens;
	// vetor com as Strings relativas as
	// colunas da tabela
	final String[] colunas = { "Grupo", "Grupo (Diária)", "Proteção",
			"Proteção (Diária)", "Exemplar" };

	// "Opicional" "PREÇO Diária opcional", "Exemplar",

	public ItemDoAluguelTableModel(List<Locacao> lista) {
		// cria a lista interna através
		// da lista recebida no contrutor
		this.itens = lista;
	}

	@Override
	public int getRowCount() {
		// o número de linhas da tabela será
		// o número de registros na lista clientes
		return itens.size();
	}

	@Override
	public int getColumnCount() {
		// o numero de colunas será a
		// quantidade de Strings do vetor colunas
		return colunas.length;

	}

	@Override
	public Object getValueAt(int linha, int coluna) {

		Locacao item = itens.get(linha);
		switch (coluna) {
		case 0:
			return item.getGrupo().getNome();

		case 1:
			DecimalFormat format = new DecimalFormat("#0.00");

			return format.format(item.getGrupo().getPreco());

		case 2:

			return item.getProtecao().getDescricao();

		case 3:
			DecimalFormat format1 = new DecimalFormat("#0.00");

			return format1.format(item.getProtecao().getPreco());

		case 4:
			return item.getExemplar().getPlaca();

		default:

			throw new ArrayIndexOutOfBoundsException("Indice invalido");

		}

	}

	public void add(Locacao item) {
		this.itens.add(item);
		fireTableRowsInserted(0, itens.size());
	}

	public void remove(ItemDoAluguel item) {
		this.itens.remove(item);
		fireTableRowsDeleted(0, itens.size());
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return String.class;
		default:
			throw new ArrayIndexOutOfBoundsException("Indice invalido");
		}
	}

	@Override
	public String getColumnName(int coluna) {
		return colunas[coluna];
	}

	public Locacao getLocacao(int indice) {
		return itens.get(indice);
	}
}
