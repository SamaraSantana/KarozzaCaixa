package br.com.senai.tela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.senai.dao.CaixaDao;
import br.com.senai.dao.ConnectionFactory;
import br.com.senai.dao.LoginDao;
import br.com.senai.modelo.Caixa;
import br.com.senai.modelo.Funcionario;
import br.com.senai.modelo.TipoFuncionario;
import br.com.senai.util.Log;

public class Login extends JFrame implements ActionListener {
	JPanel pnCentral, pnBotao, pnLogin;
	JLabel imagem, lbRecepcao, lbTipo, imagemUser, imagemSenha, imagemTipo;
	JTextField tfUsuario;
	JPasswordField jpSenha;
	Font fontePadrao, fontePadrao2, fontePadrao3;
	JButton btEntrar;
	CaixaDao daoCaixa;
	LoginDao daoL;
	Funcionario func;
	JComboBox<TipoFuncionario> cbTipo;

	private String nomeCompleto = null;

	public String getNomeCompleto() {

		return nomeCompleto;
	}

	public Login(java.awt.Frame parent, boolean modal) {
		super();
		// dao = new FuncionarioDao();
		inicializarComponentes();
		definirEventos();

	}

	private void inicializarComponentes() {

		// fonte padrao
		fontePadrao = new Font("Georgia, Times New Roman, serif", Font.PLAIN, 16);

		// fonte padrao2
		fontePadrao2 = new Font("Georgia, Times New Roman, serif", Font.PLAIN, 14);

		// fonte padrao3
		fontePadrao3 = new Font("Georgia, Times New Roman, serif", Font.PLAIN, 24);

		// pnCentral
		pnCentral = new JPanel();
		pnCentral.setLayout(null);

		// lbRecepcao(label recepcao)
		lbRecepcao = new JLabel("Seja bem-vindo!");
		lbRecepcao.setBounds(140, 100, 180, 30);
		lbRecepcao.setFont(fontePadrao3);

		tfUsuario = new JTextField();
		tfUsuario.setBackground(Color.white);
		tfUsuario.setBounds(130, 180, 200, 30);
		tfUsuario.setFont(fontePadrao2);

		jpSenha = new JPasswordField();
		jpSenha.setBackground(Color.white);
		jpSenha.setBounds(130, 240, 200, 30);
		jpSenha.setFont(fontePadrao2);

		imagem = new JLabel();
		imagem.setBounds(350, 5, 450, 480);
		imagem.setIcon(new ImageIcon(getClass().getResource("/imagens/menor_logo.png")));

		// imagem usuario
		imagemUser = new JLabel();
		imagemUser.setBounds(60, 170, 80, 50);
		imagemUser.setIcon(new ImageIcon(getClass().getResource("/imagens/user.png")));

		// imagem senha
		imagemSenha = new JLabel();
		imagemSenha.setBounds(65, 230, 80, 50);
		imagemSenha.setIcon(new ImageIcon(getClass().getResource("/imagens/cadeado.png")));

		// imagem tipo
		imagemTipo = new JLabel();
		imagemTipo.setBounds(63, 300, 80, 40);
		imagemTipo.setIcon(new ImageIcon(getClass().getResource("/imagens/icon-cracha.png")));

		// cb forma de pagamento
		cbTipo = new JComboBox<TipoFuncionario>(TipoFuncionario.values());
		cbTipo.setBounds(130, 305, 200, 30);
		cbTipo.setFont(fontePadrao2);
		cbTipo.setSelectedIndex(-1);

		// btEntrar
		btEntrar = new JButton("Entrar");
		btEntrar.setFont(fontePadrao);
		btEntrar.setBackground(Color.white);
		btEntrar.setMnemonic(KeyEvent.VK_S);

		pnBotao = new JPanel();
		GridLayout layout = new GridLayout(1, 3);
		layout.setHgap(5);
		pnBotao.setLayout(layout);
		pnBotao.setBounds(180, 380, 100, 40);
		pnBotao.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnBotao.add(btEntrar);

		pnCentral.add(tfUsuario);
		pnCentral.add(jpSenha);
		pnCentral.add(imagem);
		pnCentral.add(imagemUser);
		pnCentral.add(imagemSenha);
		pnCentral.add(imagemTipo);
		pnCentral.add(pnBotao);
		pnCentral.add(lbRecepcao);
		pnCentral.add(cbTipo);

		// Adicionando os componentes
		add(pnCentral, BorderLayout.CENTER);

		// ******** definiçoes da janela***********

		// ****definir tamanho da janela ****
		setSize(800, 550);

		// ****Colocar nome na janela****
		setTitle("Login Karozza");

		// ****(localização)Posiçao da janela****
		// setLocation(100,100);

		// ****Centralizar janela
		setLocationRelativeTo(null);

		// ****Colocar nome na janela****
		// setTitle("Cadastro Clientes");

		// ****operaçãop padra de Fechar a tela****
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// ****Nao redimencionar a janela****
		setResizable(false);

		// mudar de fundo do da janela do java e nova cor
		// getContentPane().setBackground(Color.BLUE);
		getContentPane().setBackground(new Color(255, 255, 255));

		// ****Desenha a tela, ultima coisa a ser colocada apos o inicializ
		// componentes****
		// Fazer com que a janela abra maximizado
		// setExtendedState(MAXIMIZED_BOTH);
		setIconImage(new ImageIcon(getClass().getResource("/imagens/menor_logo.png")).getImage());

		// setContentPane(new Background());
		setVisible(true);

	}

