package br.com.senai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.modelo.Loja;

public class LojaDao {
	private Connection conexao;

	// metodo selecionar
	public List<Loja> ListaLoja() throws SQLException {
		Connection con = ConnectionFactory.getConnection();
		List<Loja> lojas = new ArrayList<Loja>();

		PreparedStatement sql = con.prepareStatement("select * from loja");
		ResultSet rs = sql.executeQuery();

		while (rs.next()) {
			Loja loja = new Loja();
			loja.setId((int) rs.getLong("id"));
			loja.setCnpj(rs.getString("cnpj"));
			loja.setTelefone(rs.getString("telefone"));
			loja.setRua(rs.getString("rua"));
			loja.setNumRua(rs.getString("numRua"));
			loja.setComplemento(rs.getString("complemento"));
			loja.setBairro(rs.getString("bairro"));
			loja.setCidade(rs.getString("cidade"));
			loja.setUf(rs.getString("uf"));
			loja.setCep(rs.getString("cep"));
			loja.setTipo(rs.getString("tipo"));
			lojas.add(loja);

		}
		rs.close();
		sql.close();
		return lojas;

	}

	// metodo buscar id
	public Loja buscaId(long id) {

		Loja l = null;
		String sql = "SELECT * FROM loja WHERE id =?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Loja();
				l.setId(rs.getLong("id"));
				l.setCnpj(rs.getString("cnpj"));
				l.setTelefone(rs.getString("telefone"));
				l.setRua(rs.getString("rua"));
				l.setNumRua(rs.getString("numRua"));
				l.setComplemento(rs.getString("complemento"));
				l.setBairro(rs.getString("bairro"));
				l.setCidade(rs.getString("cidade"));
				l.setUf(rs.getString("uf"));
				l.setCep(rs.getString("cep"));
				l.setTipo(rs.getString("tipo"));
			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return l;
	}

	// metodo buscar id
	public Loja buscar(String bairro) {
		System.out.println("passou aqui");
		System.out.println(bairro);
		Loja l = null;
		String sql = "SELECT * FROM loja WHERE (bairro =?)";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, bairro);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Loja();
				l.setId(Integer.parseInt(rs.getString("id")));
				l.setBairro(rs.getString("bairro"));

			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return l;
	}

	public Loja buscaMatriz(long id) {

		Loja l = null;
		String sql = "SELECT * FROM loja WHERE id = ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, 1);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Loja();
				l.setId(rs.getLong("id"));
				l.setCnpj(rs.getString("cnpj"));
				l.setTelefone(rs.getString("telefone"));
				l.setRua(rs.getString("rua"));
				l.setNumRua(rs.getString("numRua"));
				l.setComplemento(rs.getString("complemento"));
				l.setBairro(rs.getString("bairro"));
				l.setCidade(rs.getString("cidade"));
				l.setUf(rs.getString("uf"));
				l.setCep(rs.getString("cep"));
				l.setTipo(rs.getString("tipo"));
			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return l;
	}
}
