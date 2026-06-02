import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.jupiter.api.Test;
import java.io.FileReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class EscreveJsonTest {

    @Test
    public void testaConteudoJson() throws Exception {

        // APAGA arquivo antigo
        File arquivo = new File("scores.json");

        if (arquivo.exists()) {
            arquivo.delete();
        }

        Usuario usuario = new Usuario("Marcelo", 1000, 3);

        EscreveJson json = new EscreveJson(usuario);

        json.gerarJSON();

        FileReader reader = new FileReader("scores.json");

        JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

        String nome = array
                .get(0)
                .getAsJsonObject()
                .get("nome")
                .getAsString();

        assertTrue(nome.equals("Marcelo"));

        reader.close();
    }
    @Test
    public void testaAcumuloDeUsuarios() {

    Usuario u1 = new Usuario("A", 100, 1);
    Usuario u2 = new Usuario("B", 200, 2);

    new EscreveJson(u1).gerarJSON();
    new EscreveJson(u2).gerarJSON();

    File arquivo = new File("scores.json");

    assertTrue(arquivo.length() > 0);
    }
    @Test
    public void testaQuantidadeUsuarios() throws Exception {

    Usuario u = new Usuario("Teste", 100, 1);

    new EscreveJson(u).gerarJSON();

    FileReader reader = new FileReader("scores.json");

    JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

    assertTrue(array.size() >= 1);

    reader.close();
    }
}
