import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EscreveJson{

    private Usuario usuario;

    // Construtor
    public EscreveJson(Usuario usuario) {
        this.usuario = usuario;
    }
	
	public void gerarJSON() {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
			String jsonUser = gson.toJson(usuario);
		
			FileWriter fileWriter = new FileWriter("D:\\TesteMaven\\src\\main\\java\\scores.json");
	
			fileWriter.write(jsonUser);
			fileWriter.flush();
			fileWriter.close();
		
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
