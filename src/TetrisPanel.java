import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 * Janela principal do jogo Tetris.
 * <p>
 * Estende o link JFrame, implementa ActionListener para o clique do botão de reset, 
 * limpando os componentes antigos da memória para evitar sobreposição visual e de pontuação. Implementa o link KeyListener para capturar
 * as entradas do teclado e repassá-las ao link Board. As teclas mapeadas são:
 * <ul>
 *   <li>← : mover a peça para a esquerda</li>
 *   <li>→ : mover a peça para a direita</li>
 *   <li>↓ : ativar descida rápida (soft drop)</li>
 *   <li>↑ : rotacionar a peça 90° no sentido horário</li>
 *   <li>Espaço : hard drop (peça cai até o fundo)</li>
 * </ul>
 * O foco do teclado é garantido via {@code setFocusable(true)} e
 * {@code requestFocusInWindow()}.
 * </p>
 */
public class TetrisPanel extends JFrame implements KeyListener, ActionListener {
	
	private Board area;
	private JLabel labelPontuacao;
	private JLabel labelLevel;
	private JButton labelLeaderboard;
	private JButton botaoReset;

	/**
	 * Constrói a janela do Tetris, inicializa o painel de jogo e o label de pontuação
	 */
    public TetrisPanel(boolean carregarJogo) {
    	setTitle("Tetris");
        setSize(665, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        
        area = new Board();
        area.setBounds(140, 0, 520, 640);
        add(area);
        
        labelPontuacao = new JLabel("Pontuação: 0", SwingConstants.CENTER);
        labelPontuacao.setForeground(Color.WHITE); 
        labelPontuacao.setBounds(20, 260, 100, 30);
        area.setLabelPontuacao(labelPontuacao);
        add(labelPontuacao);
        
        labelLevel = new JLabel("Level: 1", SwingConstants.CENTER);
        labelLevel.setForeground(Color.WHITE); 
        labelLevel.setBounds(20, 240, 100, 30);
        area.setLabelLevel(labelLevel);
        add(labelLevel);
        
        labelLeaderboard = new JButton("Leaderboard");
        labelLeaderboard.setFont(new Font("Lucida Console", Font.BOLD, 12));
        labelLeaderboard.setBounds(10, 470, 120, 40);
        labelLeaderboard.addActionListener(this);
        labelLeaderboard.setFocusable(false);
        add(labelLeaderboard);
        
        botaoReset = new JButton("Reset");
        botaoReset.setFont(new Font("Lucida Console", Font.BOLD, 20));
        botaoReset.setBounds(10, 520, 120, 40);
        botaoReset.addActionListener(this);
        add(botaoReset);

        addKeyListener(this);
        setFocusable(true);
        
        getContentPane().setBackground(Color.BLACK);
        
        setVisible(true);
        requestFocusInWindow();
    }

    public static void main(String args[]) {
    	new TetrisPanel();
    }

    @Override
    public void actionPerformed(ActionEvent menuEvent) {
        // Faz o reset do game
    	if(menuEvent.getSource() == botaoReset) {    		
    		remove(area);
    		remove(labelPontuacao);
            remove(labelLevel);
    		
            area = new Board();
            area.setBounds(140, 0, 520, 640);
            add(area);
            
            labelPontuacao = new JLabel("Pontuação: 0", SwingConstants.CENTER);
            labelPontuacao.setForeground(Color.WHITE); 
            labelPontuacao.setBounds(20, 260, 100, 30);
            area.setLabelPontuacao(labelPontuacao);
            add(labelPontuacao);
            
            labelLevel = new JLabel("Level: 1", SwingConstants.CENTER);
            labelLevel.setForeground(Color.WHITE); 
            labelLevel.setBounds(20, 240, 100, 30);
            area.setLabelLevel(labelLevel);
            add(labelLevel);
            
    		revalidate();
    		repaint();
    		
    		requestFocusInWindow();
    	}
    	
    	// Tela dos LeaderBoard
    	if(menuEvent.getSource() == labelLeaderboard) {
    		PainelLeaderboard rank = new PainelLeaderboard();
    		rank.setVisible(true);
    	}
    }
    
    @Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			area.moveBlocoDireita();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			area.moveBlocoEsquerda();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			area.acelerarQuedaBloco();
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			area.rotacionar();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			area.hardDrop();
		}
	}
    
    @Override
	public void keyReleased(KeyEvent e) {
    	area.velocidadeNormal();
	}
    
	@Override
	public void keyTyped(KeyEvent e) {}

}
