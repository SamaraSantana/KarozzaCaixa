package br.com.senai.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.modelo.ItemDoAluguel;
import br.com.senai.modelo.Opcional;

public class ItemOpcionalTableModel extends AbstractTableModel {

	List<Opcional> itens;
final String[] colunas = { "Opcional", "Preço Diária" };

	public ItemOpcionalTableModel(List<Opcional> lista) {
		this.itens = lista;
	}

	@Override
	public int getRowCount() {
		return itens.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public Object getValueAt(int linha, int coluna) {

		Opcional item = itens.get(linha);
		switch (coluna) {
		case 0:
			return item.getNome();

		case 1:
			return item.getPreco();
			
		default:

			throw new ArrayIndexOutOfBoundsException("Indice invalido");

		}

	}

	public void add(Opcional item) {
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
		default:
			throw new ArrayIndexOutOfBoundsException("Indice invalido");
		}
	}

	@Override
	public String getColumnName(int coluna) {
		return colunas[coluna];
	}

	public Opcional getOpcional(int indice) {
		return itens.get(indice);
	}

}
