import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class EscreveJson{

    private Usuario usuario;
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    File arquivo = new File("scores.json");

    // Construtor
    public EscreveJson(Usuario usuario) {
        this.usuario = usuario;
    }
	
	public void gerarJSON() {
		JsonArray listaUsuario = new JsonArray();

		try {
			// Se arquivo existir, lê o conteúdo antigo
            if (arquivo.exists() && arquivo.length() > 0) {
                FileReader reader = new FileReader(arquivo);
                JsonElement elemento = JsonParser.parseReader(reader);
                
                if (elemento.isJsonArray()) {
                    listaUsuario = elemento.getAsJsonArray();
                }
                
                reader.close();
            }
            // adiciona usuário
            listaUsuario.add(gson.toJsonTree(usuario));

            // salva novamente
            FileWriter writer = new FileWriter(arquivo);
            gson.toJson(listaUsuario, writer);
			writer.close();
		
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
