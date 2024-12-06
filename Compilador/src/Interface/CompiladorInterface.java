package Interface;
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.border.AbstractBorder;
import Sintatico.LexicalError;
import Sintatico.Lexico;
import Sintatico.SemanticError;
import Sintatico.Semantico;
import Sintatico.Sintatico;
import Sintatico.SyntaticError;
import Sintatico.Token;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.awt.event.KeyEvent;

public class CompiladorInterface extends JFrame {
	private JTextArea editor, mensagemArea;
	private JLabel barraDeStatus;
	private File arquivoEscolhido = null;

	public CompiladorInterface() {
		setTitle("Compilador");
		setSize(910, 600);
		setMinimumSize(new Dimension(910, 600));
		setPreferredSize(new Dimension(910, 600));
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		criarComponentes();
		setVisible(true);
	}

	private ImageIcon redimensionarIcone(String caminho, int largura, int altura) {
		URL imgURL = getClass().getResource(caminho);
		ImageIcon icone = new ImageIcon(imgURL);
		Image imagem = icone.getImage();
		Image imagemRedimensionada = imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
		return new ImageIcon(imagemRedimensionada);
	}

	private void criarComponentes() {

		JToolBar toolbar = new JToolBar();
		toolbar.setPreferredSize(new Dimension(900, 70));
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));

		ImageIcon novoIcon = redimensionarIcone("/imagens/novo.png", 30, 30);
		ImageIcon abrirIcon = redimensionarIcone("/imagens/abrir.png", 30, 30);
		ImageIcon salvarIcon = redimensionarIcone("/imagens/salvar.png", 30, 30);
		ImageIcon copiarIcon = redimensionarIcone("/imagens/copiar.png", 30, 30);
		ImageIcon colarIcon = redimensionarIcone("/imagens/colar.png", 30, 30);
		ImageIcon recortarIcon = redimensionarIcone("/imagens/recortar.png", 30, 30);
		ImageIcon compilarIcon = redimensionarIcone("/imagens/compilar.png", 30, 30);
		ImageIcon equipeIcon = redimensionarIcone("/imagens/equipe.png", 30, 30);

		JButton novoBtn = new JButton("Novo [Ctrl-N]", novoIcon);
		novoBtn.setToolTipText("Novo [Ctrl-N]");
		JButton abrirBtn = new JButton("Abrir [Ctrl-O]", abrirIcon);
		abrirBtn.setToolTipText("Abrir [Ctrl-O]");
		JButton salvarBtn = new JButton("Salvar [Ctrl-S]", salvarIcon);
		salvarBtn.setToolTipText("Salvar [Ctrl-S]");
		JButton copiarBtn = new JButton("Copiar [Ctrl-C]", copiarIcon);
		copiarBtn.setToolTipText("Copiar [Ctrl-C]");
		JButton colarBtn = new JButton("Colar [Ctrl-V]", colarIcon);
		colarBtn.setToolTipText("Colar [Ctrl-V]");
		JButton recortarBtn = new JButton("Recortar [Ctrl-X]", recortarIcon);
		recortarBtn.setToolTipText("Recortar [Ctrl-X]");
		JButton compilarBtn = new JButton("Compilar [F7]", compilarIcon);
		compilarBtn.setToolTipText("Compilar [F7]");
		JButton equipeBtn = new JButton("Equipe [F1]", equipeIcon);
		equipeBtn.setToolTipText("Equipe [F1]");

		Dimension tamanhoBotao = new Dimension(100, 60);
		Font fonteBotao = new Font("Arial", Font.PLAIN, 10);
		for (JButton btn : new JButton[] { novoBtn, abrirBtn, salvarBtn, copiarBtn, colarBtn, recortarBtn, compilarBtn,
				equipeBtn }) {
			btn.setPreferredSize(tamanhoBotao);
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			btn.setVerticalTextPosition(SwingConstants.BOTTOM);
			btn.setFont(fonteBotao);
		}

		toolbar.add(novoBtn);
		toolbar.add(abrirBtn);
		toolbar.add(salvarBtn);
		toolbar.add(copiarBtn);
		toolbar.add(colarBtn);
		toolbar.add(recortarBtn);
		toolbar.add(compilarBtn);
		toolbar.add(equipeBtn);

		add(toolbar, BorderLayout.NORTH);

		editor = new JTextArea();
		editor.setSize(new Dimension(8000, 4000));

		JScrollPane scrollPaneEditor = new JScrollPane(editor);
		scrollPaneEditor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneEditor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		editor.setBorder(new NumberedBorder());

		mensagemArea = new JTextArea();
		mensagemArea.setPreferredSize(new Dimension(8000, 4000));
		mensagemArea.setEditable(false);
		JScrollPane scrollPaneMensagens = new JScrollPane(mensagemArea);
		scrollPaneMensagens.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneMensagens.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneEditor, scrollPaneMensagens);
		splitPane.setResizeWeight(0.8);
		splitPane.setDividerLocation(400);

		add(splitPane, BorderLayout.CENTER);

		barraDeStatus = new JLabel("Nenhum arquivo aberto");
		barraDeStatus.setPreferredSize(new Dimension(900, 25));
		add(barraDeStatus, BorderLayout.SOUTH);

		novoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.setText("");
				mensagemArea.setText("");
				barraDeStatus.setText("Nenhum arquivo aberto");
				arquivoEscolhido = null;
			}
		});

		KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
		InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = getRootPane().getActionMap();

		inputMap.put(ctrlN, "novo");
		actionMap.put("novo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				novoBtn.doClick();
			}
		});

		abrirBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					arquivoEscolhido = fileChooser.getSelectedFile();
					System.out.println(arquivoEscolhido);
					try (BufferedReader reader = new BufferedReader(new FileReader(arquivoEscolhido))) {
						editor.read(reader, null);
						barraDeStatus.setText("Arquivo aberto: " + arquivoEscolhido.getAbsolutePath());
						mensagemArea.setText("");
					} catch (IOException ex) {
						mensagemArea.setText("Erro ao abrir arquivo: " + ex.getMessage());
					}
				}
			}

		});

		KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		InputMap inputMapO = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMapO = getRootPane().getActionMap();

		inputMapO.put(ctrlO, "abrir");
		actionMapO.put("abrir", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirBtn.doClick();
			}
		});

		salvarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (arquivoEscolhido == null) {
					JFileChooser fileChooser = new JFileChooser();
					int result = fileChooser.showSaveDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {
						arquivoEscolhido = fileChooser.getSelectedFile();
						mensagemArea.setText("");
					}
				}

				if (arquivoEscolhido != null) {
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoEscolhido))) {
						editor.write(writer);
						mensagemArea.setText("");
						barraDeStatus.setText("Arquivo salvo: " + arquivoEscolhido.getAbsolutePath());
					} catch (IOException ex) {
						mensagemArea.setText("Erro ao salvar arquivo: " + ex.getMessage());
					}
				}
			}
		});

		KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		InputMap inputMapS = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMapS = getRootPane().getActionMap();

		inputMapS.put(ctrlS, "salvar");
		actionMapS.put("salvar", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarBtn.doClick();
			}
		});

		copiarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.copy();
			}
		});

		KeyStroke ctrlC = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
		InputMap inputMapC = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMapC = getRootPane().getActionMap();

		inputMapC.put(ctrlC, "copiar");
		actionMapC.put("copiar", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				copiarBtn.doClick();
			}
		});

		colarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.paste();
			}
		});

		KeyStroke ctrlV = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);
		InputMap inputMapV = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMapV = getRootPane().getActionMap();

		inputMapV.put(ctrlV, "colar");
		actionMapV.put("colar", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				colarBtn.doClick();
			}
		});

		recortarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.cut();
			}
		});

		KeyStroke ctrlX = KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK);
		InputMap inputMapX = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMapX = getRootPane().getActionMap();

		inputMapX.put(ctrlX, "recortar");
		actionMapX.put("recortar", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				recortarBtn.doClick();
			}
		});

		equipeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mensagemArea.setText(
						"Equipe de Desenvolvimento: Alana Cristina Andreazza, Caio Abraão Manarim e Letícia Fruet");
			}
		});

		KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
		InputMap inputMapF1 = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMapF1 = getRootPane().getActionMap();

		inputMapF1.put(f1, "equipe");
		actionMapF1.put("equipe", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				equipeBtn.doClick();
			}
		});

		compilarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sintatico sintatico = new Sintatico();
				Semantico semantico = new Semantico(arquivoEscolhido);
				Lexico lexico = new Lexico(new java.io.StringReader(editor.getText()));

				try {
					sintatico.parse(lexico, semantico);
					mensagemArea.setText("programa compilado com sucesso");
				} catch (LexicalError e1) {
					int linhaErro = getLinhaErro(e1.getPosition(), editor.getText().split("\n"));
					Token tokenErro = e1.getToken();
					if (tokenErro.getId() == 2) {
						mensagemArea.setText("Erro na linha " + linhaErro + " - " + tokenErro.getLexeme() + "palavra reservada invalida");
					}
					mensagemArea.setText("Erro na linha " + linhaErro + " - " + e1.getMessage()); 
				} catch (SyntaticError e2) {
					int linhaErro = getLinhaErro(e2.getPosition(), editor.getText().split("\n"));
					Token tokenEncontrado = e2.getToken();
					
					if (tokenEncontrado.getLexeme().equals("$")) {
						mensagemArea.setText("Erro na linha " + linhaErro + " - encontrado EOF \n\t " + e2.getMessage());
					} else if (tokenEncontrado.getId() == 2) {
					        mensagemArea.setText("Erro na linha " + linhaErro + " - " + tokenEncontrado.getLexeme() + " palavra reservada inválida");
					    } else if (tokenEncontrado.getId() == 19) {
					    	 mensagemArea.setText("Erro na linha " + linhaErro + " - encontrado constante_string esperado if read write writeln repeat identificador");
					    } else {
						mensagemArea.setText("Erro na linha " + linhaErro + " - encontrado "
								+ tokenEncontrado.getLexeme() + "\n\t " + e2.getMessage());
					}

				} catch (SemanticError e3) {
					int linhaErro = getLinhaErro(e3.getPosition(), editor.getText().split("\n"));
					mensagemArea.setText("Erro na linha " + linhaErro + " - " + e3.getMessage());
				}
			}
		});

		KeyStroke f7 = KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0);
		InputMap inputMapF7 = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMapF7 = getRootPane().getActionMap();

		inputMapF7.put(f7, "compilar");
		actionMapF7.put("compilar", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				compilarBtn.doClick();
			}

		});
	}

	protected int getLinhaErro(int posicao, String[] linhasCodigo) {
		int posicaoAtual = 0;
		for (int i = 0; i < linhasCodigo.length; i++) {
			posicaoAtual += linhasCodigo[i].length() + 1; // +1 para contar o '\n'
			if (posicao < posicaoAtual) {
				return i + 1;
			}
		}
		return -1; // Caso não encontre a linha return 0;
	}

	public static void main(String[] args) {
		new CompiladorInterface();
	}

	class NumberedBorder extends AbstractBorder {
		private static final long serialVersionUID = -5089118025935944759L;

		private static int lineHeight;
		private final int characterHeight = 8, characterWidth = 7;
		private final Color myColor;

		NumberedBorder() {
			myColor = new Color(0, 0, 0);
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			JTextArea textArea = (JTextArea) c;
			Font font = textArea.getFont();
			FontMetrics metrics = g.getFontMetrics(font);
			lineHeight = metrics.getHeight();

			Color oldColor = g.getColor();
			g.setColor(myColor);

			double r = (double) height / (double) lineHeight;
			int rows = (int) (r + 0.5);
			String str = String.valueOf(rows);
			int lineLeft = calculateLeft(height) + 10;

			int px = 0, py = 0, lenght = 0;

			int visibleLines = textArea.getHeight() / lineHeight;
			for (int i = 0; i < visibleLines; i++) {

				str = String.valueOf(i + 1);
				lenght = str.length();

				py = lineHeight * i + 14;
				px = lineLeft - (characterWidth * lenght) - 2;

				g.drawString(str, px, py);
			}

			g.drawLine(lineLeft, 0, lineLeft, height);

			g.setColor(oldColor);
		}

		@Override
		public Insets getBorderInsets(Component c) {
			int left = calculateLeft(c.getHeight()) + 13;
			return new Insets(1, left, 1, 1);
		}

		private int calculateLeft(int height) {
			double r = (double) height / (double) lineHeight;
			int rows = (int) (r + 0.5);
			String str = String.valueOf(rows);
			int lenght = str.length();
			return characterHeight * lenght;
		}
	}
}