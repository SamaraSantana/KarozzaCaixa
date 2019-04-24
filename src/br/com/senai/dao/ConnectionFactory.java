package br.com.senai.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// DriverManager.getConnection("jdbc:mysql://localhost/senai","root","root");
			return DriverManager.getConnection("jdbc:mysql://10.84.150.15/Karozza04", "karozza", "karozza");

		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
