package br.com.senai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.senai.modelo.Caixa;

public class CaixaDao {

	public void adicionaDataAbertura(Caixa caixas) throws SQLException {
		Connection con = ConnectionFactory.getConnection();

		String sql = "INSERT INTO caixa (dataAbertura, dataFechamento, funcionario_id) values(?,?,?)";

		try {
			PreparedStatement start = con.prepareStatement(sql);
			start.setString(1, caixas.getDataAbertura());
			start.setString(2, caixas.getDataFechamento());
			start.setString(3, caixas.getFuncionario());

			start.execute();
			start.close();
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adicionaDataFechamento(Caixa caixas) throws SQLException {
		Connection con = ConnectionFactory.getConnection();

		String sql = "update caixa set dataFechamento = ? where id = ?";

		try {
			PreparedStatement start = con.prepareStatement(sql);
			start.setString(1, caixas.getDataFechamento());
			start.setLong(2, caixas.getId());

			start.execute();
			start.close();
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int buscaId() throws SQLException {
		Connection con = ConnectionFactory.getConnection();

		String sql = "SELECT MAX(ID) as id FROM caixa";

		PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int ultimoId = rs.getInt("id");

		rs.close();
		stmt.close();

		return ultimoId;
	}

	public int buscaCaixa() throws SQLException {
		Connection con = ConnectionFactory.getConnection();

		String sql = "SELECT MAX(ID) as id FROM caixa";
		PreparedStatement stmt = (PreparedStatement) con
				.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int ultimoId = rs.getInt("id");

		rs.close();
		stmt.close();

		return ultimoId;
	}
}
