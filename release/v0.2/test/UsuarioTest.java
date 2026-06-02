import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UsuarioTest {

    @Test
    public void testaNomeUsuario() {

        Usuario usuario = new Usuario("Marcelo", 1000, 3);

        assertEquals("Marcelo", usuario.getNome());
    }
}