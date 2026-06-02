/**
 * Thread responsável por controlar a descida automática das peças e a velocidade do jogo.
 */
public class TetrisThread extends Thread{
	
	private Board grade;
	private PainelLeaderboard rank;

	 /**
     * @param grade Board onde o jogo está sendo executado
     */
	public TetrisThread(Board grade) {
		this.grade = grade;
	}

	/**
     * Loop principal da thread — spawna peças, move para baixo e remove linhas completas.
     */
	@Override
	public void run() {
		while(true) {
			grade.spawnBloco();

			// Verifica se o jogo terminou e atualiza o leaderboard
			if(grade.isJogoTerminado()) {
				finalizarJogo();
				rank = new PainelLeaderboard();
                rank.atualizarLeaderboard();
				break;
			}
			
			while(grade.moveBlocoBaixo()) {
				try {
					// Velocidade da queda do bloco
					Thread.sleep(grade.getVelocidadeAtual());
				} catch (InterruptedException eventoParar) {
					eventoParar.printStackTrace();
				}
			}
			// Remove a linha e gera a pontuação
	        int removidas = grade.removerLinhasCompletas();
	        grade.atualizaPontuacao(removidas);
		}
	}
	
	/**
     * Encerra a partida, solicita o nome do jogador e salva a pontuação no ranking.
     */
	private void finalizarJogo() {
        grade.repaint();
        grade.painelDigitarNome();

        Usuario usuario = new Usuario(grade.getNomeJogador(), grade.getPontuacao(), grade.getLevel());

        EscreveJson escreveJson = new EscreveJson(usuario);
        escreveJson.gerarJSON();
    }
}
