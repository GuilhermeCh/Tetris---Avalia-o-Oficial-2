import java.io.Serializable;

public class Usuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private String nome;
	private int pontuacao;
	private int level;
	
	public Usuario(String nome, int pontuacao, int level) {
		this.nome = nome;
		this.pontuacao = pontuacao;
		this.level = level;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNome() {
		return nome;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public int getLevel() {
		return level;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
