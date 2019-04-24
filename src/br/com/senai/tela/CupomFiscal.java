package br.com.senai.tela;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CupomFiscal extends JFrame {
	JLabel lbBorda, lbBorda2, lbBorda3, lbSlogan, lbTel, lbLoja, lbPedido, lbData, lbTotal,
			lbCliente, lbGrupo, lbProtecao, lbOpcional, lbCod, lbTotalLocacao, lbCodLocacao;
	JPanel pnCentral;

	public CupomFiscal() {
		inicializarComponentes();
		definirEventos();
	}

	private void inicializarComponentes() {

		// pnCentral
		pnCentral = new JPanel();
		pnCentral.setBackground(new Color(255,255,224));
		pnCentral.setLayout(null);

		// slogan
		lbSlogan = new JLabel("Karozza rent a car");
		lbSlogan.setBounds(25, 5, 300, 25);
		lbSlogan.setHorizontalAlignment(SwingConstants.CENTER);

		// tel
		lbTel = new JLabel("Fone  :  2556-1234 / 1239-7069");
		lbTel.setBounds(30, 30, 300, 30);
		// lbTel.setHorizontalAlignment(SwingConstants.CENTER);

		lbLoja = new JLabel("Loja");
		lbLoja.setBounds(30, 50, 300, 30);
		// lbLoja.setHorizontalAlignment(SwingConstants.CENTER);

		lbData = new JLabel("Data");
		lbData.setBounds(30, 70, 300, 30);

		lbPedido = new JLabel("Pedido");
		lbPedido.setBounds(30, 90, 300, 30);

		lbCliente = new JLabel("Cliente: Consumidor");
		lbCliente.setBounds(30, 110, 300, 30);

		// borda
		lbBorda = new JLabel("=========================================");
		lbBorda.setBounds(30, 130, 340, 30);
		
		lbCod = new JLabel("Cód Locação");
		lbCod.setBounds(40, 150, 100, 30);
		
		lbTotal = new JLabel("Total (R$)");
		lbTotal.setBounds(250, 150, 300, 30);
		
		lbBorda2 = new JLabel("=========================================");
		lbBorda2.setBounds(30, 170, 340, 30);
		
		lbCodLocacao = new JLabel("");
		lbCodLocacao.setBounds(40, 190, 100, 30);
		
		lbTotalLocacao = new JLabel("");
		lbTotalLocacao.setBounds(250, 190, 300, 30);
		
		lbBorda3 = new JLabel("=========================================");
		lbBorda3.setBounds(30, 210, 340, 30);
		
		pnCentral.add(lbSlogan);
		pnCentral.add(lbTel);
		pnCentral.add(lbLoja);
		pnCentral.add(lbData);
		pnCentral.add(lbPedido);
		pnCentral.add(lbCliente);
		pnCentral.add(lbBorda);
		pnCentral.add(lbCod);
		pnCentral.add(lbTotal);
		pnCentral.add(lbBorda2);
		pnCentral.add(lbCodLocacao);
		pnCentral.add(lbTotalLocacao);
		pnCentral.add(lbBorda3);

		add(pnCentral, BorderLayout.CENTER);
		// definiçoes do formulário
		setSize(350, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Nota fiscal");
		getContentPane().setBackground(Color.yellow);

		setIconImage(new ImageIcon(getClass().getResource(
				"/imagens/menor_logo.png")).getImage());
		setVisible(true);
	}

	private void definirEventos() {

	}

}
