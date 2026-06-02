import java.io.Serializable;

/**
 * Representa um jogador com nome, pontuação e level.
 */
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	private String nome;
	private int pontuacao;
	private int level;

	/**
     * @param nome      Nome do jogador
     * @param pontuacao Pontuação obtida
     * @param level     Level atingido
     */
	public Usuario(String nome, int pontuacao, int level) {
		this.nome = nome;
		this.pontuacao = pontuacao;
		this.level = level;
	}

	/** @return Nome do jogador */
	public String getNome() {
		return nome;
	}

	/** @return Pontuação do jogador */
	public int getPontuacao() {
		return pontuacao;
	}

	/** @return Level atingido pelo jogador */
	public int getLevel() {
		return level;
	}

}
