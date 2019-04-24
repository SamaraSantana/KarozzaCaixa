package br.com.senai.main;

import br.com.senai.tela.Login;
import br.com.senai.tela.TelaCaixa;


public class Main {
	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				System.out.println(info);
				if ("Nimbus".equals(info.getName())) {

					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			
				new Login(null, false);
				//new TelaCaixa();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
