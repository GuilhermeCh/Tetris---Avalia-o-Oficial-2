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

/**
 * Responsável por gravar os dados do jogador no arquivo scores.json.
 */
public class EscreveJson{

    private Usuario usuario;
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    File arquivo = new File("scores.json");

    /**
     * @param usuario Usuário com os dados a serem gravados
     */
    public EscreveJson(Usuario usuario) {
        this.usuario = usuario;
    }

	/**
     * Grava os dados do usuário no arquivo scores.json.
     * <p>
     * Se o arquivo já existir, mantém os dados anteriores e adiciona o novo jogador.
     * </p>
     */
	public void gerarJSON() {
		List<Usuario> listaUsuario = new ArrayList<>();

		try {
			// Se arquivo existir, lê o conteúdo antigo
			if (arquivo.exists() && arquivo.length() > 0) {
                FileReader reader = new FileReader(arquivo);
                
                Type tipoLista = new TypeToken<ArrayList<Usuario>>(){}.getType();
                List<Usuario> listaExistente = gson.fromJson(reader, tipoLista);
                
                if (listaExistente != null) {
                    listaUsuario = listaExistente;
                }
                reader.close();
            }
            // adiciona usuário
            listaUsuario.add(this.usuario);

            // salva novamente
            FileWriter writer = new FileWriter(arquivo);
            gson.toJson(listaUsuario, writer);
			writer.close();
		
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
