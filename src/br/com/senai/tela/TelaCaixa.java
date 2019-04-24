package br.com.senai.tela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.senai.dao.CaixaDao;
import br.com.senai.dao.ConnectionFactory;
import br.com.senai.dao.LocacaoDao;
import br.com.senai.dao.LojaDao;
import br.com.senai.model.ItemDoAluguelTableModel;
import br.com.senai.model.ItemOpcionalTableModel;
import br.com.senai.modelo.Caixa;
import br.com.senai.modelo.FormaDePagamento;
import br.com.senai.modelo.Funcionario;
import br.com.senai.modelo.Locacao;
import br.com.senai.modelo.Opcional;
import br.com.senai.util.GerarPDF;
import br.com.senai.util.Log;

public class TelaCaixa extends JFrame implements ActionListener {
	JPanel pnCentral, pnBotao, pnLabel, pnBotao2;
	JTextField tfCodAluguel, tfFuncionario, tfData;
	JLabel imagem, lbCodAluguel, lbFuncionario, lbPagamento, lbLoja, lbTotal,
			lbDias;
	JButton btConfirmar, btBuscar, btCancelar, btFecharCaixa, btFechar;
	Font fontePadrao, fontePadrao2, fontePadrao3;
	JComboBox<FormaDePagamento> cbFormaDePagemento;
	JTable tbItens = new JTable(), tbItensOpcionais = new JTable();
	JScrollPane spItens = new JScrollPane(tbItens);
	JScrollPane spItens2 = new JScrollPane(tbItensOpcionais);
	public static Funcionario f2 = new Funcionario();
	public static Locacao l;
	public static JTextField tfTotal, tfDias, tfLoja;
	LojaDao daoLoja = new LojaDao();
	LocacaoDao dao = new LocacaoDao();
	Caixa caixa;
	Locacao item = new Locacao();
	Locacao locacao;

	public TelaCaixa() throws SQLException {
		locacao = new Locacao();
		inicializarComponentes();
		definirEventos();

		class Relogio extends TimerTask {
			JTextField textField;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			public Relogio(JTextField textField) {
				this.textField = textField;
			}

			public void run() {
				textField.setText(sdf.format(new Date()));
			}
		}
		Timer t = new Timer();
		t.scheduleAtFixedRate(new Relogio(tfData), 0, 1000);

		setModal(true);
		setVisible(true);
	}

