import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class EscreveJsonTest {

    @Test
    public void testaCriacaoJson() {

        Usuario usuario = new Usuario("Marcelo", 1000, 3);

        EscreveJson escreveJson = new EscreveJson(usuario);

        escreveJson.gerarJSON();

        File arquivo = new File("scores.json");

        assertTrue(arquivo.exists());
    }
}
