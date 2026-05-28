
public class TetrisThread extends Thread{
	
	private Board grade;
	
	public TetrisThread(Board grade) {
		this.grade = grade;
	}
	
	@Override
	public void run() {
		while(true) {
			grade.spawnBloco();

			// Verifica se o jogo terminou
			if(grade.isJogoTerminado()) {
				finalizarJogo();
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
	
	private void finalizarJogo() {
        grade.repaint();
        grade.painelDigitarNome();

        Usuario usuario = new Usuario(grade.getNomeJogador(), grade.getPontuacao(), grade.getLevel());

        EscreveJson escreveJson = new EscreveJson(usuario);
        escreveJson.gerarJSON();
    }
}
