import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Tela inicial do jogo Tetris.
 * <p>
 * Exibe as opções de iniciar um novo jogo ou carregar uma partida salva.
 * É o ponto de entrada da aplicação — o {@code main()} foi movido para cá,
 * saindo do {@link TetrisPanel}.
 * </p>
 */
public class TelaPrincipal extends JFrame implements ActionListener {

    private JButton botaoNovoJogo;
    private JButton botaoCarregarJogo;

    /**
     * Constrói a tela inicial com os botões de novo jogo e carregar jogo.
     */
    public TelaPrincipal() {
        setTitle("Tetris");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        // Título
        JLabel titulo = new JLabel("TETRIS", SwingConstants.CENTER);
        titulo.setFont(new Font("Lucida Console", Font.BOLD, 64));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 80, 400, 80);
        add(titulo);

        // Botão Novo Jogo
        botaoNovoJogo = new JButton("Novo Jogo");
        botaoNovoJogo.setFont(new Font("Lucida Console", Font.BOLD, 20));
        botaoNovoJogo.setBounds(100, 240, 200, 50);
        botaoNovoJogo.setBackground(Color.WHITE);
        botaoNovoJogo.setForeground(Color.BLACK);
        botaoNovoJogo.setFocusPainted(false);
        botaoNovoJogo.addActionListener(this);
        add(botaoNovoJogo);

        // Botão Carregar Jogo — só fica ativo se existir um save
        botaoCarregarJogo = new JButton("Carregar Jogo");
        botaoCarregarJogo.setFont(new Font("Lucida Console", Font.BOLD, 20));
        botaoCarregarJogo.setBounds(100, 310, 200, 50);
        botaoCarregarJogo.setFocusPainted(false);

        boolean existeSave = new File("savegame.json").exists();
        botaoCarregarJogo.setEnabled(existeSave);
        botaoCarregarJogo.setBackground(existeSave ? Color.WHITE : Color.DARK_GRAY);
        botaoCarregarJogo.setForeground(existeSave ? Color.BLACK : Color.GRAY);
        botaoCarregarJogo.addActionListener(this);
        add(botaoCarregarJogo);

        setVisible(true);
    }

    /**
     * Trata os cliques nos botões da tela inicial.
     * <ul>
     *   <li>Novo Jogo: abre o {@link TetrisPanel} normalmente.</li>
     *   <li>Carregar Jogo: abre o {@link TetrisPanel} com o estado salvo.</li>
     * </ul>
     *
     * @param e Evento de clique
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoNovoJogo) {
            dispose();
            new TetrisPanel(false);
        } else if (e.getSource() == botaoCarregarJogo) {
            dispose();
            new TetrisPanel(true);
        }
    }

    /**
     * Ponto de entrada da aplicação.
     *
     * @param args Argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaPrincipal::new);
    }
}