	private void definirEventos() {

		// Listener da tfUsuario
		// keyListener serve para verificar as teclas que estão sendo
		// presionadas
		tfUsuario.addKeyListener(new KeyAdapter() {
			@Override
			// o objeto keyEvent (e) tem as informaçoes da
			// tecla pressionada
			public void keyTyped(KeyEvent e) {
				// se p caractere da tecla pressionada
				// for númerico não deixa ir para TextField
				if (Character.isDigit(e.getKeyChar()) || (tfUsuario.getText().length() >= 15))
					e.consume();
				if (Character.isSpace(e.getKeyChar()))
					e.consume();
			}
		});
		jpSenha.addKeyListener(new KeyAdapter() {
			@Override
			// o objeto keyEvent (e) tem as informaçoes da
			// tecla pressionada
			public void keyTyped(KeyEvent e) {
				// se p caractere da tecla pressionada
				// for númerico não deixa ir para TextField
				if (jpSenha.getText().length() >= 8)
					e.consume();
				if (Character.isSpace(e.getKeyChar()))
					e.consume();
			}
		});

		btEntrar.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String usuario = tfUsuario.getText();
				String senha = jpSenha.getText();
				if (tfUsuario.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informe o Usuário!", "Atenção!",
							JOptionPane.INFORMATION_MESSAGE);
					tfUsuario.requestFocus();
				} else if (jpSenha.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informe a Senha!", "Atenção!",
							JOptionPane.INFORMATION_MESSAGE);
					jpSenha.requestFocus();

				} else if (cbTipo.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Informe o tipo!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
					jpSenha.requestFocus();

				} else {
					Connection con = null;
					
					try {
						con = ConnectionFactory.getConnection();
						Statement stm = (Statement) con.createStatement();
						String SQL = "Select * from funcionario as f, loja as l where f.Loja_id = l.id and f.login = '" + tfUsuario.getText() + "';";

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
							
							long id = rs.getLong("f.id");
							String loginn = rs.getString("f.login");
							String senhaa = rs.getString("f.senha");
							String tipoo = rs.getString("f.tipo");
							String completo = rs.getString("f.nome");
							String loja = rs.getString("l.bairro");

							if (tfUsuario.getText().equals(loginn) && jpSenha.getText().equals(senhaa)
									&& cbTipo.getSelectedItem().toString().equals(tipoo)) {
								nomeCompleto = completo;
								System.out.println("olaaa" + completo);
								
								
								Log.Write(" Id usuario:"+ f.getId()+" Data: " +Instant.now()+ " Nome: " + f.getNome() + " acessou o sistema "+ "\n");

								DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
								Date date = new Date();
								String now = dateFormat.format(date);
								
								CaixaDao c = new CaixaDao(); 
								Caixa caixas = new Caixa();
								
								caixas.setDataAbertura(now);
								caixas.setDataFechamento(now);
								caixas.setFuncionario(id+"");
								c.adicionaDataAbertura(caixas);

								TelaCaixa principal = new TelaCaixa();
								principal.tfFuncionario.setText(completo);
								principal.tfLoja.setText(loja);
								principal.show();

								Login.this.dispose();

								return;

							}
						}
						// em caso de usuario OU senha errados
						JOptionPane.showMessageDialog(null, "Usuario ou Senha Inválidos!", "Atenção!",
								JOptionPane.ERROR_MESSAGE);

					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erro na conexão, com o banco de dados!", "Atenção",
								JOptionPane.WARNING_MESSAGE);
					} finally {
						try {
							con.close();
						} catch (SQLException onConClose) {
							JOptionPane.showMessageDialog(null, "Erro na conexão, com o banco de dados!", "Atenção!",
									JOptionPane.WARNING_MESSAGE);
							onConClose.printStackTrace();
						}
					}
				}
				limpar();
			}

		});
	}

	/*
	 * 
	 * Login login = daoL.getCheca(f); if (func != null) { new TelaInicial();
	 * dispose(); }else{ JOptionPane.showMessageDialog(Login.this,
	 * "Funcionário não cadastrado", "Aviso", JOptionPane.WARNING_MESSAGE);
	 * 
	 * } limpar(); } }); }
	 */

	private void limpar() {
		tfUsuario.setText(null);
		jpSenha.setText(null);
		cbTipo.setSelectedIndex(-1);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
