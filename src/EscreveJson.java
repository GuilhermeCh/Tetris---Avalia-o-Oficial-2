import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class EscreveJson{

    private Usuario usuario;
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    File arquivo = new File("scores.json");

    // Construtor
    public EscreveJson(Usuario usuario) {
        this.usuario = usuario;
    }
	
	public void gerarJSON() {
		List<Usuario> listaUsuario = new ArrayList<>();
		boolean usuarioNovo = true;

		try {
			// Verifica se existe um arquivo Json e depois preencher a lista com os dados do arquivo Json existente 
			if (arquivo.exists() && arquivo.length() > 0) {
                FileReader reader = new FileReader(arquivo);
                
                Type tipoLista = new TypeToken<ArrayList<Usuario>>(){}.getType();
                List<Usuario> listaExistente = gson.fromJson(reader, tipoLista);
                
                if (listaExistente != null) {
                    listaUsuario = listaExistente;
                }
                reader.close();
            }
			
			// Atualiza o rank pelo nome, caso for o mesmo nome, ira atualizar a tentativa e verifica a pontuação e level do jogador
			for (Usuario user : listaUsuario) {
                if (user.getNome().equals(this.usuario.getNome())) { 
                	user.setTentativas(user.getTentativas() + 1);
                    
                    if (user.getPontuacao() < this.usuario.getPontuacao()) {
                    	user.setPontuacao(this.usuario.getPontuacao());
                    }
                    if (user.getLevel() < this.usuario.getLevel()) {
                    	user.setLevel(this.usuario.getLevel());
                    }
                    usuarioNovo = false;
                    break;
                } 
            }
           
			if (usuarioNovo) {
                listaUsuario.add(this.usuario);
            }
            
            // Salva novamente o arquivo
            FileWriter writer = new FileWriter(arquivo);
            gson.toJson(listaUsuario, writer);
			writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
