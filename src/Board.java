import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Painel principal do jogo Tetris.
 * <p>
 * Gerencia a grade de jogo com 10 colunas por 20 linhas,
 * o loop de queda das peças via {@link Timer}, a detecção de colisões, a fixação de peças
 * e a remoção de linhas completas. Estende {@link JPanel} e realiza a renderização
 * do estado atual do jogo sobrescrevendo {@link #paintComponent(Graphics)}.
 * </p>
 * <p>
 * Invariantes:
 * <ul>
 *   <li>A grade possui sempre exatamente 10 colunas e 20 linhas.</li>
 *   <li>Células vazias contêm {@code null}; células ocupadas contêm a cor da peça fixada.</li>
 * </ul>
 * </p>
 */
public class Board extends JPanel {
	
	private final int gradeColuna = 10, gradeLinha = 20, gradeArea = 30;
	
	private Tetromino bloco;
	private Tetromino proximoBloco;
	private Color[][] fundoBlocos;

	private int velocidadeRapida = 16, velocidadeBase = 500;
	private int velocidadeAtual = velocidadeBase;
	
	private boolean jogoTerminado = false;
	
	private Random random = new Random();

	private int pontuacao = 0;
	private int level = 1;
	private JLabel labelPontuacao;
	private JLabel labelLevel;

	/**
	 * Constrói o painel de jogo, inicializa a grade, cria a primeira peça
	 * e inicia o loop de queda automática.
	 */
	public Board() {
		fundoBlocos = new Color[gradeLinha][gradeColuna];
	    iniciarJogo();
	}

	/**
	 * Define o label que vai exibir a pontuação na tela
	 * @param label JLabel onde a pontuação será exibida
	 */
	public void setLabelPontuacao(JLabel label) {
		this.labelPontuacao = label;
		atualizaPontuacao(0);
	}
	
	/**
	 * Define o label que vai exibir o score na tela
	 * @param label JLabel onde o score será exibida
	 */
	public void setLabelLevel(JLabel label) {
		this.labelLevel = label;
		atualizaLevel();
	}

	/**
	 * Atualiza a pontuação de acordo com as linhas removidas
	 * @param linhasRemovidas Quantidade de linhas removidas de uma vez
	 */
	public void atualizaPontuacao(int linhasRemovidas) {
		switch (linhasRemovidas) {
			case 1: pontuacao += 100; break;
			case 2: pontuacao += 300; break;
			case 3: pontuacao += 500; break;
			case 4: pontuacao += 800; break;
		}
		if (labelPontuacao != null) {
			labelPontuacao.setText("Pontuação: " + pontuacao);
		}
		// Atualiza level
		level = (pontuacao / 500) + 1;
		velocidadeNormal();
		atualizaLevel();
	}
	
	/**
	 * Atualiza o level de acordo com as linhas removidas
	 */
	private void atualizaLevel() {
		if (labelLevel != null) {
			labelLevel.setText("Level: " + level);
		}
	}

	/**
	 * Retorna a pontuação atual do jogador
	 * @return Pontuação acumulada
	 */
	public int getPontuacao() { return pontuacao; }
	
	/**
	 * Cria uma Thread e incia o jogo
	 */
	public void iniciarJogo() {
		new TetrisThread(this).start();
	}
	
	/**
	 * Cria o proximo bloco
	 */
	public void geraProximoBloco() {
		new TetrisThread(this).start();
	}
	
	/**
	 * Seleciona aleatoriamente um bloco e gera ele na grade
	 */
	public void criaBloco() {
		int seletor = random.nextInt(7);

	    switch (seletor) {
	        case 0: bloco = Tetromino.blocoI(); break;
	        case 1: bloco = Tetromino.blocoO(); break;
	        case 2: bloco = Tetromino.blocoT(); break;
	        case 3: bloco = Tetromino.blocoL(); break;
	        case 4: bloco = Tetromino.blocoJ(); break;
	        case 5: bloco = Tetromino.blocoS(); break;
	        case 6: bloco = Tetromino.blocoZ(); break;
	    }
	    bloco.spawn(gradeColuna);
	    
	    if (verificaColisaoAoNascer()) {
	        jogoTerminado = true;
	    }	
	}
	
	/**
	 * Desenha o bloco e o seu contorno na grade
	 * @param grade Contexto gráfico
	 * @param color Cor do bloco
	 * @param x Posição X do bloco
	 * @param y Posição Y do bloco
	 */
	private void desenhaBloco(Graphics grade, Color color, int x, int y) {
		// Desenha o bloco
		grade.setColor(color);
		grade.fillRect(x, y, gradeArea, gradeArea);
		// Desenha o contorno do bloco
		grade.setColor(Color.white);	
		grade.drawRect(x, y, gradeArea, gradeArea);
	}

	/**
	 * Fixa a peça na grade nas coordenadas informadas
	 * @param peca Bloco a ser fixado
	 * @param x Posição X da peça na grade
	 * @param y Posição Y da peça na grade
	 */
	public void fixarPeca(Tetromino peca, int x, int y) {
	    int[][] forma = peca.getBloco();
	    Color cor = peca.getCor();

	    for (int l = 0; l < peca.getHeight(); l++) {
	        for (int c = 0; c < peca.getWidth(); c++) {
	            if (forma[l][c] == 1) {
	                int gy = l + y;
	                int gx = c + x;

	                // Proteção contra índices inválidos
	                if (gy >= 0 && gy < gradeLinha && gx >= 0 && gx < gradeColuna) {
	                    fundoBlocos[gy][gx] = cor;
	                }
	            }
	        }
	    }
	}

	/**
	 * Gera o fundo quando um bloco chega ao final da grade
	 * @param grade Contexto gráfico
	 */
	private void geraFundoBlocos(Graphics grade) {
		Color color;
		
		for(int l = 0; l < gradeLinha; l++) {
			for(int c = 0; c < gradeColuna; c++) {
				color = fundoBlocos[l][c];
				// color esta sem valor (null), fazendo isso conseguimos gerar os blocos do fundo
				if(color != null) {
					int x = c * gradeArea;
					int y = l * gradeArea;
					
					desenhaBloco(grade, color, x, y);
				}
			}
		}
	}
	
	/**
	 * Remove uma linha de bloco quando estiver completa
	 * @return Quantidade de linhas removidas
	 */
	public int removerLinhasCompletas() {
	    int linhasRemovidas = 0;
		boolean linhaCompleta;
		
		for(int linha = gradeLinha - 1; linha >= 0; linha--) {
			linhaCompleta = true;
			for(int coluna = 0; coluna < gradeColuna; coluna++) {
				if(fundoBlocos[linha][coluna] == null) {
					linhaCompleta = false;
					break;
				}
			}
			if(linhaCompleta == true) {
				for(int c = 0; c < gradeColuna; c++) {
					fundoBlocos[linha][c] = null;
				}
				descerLinha(linha);
				linha++;
				repaint();
				
	            linhasRemovidas++;
			}
		}
	    return linhasRemovidas;
	}
	
	/**
	 * Move todos os blocos acima da linha removida para a linha de baixo
	 * @param l Índice da linha removida
	 */
	public void descerLinha(int l) {
		for(int linha = l; linha > 0; linha--) {
			for(int coluna = 0; coluna < gradeColuna; coluna++) {
				fundoBlocos[linha][coluna] = fundoBlocos[linha - 1][coluna];
			}
		}
	}
	
	/**
	 * Move o bloco para baixo e converte o bloco para Fundo
	 * @return Retorna verdadeiro caso nao tenha colisao e move o bloco para baixo, caso o contrario, retorna falso e fixa o bloco no fundo
	 */
	public boolean moveBlocoBaixo() {
		if (jogoTerminado) return false;
		if (colisao(bloco, bloco.getX(), bloco.getY() + 1)) {
			fixarPeca(bloco, bloco.getX(), bloco.getY());
			return false;
		}
		bloco.descerBloco();
		repaint();
		return true;
	}
	
	/**
	 * Move o bloco para a direita enquanto estiver caindo e verifica a colisão da borda esquerda dos blocos do fundo
	 */
	public void moveBlocoDireita() {
		if (jogoTerminado) return;
		if (!colisao(bloco, bloco.getX() + 1, bloco.getY())) {
	        bloco.moveDireita();
	        repaint();
	    }
	}
	
	/**
	 * Move o bloco para a esquerda enquanto estiver caindo e verifica a colisão da borda direita dos blocos do fundo
	 */
	public void moveBlocoEsquerda() {
		if (jogoTerminado) return;
		if (!colisao(bloco, bloco.getX() - 1, bloco.getY())) {
	        bloco.moveEsquerda();
	        repaint();
	    }
	}
	
	/**
	 * Rotaciona o bloco que esta caindo
	 */
	public void rotacionar() {
		if (jogoTerminado) return;
		bloco.rotacionarBloco(gradeColuna);
		repaint();
	}

	/**
	 * Desce o bloco até a primeira posição de colisão
	 */
	public void hardDrop() {
		if (jogoTerminado) return;
		while (!colisao(bloco, bloco.getX(), bloco.getY() + 1)) {
			bloco.descerBloco();
		}
		fixarPeca(bloco, bloco.getX(), bloco.getY());
		int removidas = removerLinhasCompletas();
		atualizaPontuacao(removidas);
		criaBloco();
		repaint();
	}
	
	/**
	 * Verifica caso o bloco chegou no fundo da grade
	 * @param peca Bloco atual
	 * @param x Posição X do bloco
	 * @param y Posição Y do bloco
	 * @return Retorna verdadeiro quando ocorre uma colisão no bloco, caso o contrario, retorna falso
	 */
	public boolean colisao(Tetromino peca, int x, int y) {
		int[][]forma = peca.getBloco();	
		
		for(int coluna = 0; coluna < peca.getWidth(); coluna++) {
			for(int linha = peca.getHeight() - 1; linha >= 0; linha--) {
				if(forma[linha][coluna] != 0) {
					int novoX = x + coluna;
	                int novoY = y + linha;

	                // Colisão borda da grade
	                if (novoX < 0 || novoX >= gradeColuna) {
	                    return true;
	                }

	                // Colisão fundo da grade
	                if (novoY >= gradeLinha) {
	                    return true;
	                }

	                // Colisão entra os blocos
	                if (novoY >= 0 && fundoBlocos[novoY][novoX] != null) {
	                	return true;
	                }
				}
			}
		}
		return false;
	}
	
	/**
	 * Verifica se o bloco recém-criado está colidindo com blocos do fundo.
	 */
	public boolean verificaColisaoAoNascer() {
	    int[][] forma = bloco.getBloco();

	    for (int l = 0; l < bloco.getHeight(); l++) {
	        for (int c = 0; c < bloco.getWidth(); c++) {
	            if (forma[l][c] != 0) {
	                int gridX = bloco.getX() + c;
	                int gridY = bloco.getY() + l;

	                // Se a parte do bloco está dentro dos limites visíveis da grade
	                if (gridX >= 0 && gridX < gradeColuna &&gridY < gradeLinha) {
	                    if (gridY >= 0 && fundoBlocos[gridY][gridX] != null) {
	                        return true; // Colisão detectada no spawn
	                    }
	                }
	                // Parte acima da grade
                    if (gridY < 0 && fundoBlocos[0][gridX] != null) {
                        return true;
                    }
	            }
	        }
	    }
	    return false;
	}

	/**
	 * Retorna a grade interna do tabuleiro
	 * @return Matriz de Color com o estado atual da grade
	 */
	public Color[][] getFundoBlocos() { return fundoBlocos; }

	/**
	 * Retorna se o jogo terminou
	 * @return Verdadeiro se o jogo acabou
	 */
	public boolean isJogoTerminado() { return jogoTerminado; }
	
	/**
	 * Retorna a velocidade atual do bloco no jogo
	 * @return Velocidade atual do bloco
	 */
	public int getVelocidadeAtual() {
	    return velocidadeAtual;
	}

	/**
	 * Retorna a velocidade acelerado do bloco no jogo
	 */
	public void acelerarQuedaBloco() {
	    velocidadeAtual = velocidadeRapida;
	}

	/**
	 * Retorna a velocidade normal do bloco no jogo
	 */
	public void velocidadeNormal() {
	    if(level > 20) {
	        velocidadeAtual = 50;
	    } else if(level > 10) {
	        velocidadeAtual = 100;
	    } else if(level > 5){
	        velocidadeAtual = 200;
	    } else if(level > 2){
	        velocidadeAtual = 350;
	    } else {
	        velocidadeAtual = 500;
	    }
	}
	
	/**
	 * Desenha o fundo preto do jogo
	 */
	private void desenhaFundoGrade(Graphics grade) {
		grade.setColor(Color.black);
		grade.fillRect(0, 0, getWidth(), getHeight());
	}
	
	/**
	 * Desenha as grades do jogo
	 */
	private void desenhaFormatoGrade(Graphics grade) {
		grade.setColor(Color.white);
        for(int linha = 0; linha <= gradeLinha; linha++){
        	grade.drawLine(0, linha * gradeArea, gradeArea * gradeColuna, linha * gradeArea);
        }	
		for(int coluna = 0; coluna <= gradeColuna; coluna++){
			grade.drawLine(coluna * gradeArea, 0, coluna * gradeArea, gradeArea * gradeLinha);
		}
	}
	
	/**
	 * Desenha os blocos do jogo
	 */
	private void desenhaBlocoGrade(Graphics grade) {
		for(int linha = 0; linha < bloco.getHeight(); linha++) {
			for(int coluna = 0; coluna < bloco.getWidth(); coluna++){
				if(bloco.getBloco()[linha][coluna] == 1) {
					
					int x = (bloco.getX() + coluna) * gradeArea;
					int y = (bloco.getY() + linha) * gradeArea;
					
					desenhaBloco(grade, bloco.getCor(), x, y);
				}
			}
		}
	}
	
	/**
	 * Desenha os blocos do jogo
	 */
	private void desenhaGameOverGrade(Graphics grade) {
		if(jogoTerminado) {
			grade.setColor(Color.BLACK);
		    grade.fillRect(25, 280, 260, 70);
			
			grade.setFont(new Font("Times New Roman", Font.BOLD, 36));
		    grade.setColor(Color.WHITE);
			grade.drawString("GAME OVER", 40, 328);
		}
	}
	
	@Override
	protected void paintComponent(Graphics grade) {
		super.paintComponent(grade);
		
		desenhaFundoGrade(grade);
		desenhaFormatoGrade(grade);
		desenhaBlocoGrade(grade);
		geraFundoBlocos(grade);
		desenhaGameOverGrade(grade);		
	}
	
}
