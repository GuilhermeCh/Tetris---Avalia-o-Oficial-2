import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileWriter;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

public class PainelLeaderboardTest {

    @Test
    public void testaLeituraArquivo() throws Exception {

        Gson gson = new Gson();

        Usuario[] usuarios = {
            new Usuario("A", 100, 1),
            new Usuario("B", 300, 2)
        };

        FileWriter writer = new FileWriter("scores.json");
        gson.toJson(usuarios, writer);
        writer.close();

        assertDoesNotThrow(() -> {
            new PainelLeaderboard();
        });
    }
    @Test
    public void testaOrdenacao() throws Exception {

        Gson gson = new Gson();

        Usuario[] usuarios = {
            new Usuario("A", 100, 1),
            new Usuario("B", 500, 2),
            new Usuario("C", 300, 1)
        };

        FileWriter writer = new FileWriter("scores.json");
        gson.toJson(usuarios, writer);
        writer.close();

        PainelLeaderboard painel = new PainelLeaderboard();

        // depois da ordenação, o maior deve vir primeiro
        // (mesmo sem acessar UI diretamente, validamos lógica)

        java.lang.reflect.Field field = PainelLeaderboard.class
            .getDeclaredField("ranks");

        field.setAccessible(true);

        javax.swing.JTextArea[] ranks =
            (javax.swing.JTextArea[]) field.get(painel);

        String texto = ranks[0].getText();

        assertEquals(true, texto.contains("B"));
    }
    

    @Test
    public void testaArquivoVazio() throws Exception {

        FileWriter writer = new FileWriter("scores.json");
        writer.write("");
        writer.close();

        assertDoesNotThrow(() -> {
            new PainelLeaderboard();
        });
    }
}


