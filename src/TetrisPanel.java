import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 * Janela principal do jogo Tetris.
 * <p>
 * Estende o link JFrame e implementa o link KeyListener para capturar
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
public class TetrisPanel extends JFrame implements KeyListener{
	private Board area;
	private JLabel labelPontuacao;

	/**
	 * Constrói a janela do Tetris, inicializa o painel de jogo e o label de pontuação
	 */
    public TetrisPanel() {
    	setTitle("Tetris");
        setSize(315, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        labelPontuacao = new JLabel("Pontuação: 0", SwingConstants.CENTER);

        area = new Board();
        area.setLabelPontuacao(labelPontuacao);

        setLayout(new BorderLayout());
        add(area, BorderLayout.CENTER);
        add(labelPontuacao, BorderLayout.SOUTH);

        addKeyListener(this);
        setFocusable(true);
        
        setVisible(true);
        requestFocusInWindow();
    }

    public static void main(String args[]) {
    	new TetrisPanel();
    }

    @Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			area.moveBlocoDireita();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			area.moveBlocoEsquerda();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			area.retornaVelocidadeRapida();
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			area.rotacionar();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			area.hardDrop();
		}
	}
    
    @Override
	public void keyReleased(KeyEvent e) {
		area.retornaVelocidadeNormal();
	}
    
	@Override
	public void keyTyped(KeyEvent e) {}

}