	private void criarTabela(List<Locacao> lista) {

		ItemDoAluguelTableModel model = new ItemDoAluguelTableModel(lista);
		tbItens.setModel(model);

		tbItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tbItens.getTableHeader().setReorderingAllowed(false);

		tbItens.getTableHeader().setResizingAllowed(false);
		// redimensiona a coluna para 100px
		tbItens.getColumnModel().getColumn(0).setPreferredWidth(100);
		tbItens.getColumnModel().getColumn(1).setPreferredWidth(120);
		tbItens.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbItens.getColumnModel().getColumn(3).setPreferredWidth(120);
		tbItens.getColumnModel().getColumn(4).setPreferredWidth(100);

		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		tbItens.getColumnModel().getColumn(0).setCellRenderer(render);
		tbItens.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	private void criarTabela2(List<Opcional> lista) {
		ItemOpcionalTableModel model = new ItemOpcionalTableModel(lista);
		tbItensOpcionais.setModel(model);

		tbItensOpcionais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tbItensOpcionais.getTableHeader().setReorderingAllowed(false);

		tbItensOpcionais.getTableHeader().setResizingAllowed(false);

		tbItensOpcionais.getColumnModel().getColumn(0).setPreferredWidth(111);
		tbItensOpcionais.getColumnModel().getColumn(1).setPreferredWidth(110);

		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		tbItensOpcionais.getColumnModel().getColumn(0).setCellRenderer(render);

		tbItensOpcionais.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbItensOpcionais.setBackground(Color.white);
	}

	public TelaCaixa(Locacao item) throws SQLException {
		inicializarComponentes();
		definirEventos();
		this.item = item;
		setModal(true);
		setVisible(true);
		
		f2.setLoja(tfLoja.getText());
	}

	private void setModal(boolean b) {
	}

	private void inicializarComponentes() throws SQLException {
		// fonte padrao
		fontePadrao = new Font("Georgia, Times New Roman, serif", Font.PLAIN,
				16);
		// fonte padrao2
		fontePadrao2 = new Font("Georgia, Times New Roman, serif", Font.BOLD,
				14);
		// fonte padrao3
		fontePadrao3 = new Font("Georgia, Times New Roman, serif", Font.BOLD,
				24);
		// pnCentral
		pnCentral = new JPanel();
		pnCentral.setBackground(new Color(176, 196, 222));
		pnCentral.setLayout(null);

		imagem = new JLabel();
		imagem.setBounds(40, 10, 320, 140);
		imagem.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/menor_logo2.png")));

		// Codigo aluguel
		lbCodAluguel = new JLabel("Cód.Aluguel");
		lbCodAluguel.setBounds(320, 5, 100, 30);
		lbCodAluguel.setFont(fontePadrao);

		// Text Field codAluguel(tfCodAluguel)
		tfCodAluguel = new JTextField();
		tfCodAluguel.setBackground(Color.white);
		tfCodAluguel.setBounds(320, 30, 240, 30);
		tfCodAluguel.setFont(fontePadrao3);
		// tfCodAluguel.setBackground(Color.BLUE);

		// btBuscar
		btBuscar = new JButton();
		btBuscar.setBounds(560, 30, 80, 30);
		btBuscar.setFont(fontePadrao);
		btBuscar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/busca.png")));

		// Funcionario
		lbFuncionario = new JLabel("Operador");
		lbFuncionario.setBounds(670, 5, 100, 30);
		lbFuncionario.setFont(fontePadrao);

		// tfFuncionario
		tfFuncionario = new JTextField();
		tfFuncionario.setBackground(Color.white);
		tfFuncionario.setBounds(670, 30, 240, 30);
		tfFuncionario.setFont(fontePadrao2);
		tfFuncionario.setEditable(false);

		// lbFormaPagamento
		lbPagamento = new JLabel("Forma de pagamento");
		lbPagamento.setBounds(320, 60, 190, 30);
		lbPagamento.setFont(fontePadrao);
		// cb forma de pagamento
		cbFormaDePagemento = new JComboBox<FormaDePagamento>(
				FormaDePagamento.values());
		cbFormaDePagemento.setBounds(320, 90, 320, 30);
		cbFormaDePagemento.setFont(fontePadrao2);
		cbFormaDePagemento.setSelectedIndex(-1);

		// text field (tfData)

		tfData = new JTextField();
		tfData.setBounds(320, 520, 150, 30);
		tfData.setFont(fontePadrao2);
		tfData.setHorizontalAlignment(SwingConstants.CENTER);
		tfData.setEditable(false);
		tfData.setBackground(Color.white);

		// lbLoja
		lbLoja = new JLabel("Loja");
		lbLoja.setBounds(670, 60, 100, 30);
		lbLoja.setFont(fontePadrao);
		// lbLoja.setForeground(Color.WHITE);

		// tfLoja
		tfLoja = new JTextField();
		tfLoja.setBounds(670, 90, 240, 30);
		tfLoja.setFont(fontePadrao2);
		tfLoja.setEditable(false);

		// PREÇO
		lbTotal = new JLabel("Total:   R$");
		lbTotal.setBounds(980, 5, 100, 30);
		lbTotal.setFont(fontePadrao);
		// lbTotal.setForeground(Color.WHITE);

		tfTotal = new JTextField();
		tfTotal.setBounds(980, 30, 100, 30);
		tfTotal.setFont(fontePadrao3);
		tfTotal.setHorizontalAlignment(SwingConstants.CENTER);
		tfTotal.setEditable(false);
		tfTotal.setForeground(Color.RED);
		tfTotal.setBackground(Color.white);

		// lbLoja
		lbDias = new JLabel("Qtd.Dias");
		lbDias.setBounds(980, 60, 180, 30);
		lbDias.setFont(fontePadrao);
		// lbLoja.setForeground(Color.WHITE);

		tfDias = new JTextField();
		tfDias.setBounds(980, 90, 100, 30);
		tfDias.setFont(fontePadrao3);
		tfDias.setHorizontalAlignment(SwingConstants.CENTER);
		tfDias.setEditable(false);
		tfDias.setBackground(Color.white);
		
		
		spItens = new JScrollPane(tbItens);
		spItens.setBackground(Color.white);
		spItens.setBounds(320, 150, 540, 330);
		spItens.setBackground(Color.white);

		spItens2 = new JScrollPane(tbItensOpcionais);
		spItens2.setBackground(Color.white);
		spItens2.setBounds(850, 150, 230, 330);

		// btEntrar
		btConfirmar = new JButton("Confirmar");
		btConfirmar.setFont(fontePadrao);
		btConfirmar.setBackground(Color.white);
		btConfirmar.setMnemonic(KeyEvent.VK_I);

		// btEntrar
		btCancelar = new JButton("Cancelar");
		btCancelar.setFont(fontePadrao);
		btCancelar.setBackground(Color.white);
		btCancelar.setMnemonic(KeyEvent.VK_E);

		// btFecharcaixa
		if (btFecharCaixa == null) {
			btFecharCaixa = new JButton("Fechar caixa");
			btFecharCaixa.setFont(fontePadrao);

			btFecharCaixa
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

						
						}
					});
			btFecharCaixa.setBackground(Color.white);
			btFecharCaixa.setMnemonic(KeyEvent.VK_E);
		}

		/*
		 * btFechar if (btFechar == null) { btFechar = new JButton("Fechar");
		 * btFechar.setFont(fontePadrao);
		 * 
		 * btFechar.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(java.awt.event.ActionEvent e) {
		 * 
		 * } }); btFechar.setBackground(Color.white);
		 * btFechar.setMnemonic(KeyEvent.VK_E); }
		 */
		// Painel do botao confirmar,limpar,cancelar e fechar
		pnBotao = new JPanel();
		GridLayout layout = new GridLayout(1, 3);
		layout.setHgap(5);
		pnBotao.setLayout(layout);
		pnBotao.setBounds(740, 520, 350, 30);
		pnBotao.add(btConfirmar);
		pnBotao.add(btCancelar);
		// pnBotao.add(btFechar);
		pnBotao.setBackground(new Color(176, 196, 222));

		// Painel do botao confirmar,limpar,cancelar e fechar
		pnBotao2 = new JPanel();
		GridLayout layoutt = new GridLayout(1, 1);
		layout.setHgap(5);
		pnBotao2.setLayout(layoutt);
		pnBotao2.setBounds(500, 520, 150, 30);
		pnBotao2.add(btFecharCaixa);
		pnBotao2.setBackground(new Color(176, 196, 222));

		// adiconar ao pnCentral(Painel central)
		pnCentral.add(imagem);
		pnCentral.add(pnBotao);
		pnCentral.add(pnBotao2);
		pnCentral.add(tfCodAluguel);
		pnCentral.add(cbFormaDePagemento);
		pnCentral.add(btBuscar);
		pnCentral.add(tfFuncionario);
		pnCentral.add(tfData);
		pnCentral.add(tfDias);
		pnCentral.add(tfLoja);
		pnCentral.add(tfTotal);
		pnCentral.add(lbTotal);
		pnCentral.add(spItens);
		pnCentral.add(spItens2);
		pnCentral.add(lbCodAluguel);
		pnCentral.add(lbFuncionario);
		pnCentral.add(lbPagamento);
		pnCentral.add(lbLoja);
		pnCentral.add(lbDias);

		add(pnCentral, BorderLayout.CENTER);

		// ******** definiçoes da janela***********

		// ****definir tamanho da janela ****
		setSize(1200, 670);

		// ****Colocar nome na janela****
		setTitle("Caixa Karozza rent a car");

		// ****Centralizar janela
		setLocationRelativeTo(null);

		// ****operaçãop padra de Fechar a tela****
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

		// ****Nao redimencionar a janela****
		setResizable(false);

		// mudar de fundo do da janela do java e nova cor
		// getContentPane().setBackground(Color.BLUE);
		getContentPane().setBackground(new Color(238, 238, 238));

		// ****Desenha a tela, ultima coisa a ser colocada apos o inicializ
		// componentes****

		setIconImage(new ImageIcon(getClass().getResource(
				"/imagens/menor_logo2.png")).getImage());
	}

	private void definirEventos() {
		btCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					limpar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btBuscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (dao.verificaDevolucao(tfCodAluguel.getText()) == true) {
						criarTabela(dao.buscar((tfCodAluguel.getText())));
						criarTabela2(dao.buscarOpcionais(tfCodAluguel.getText()));

						tfDias.setText(dao.diasLocacao(tfCodAluguel.getText())
								+ "");

						DecimalFormat format = new DecimalFormat("#0.00#");
						tfTotal.setText(format.format(dao
								.somaItens(tfCodAluguel.getText())
								* dao.diasLocacao(tfCodAluguel.getText())));
					} else {
						JOptionPane.showMessageDialog(null,
								"Confirme a devolução antes de pagar",
								"Atenção!", JOptionPane.INFORMATION_MESSAGE);
						limpar();
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btConfirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tfCodAluguel.getText().equals("")
						|| tfCodAluguel.getText().equals(null)) {
					JOptionPane.showMessageDialog(null, "Informe o código");
				} else {
					if (cbFormaDePagemento.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null,
								"Informe a forma de pagamento", "Atenção!",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						String itemSelecionado = cbFormaDePagemento
								.getSelectedItem().toString();
						if (itemSelecionado.equals("DINHEIRO")) {
							String valor;
							double n, n2 = 0, troco;
							valor = JOptionPane.showInputDialog(null,
									"Valor pago: ", "Caixa Karozza",
									JOptionPane.PLAIN_MESSAGE);
							n = Double.parseDouble(valor);

							try {
								try {
									CaixaDao c = new CaixaDao();

									dao.adicionaPagamento(c.buscaCaixa(), dao
											.buscaDevolucao(tfCodAluguel
													.getText()));
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								n2 = dao.somaItens(tfCodAluguel.getText())
										* (dao.diasLocacao(tfCodAluguel
												.getText()) + dao
												.diasLocacaoFinal(tfCodAluguel
														.getText()));
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
							try {
								int d = (dao.diasLocacaoFinal(tfCodAluguel.getText()));
								System.out.println(d);
								double d2 = dao.somaItens(tfCodAluguel.getText())* dao.diasLocacao(tfCodAluguel.getText());
								System.out.println(d2);
								if (d < 0) {
									DecimalFormat format = new DecimalFormat("#0.00");
									JOptionPane.showMessageDialog(null, "Troco: "
											+ format.format(n - d2));
								} else {
									DecimalFormat format = new DecimalFormat("#0.00");
									JOptionPane.showMessageDialog(null, "Troco: "
											+ format.format(n - n2));
								}
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							
							try {
								limpar();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							JOptionPane.showMessageDialog(TelaCaixa.this,
									"Alugado com sucesso", "Sucess",
									JOptionPane.INFORMATION_MESSAGE);
						} else

						if (cbFormaDePagemento.getSelectedItem().toString()
								.equals("CREDITO")
								|| cbFormaDePagemento.getSelectedItem()
										.toString().equals("DEBITO")) {
							CaixaDao c = new CaixaDao();

							try {
								dao.adicionaPagamento(c.buscaCaixa(), dao
										.buscaDevolucao(tfCodAluguel.getText()));
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							JOptionPane.showMessageDialog(TelaCaixa.this,
									"Alugado com sucesso", "Sucess",
									JOptionPane.INFORMATION_MESSAGE);
						}
						
						CupomFiscal principal = new CupomFiscal();

						DateFormat dateFormat = new SimpleDateFormat("dd/MM/dd");
						Date date = new Date();
						String now = dateFormat.format(date);
						System.out.println("olaaa " + now + tfTotal.getText());
						principal.lbData.setText("Dt. Entr.  : " + now);

						principal.lbPedido.setText("Pedido : "
								+ "Dt. Entr.  : " + now);

						principal.lbLoja.setText("Loja: " + tfLoja.getText());

						principal.lbTotalLocacao.setText(tfTotal.getText());
						principal.lbCodLocacao.setText(tfCodAluguel.getText());

						principal.show();
						
						int numeroLocacao = Integer.parseInt(tfCodAluguel.getText());

						 l = dao.buscaId(numeroLocacao);
						 
						try {
							new GerarPDF().GerarPdf();
							java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
							desktop.open(new File("comprovante-" + l.getId() + ".pdf"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						//TelaCaixa.this.dispose();
						try {
							limpar();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						return;
					}
				}
			}
		});

		btFecharCaixa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TelaCaixa.this.dispose(); // Referência this da tela de Venda
			
				Connection con = null;

				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = ConnectionFactory.getConnection();
					Statement stm = (Statement) con.createStatement();
					String SQL = "Select * from funcionario as f, loja as l where f.Loja_id = l.id and f.nome = '" + tfFuncionario.getText() + "';";

					// obter instancia do result set
					ResultSet rs = stm.executeQuery(SQL);

					// verificar se há ao menos um registro
					if (rs.next()) {
						// agora é só pegar os valores das colunas

						Funcionario f = new Funcionario();
						f.setId(rs.getLong("f.id"));
						f.setNome(rs.getString("f.nome"));
						f.setDataNascimento(rs.getString("f.dataNascimento"));
						f.setRg(rs.getString("f.rg"));
						f.setCpf(rs.getString("f.cpf"));
						f.setSexo(rs.getString("f.sexo"));
						f.setEmail(rs.getString("f.email"));
						f.setTelefone(rs.getString("f.telefone"));
						f.setRua(rs.getString("f.rua"));
						f.setNumRua(rs.getInt("f.numRua"));
						f.setComplemento(rs.getString("f.complemento"));
						f.setBairro(rs.getString("f.bairro"));
						f.setCidade(rs.getString("f.cidade"));
						f.setUf(rs.getString("f.uf"));
						f.setCep(rs.getString("f.cep"));
						f.setSenha(rs.getString("f.senha"));
						f.setLogin(rs.getString("f.login"));
						f.setTipo(rs.getString("f.tipo"));
						f.setLoja(rs.getString("l.bairro"));

						Log.Write(" Id usuario: "+f.getId()+ "  Data: " +Instant.now()+ " Nome: " + f.getNome()+ " saiu do sistema " +  " \n");
					

				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				String now = dateFormat.format(date);

				CaixaDao c = new CaixaDao();
				Caixa caixas = new Caixa();

				caixas.setDataFechamento(now);
				try {
					caixas.setId(c.buscaId());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					c.adicionaDataFechamento(caixas);
					
					new GerarPDF();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
	
	} catch (Exception e2) {
		// TODO: handle exception
	}
			}
		});
		btCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					criarTabela(dao.buscar(""));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					criarTabela2(dao.buscarOpcionais(""));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					limpar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}			
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	private void limpar() throws SQLException {
		criarTabela(dao.buscar(""));
		criarTabela2(dao.buscarOpcionais(""));
	}
}