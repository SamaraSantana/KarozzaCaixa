package br.com.senai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.senai.modelo.Cliente;
import br.com.senai.modelo.Exemplar;
import br.com.senai.modelo.Grupo;
import br.com.senai.modelo.Locacao;
import br.com.senai.modelo.Opcional;
import br.com.senai.modelo.Protecao;

public class LocacaoDao {
	int d;

	public boolean verificaDevolucao(String d) throws SQLException {
		Connection con = ConnectionFactory.getConnection();

		try {
			PreparedStatement start = con
					.prepareStatement("select * from devolucao where locacao_id = ?");
			start.setString(1, d);
			ResultSet rs = start.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public int buscaDevolucao(String id) throws SQLException {
		Connection con = ConnectionFactory.getConnection();

		String sql = "select l.id,l.data_Prevista, l.hora_Prevista, d.id as idd, d.data_Devolucao,d.hora_Devolucao from "
				+ "devolucao as d, locacao as l where d.Locacao_id = l.id and l.id = ?";

		PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			d = (rs.getInt("idd"));

			rs.close();
			stmt.close();
		}
		return d;
	}

	public void adicionaPagamento(int id, int devolucao) throws SQLException {
		Connection con = ConnectionFactory.getConnection();

		String sql = "INSERT INTO pagamento (caixa_id, devolucao_id) values(?,?)";
		try {
			PreparedStatement start = con.prepareStatement(sql);
			start.setLong(1, id);
			start.setLong(2, devolucao);

			start.execute();
			start.close();
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Opcional> listarOpcional() {
		Connection con = ConnectionFactory.getConnection();
		try {
			List<Opcional> opcionais = new ArrayList<Opcional>();
			PreparedStatement stmt = con
					.prepareStatement("Select * from opcional");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Opcional opcional = new Opcional();
				opcional.setId((int) rs.getLong("id"));
				opcional.setNome(rs.getString("nome"));
				opcional.setPreco(rs.getDouble("preco"));

				opcionais.add(opcional);

			}
			rs.close();
			stmt.close();
			return opcionais;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Grupo> listarGrupo() {
		Connection con = ConnectionFactory.getConnection();
		try {
			List<Grupo> lista = new ArrayList<Grupo>();
			PreparedStatement stmt = con
					.prepareStatement("select * from grupo");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Grupo g = new Grupo();
				g.setId(rs.getInt("id"));
				g.setNome(rs.getString("nome"));
				g.setDescricao(rs.getString("descricao"));
				g.setPreco(rs.getDouble("preco"));
				g.setFoto(rs.getBytes("foto"));
				g.setLoja(rs.getString("loja_id"));

				lista.add(g);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Locacao buscaId(long id) {
		Connection con = ConnectionFactory.getConnection();

		Locacao l = null;
		String sql = "select * from locacao as lc, protecao as p, grupo as g, loja as l, cliente as c, exemplar as e where "
				+ "lc.Protecao_id = p.id and lc.Grupo_id = g.id and "
				+ "lc.local_reserva = l.id and lc.cliente_id = c.id and lc.exemplar_id = e.id and lc.id = ?";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Locacao();
				l.setId(rs.getLong("lc.id"));
				l.setLocal_Reserva(rs.getString("l.bairro"));

				Date g = rs.getDate("lc.data_Retirada");
				SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
				l.setData_Retirada(formata.format(g));

				Time t = rs.getTime("lc.hora_Retirada");
				SimpleDateFormat formata3 = new SimpleDateFormat("HH:mm");
				l.setHora_Retirada(formata3.format(t));

				Date g2 = rs.getDate("lc.data_Prevista");
				SimpleDateFormat formata2 = new SimpleDateFormat("dd/MM/yyyy");
				l.setData_Prevista(formata2.format(g2));
				
				
				Time t2 = rs.getTime("lc.hora_Retirada");
				SimpleDateFormat formata4 = new SimpleDateFormat("HH:mm");
				
				l.setHora_Prevista(formata4.format(t2));
				
				Grupo grupo = new Grupo();
				grupo.setId(rs.getLong("g.id"));
				grupo.setNome(rs.getString("g.nome"));
				grupo.setDescricao(rs.getString("g.descricao"));
				grupo.setPreco(rs.getDouble("g.preco"));	
				l.setGrupo(grupo);
				
				Protecao protecao = new Protecao();
				protecao.setId(rs.getLong("p.id"));
				protecao.setDescricao(rs.getString("p.descricao"));
				protecao.setPreco(rs.getDouble("p.preco"));
				l.setProtecao(protecao);
				
				Exemplar exemplar = new Exemplar();
				exemplar.setId(rs.getLong("e.id"));
				exemplar.setPlaca(rs.getString("e.placa"));
				l.setExemplar(exemplar);
				
				Cliente cliente = new Cliente();
				cliente.setId(rs.getLong("c.id"));
				cliente.setNome(rs.getString("c.nome"));
				cliente.setCpf(rs.getString("c.cpf"));
				cliente.setEmail(rs.getString("c.email"));
				cliente.setRg(rs.getString("c.rg"));
				cliente.setTelefone(rs.getString("c.telefone"));
				l.setCliente(cliente);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return l;
	}

	// metodo buscar id
	public List<Locacao> buscar(String id) throws SQLException {
		Connection con = ConnectionFactory.getConnection();

		Locacao l = null;
		List<Locacao> locacoes = new ArrayList<Locacao>();

		String sql = "select e.placa as exemplarPlaca, lc.id, l.bairro, lc.data_Retirada, lc.hora_Retirada, lc.data_Prevista, lc.hora_Prevista, p.descricao as protecaoDescricao, p.preco as protecaoPreco,  g.nome as grupoNome, g.descricao as grupoDescricao, g.preco as grupoPreco, g.foto as grupoFoto  from "
				+ "exemplar as e, loja as l, locacao as lc, protecao as p, grupo as g  where lc.Protecao_id = p.id and lc.Grupo_id = g.id and lc.local_Reserva = l.id and e.id = lc.Exemplar_id and lc.id =?";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Locacao();
				l.setId(rs.getLong("lc.id"));
				l.setGrupo(new Grupo());
				l.getGrupo().setNome(rs.getString("grupoNome"));
				l.getGrupo().setPreco(rs.getDouble("grupoPreco"));

				l.setProtecao(new Protecao());
				l.getProtecao().setDescricao(rs.getString("protecaoDescricao"));
				l.getProtecao().setPreco(rs.getDouble("protecaoPreco"));

				l.setExemplar(new Exemplar());
				l.getExemplar().setPlaca(rs.getString("exemplarPlaca"));

				locacoes.add(l);
			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return locacoes;
	}

	public List<Opcional> buscarOpcionais(String id) throws SQLException {
		Connection con = ConnectionFactory.getConnection();

		Opcional o = null;
		List<Opcional> opcionais = new ArrayList<Opcional>();
		try {
			String sql = "select o.id, o.nome, o.preco from locacao as l, opcional as o, itemopcional as io where l.id = io.Locacao_id and o.id = io.Opcional_id and l.id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				o = new Opcional();
				o.setId((int) rs.getLong("o.id"));
				o.setNome(rs.getString("o.nome"));
				o.setPreco(rs.getDouble("o.preco"));

				opcionais.add(o);
			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return opcionais;
	}

	public List<Locacao> listar() {
		Connection con = ConnectionFactory.getConnection();

		try {
			List<Locacao> locacoes = new ArrayList<Locacao>();
			PreparedStatement stmt = con
					.prepareStatement("select e.placa as exemplarPlaca, l.bairro, lc.data_Retirada, lc.hora_Retirada, lc.data_Prevista, lc.hora_Prevista, p.descricao as protecaoDescricao, p.preco as protecaoPreco,  g.nome as grupoNome, g.descricao as grupoDescricao, g.preco as grupoPreco, g.foto as grupoFoto  from "
							+ "exemplar as e, loja as l, locacao as lc, protecao as p, grupo as g  where lc.Protecao_id = p.id and lc.Grupo_id = g.id and lc.local_Reserva = l.id and e.id = lc.Exemplar_id");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Locacao locacao = new Locacao();

				locacao.setGrupo(new Grupo());
				locacao.getGrupo().setNome(rs.getString("grupoNome"));
				locacao.getGrupo().setPreco(rs.getDouble("grupoPreco"));

				locacao.setProtecao(new Protecao());
				locacao.getProtecao().setDescricao(
						rs.getString("protecaoDescricao"));
				locacao.getProtecao().setPreco(rs.getDouble("protecaoPreco"));

				locacao.setExemplar(new Exemplar());
				locacao.getExemplar().setPlaca(rs.getString("exemplarPlaca"));

				locacoes.add(locacao);
			}
			rs.close();
			stmt.close();
			return locacoes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public double somaItens(String id) {
		try {
			Connection con = ConnectionFactory.getConnection();
			List<Double> n = new ArrayList<Double>();
			String sql = "select o.id, o.nome, o.preco from locacao as l, opcional as o, itemopcional as io where l.id = io.Locacao_id and o.id = io.Opcional_id and l.id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				n.add(rs.getDouble("o.preco"));
			}
			double total = 0;
			for (double k : n)
				total = total + k;

			Connection con2 = ConnectionFactory.getConnection();
			String sql2 = "select g.preco from grupo as g, locacao as l where l.grupo_id = g.id and l.id = ?";
			PreparedStatement stmt2 = con2.prepareStatement(sql2);
			stmt2.setString(1, id);
			ResultSet rs2 = stmt2.executeQuery();
			if (rs2.next()) {
				total += rs2.getDouble("g.preco");
			}

			Connection con3 = ConnectionFactory.getConnection();
			String sql3 = "select p.preco from protecao as p, locacao as l where l.Protecao_id = p.id and l.id = ?";
			PreparedStatement stmt3 = con3.prepareStatement(sql3);
			stmt3.setString(1, id);
			ResultSet rs3 = stmt3.executeQuery();
			if (rs3.next()) {
				total += rs3.getDouble("p.preco");
			}

			rs.close();
			stmt.close();
			rs2.close();
			stmt2.close();
			rs3.close();
			stmt3.close();
			return total;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int diasLocacao(String id) throws SQLException {
		Connection con = ConnectionFactory.getConnection();
		int day = 0, day2 = 0, dayFinal = 0;
		int month = 0, month2 = 0;
		String sql = "select data_Retirada, data_Prevista from locacao where id = ?";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				Calendar c = Calendar.getInstance();
				Date g2 = rs.getDate("data_Prevista");
				c.setTime(g2);
				day = c.get(Calendar.DAY_OF_MONTH);
				month = c.get(Calendar.MONTH);

				Calendar c2 = Calendar.getInstance();
				Date g = rs.getDate("data_Retirada");
				c2.setTime(g);
				day2 = c2.get(Calendar.DAY_OF_MONTH);
				month2 = c2.get(Calendar.MONTH);

				if (month > month2) {
					dayFinal = day - day2 + 31;
				} else {
					dayFinal = day - day2;
				}
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return dayFinal;
	}

	public int diasLocacaoFinal(String id) throws SQLException {
		Connection con = ConnectionFactory.getConnection();
		int day = 0, day3 = 0;
		int dayFinal = 0;
		int month = 0, month2 = 0;
		String sql = "select l.id,l.data_Prevista, l.hora_Prevista, d.data_Devolucao,d.hora_Devolucao from devolucao as d, locacao as l where d.Locacao_id = l.id and l.id = ?";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				Calendar c = Calendar.getInstance();
				Date g2 = rs.getDate("l.data_Prevista");
				c.setTime(g2);
				day = c.get(Calendar.DAY_OF_MONTH);
				month = c.get(Calendar.MONTH);

				Calendar c3 = Calendar.getInstance();
				Date g3 = rs.getDate("d.data_Devolucao");
				c3.setTime(g3);
				day3 = c3.get(Calendar.DAY_OF_MONTH);
				month2 = c3.get(Calendar.MONTH);

				if (month < month2) {
					dayFinal = day3 - day + 31;
				} else {
					dayFinal = day3 - day;
				}
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return dayFinal;
	}
}
