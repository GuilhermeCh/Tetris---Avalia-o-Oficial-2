import java.io.Serializable;

/**
 * Representa um jogador com nome, pontuação, level e tentativas.
 */
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	private String nome;
	private int pontuacao;
	private int level;
	private int tentativas;

	/**
     * @param nome      Nome do jogador
     * @param pontuacao Pontuação obtida
     * @param level     Level atingido
     */
	public Usuario(String nome, int pontuacao, int level) {
		this.nome = nome;
		this.pontuacao = pontuacao;
		this.level = level;
		this.tentativas = 1;
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

	/** @return Número de tentativas */
	public int getTentativas() {
		return tentativas;
	}

	/** @param pontuacao Nova pontuação */
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	/** @param level Novo level */
	public void setLevel(int level) {
		this.level = level;
	}

	/** @param tentativas Novo número de tentativas */
	public void setTentativas(int tentativas) { 
		this.tentativas = tentativas; 
	}

}
