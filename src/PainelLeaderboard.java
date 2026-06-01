import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PainelLeaderboard extends JFrame {
	private JTextArea textoLeaderboard;
	private JTextArea[] ranks = new JTextArea[10];

    private File arquivo = new File("scores.json");
    private Gson gson = new Gson();
    
	public PainelLeaderboard() {
		setTitle("Leaderboar");
        setSize(400, 500);
        setResizable(false);
        setLayout(new GridLayout(5, 3, 10, 10));
        
        add(new JLabel("")); 
        textoLeaderboard = new JTextArea("Leaderboard");
        textoLeaderboard.setFont(new Font("Lucida Console", Font.BOLD, 17));
        textoLeaderboard.setForeground(Color.WHITE); 
        textoLeaderboard.setBackground(Color.BLACK);
        textoLeaderboard.setEditable(false);
        add(textoLeaderboard); 
        add(new JLabel("")); 
        
        for (int i = 0; i < 10; i++) {
            ranks[i] = new JTextArea((i + 1) + "º Lugar\n--- pts");
            ranks[i].setForeground(Color.WHITE); 
            ranks[i].setBackground(Color.BLACK);
            ranks[i].setEditable(false);
            add(ranks[i]);
        }
        carregarRanking();
	}
	
	private void carregarRanking() {
		if (arquivo.exists() && arquivo.length() > 0) {
            try {
    			BufferedReader reader = new BufferedReader(new FileReader(arquivo));
    			Usuario[] jogadores = gson.fromJson(reader, Usuario[].class);

    			// Coloca os jogadores em posiçao de melhores pontuações
				if (jogadores != null) {
					for (int i = 0; i < jogadores.length - 1; i++) {
						for (int f = i + 1; f < jogadores.length; f++) {
							if (jogadores[f].getPontuacao() > jogadores[i].getPontuacao()) {
							    Usuario temp = jogadores[i];
							    jogadores[i] = jogadores[f];
							    jogadores[f] = temp;
							}
						}
					}
				}
    			
    			// Imprime as posições dos jogadores
    			for (int i = 0; i < ranks.length; i++) {
                    if (i < jogadores.length) {
                        ranks[i].setText(
                            (i + 1) + "º Lugar\n" +
                            jogadores[i].getNome() + "\n" +
                            jogadores[i].getPontuacao() + " pts\n" +
                            jogadores[i].getLevel() + " lvl"
                        );
                    } else {
                        ranks[i].setText((i + 1) + "º Lugar\n--- pts");
                    }
                }
    			
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	
        getContentPane().setBackground(Color.BLACK);
	}
	
	public void atualizarLeaderboard() {
        carregarRanking();
    }
}
