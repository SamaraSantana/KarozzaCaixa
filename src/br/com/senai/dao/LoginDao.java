package br.com.senai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import br.com.senai.modelo.Funcionario;
import br.com.senai.tela.TelaCaixa;

public class LoginDao {
	public static Funcionario getCheca(Funcionario f) throws SQLException {
		Connection con = ConnectionFactory.getConnection();

		PreparedStatement sql = con
				.prepareStatement("select * from funcionario where login=? and senha=? and tipo=?");
		sql.setString(1, f.getLogin());
		sql.setString(2, f.getSenha());
		sql.setString(3, f.getTipo());
		ResultSet resultado = sql.executeQuery();
		if (resultado.next()) {
			new TelaCaixa().show();

		} else {
			JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!");
		}
		resultado.close();
		con.close();
		return f;
	}
}
