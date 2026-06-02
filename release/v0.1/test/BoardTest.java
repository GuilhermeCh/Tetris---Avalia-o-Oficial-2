import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void testaPontuacaoInicial() {

        Board board = new Board();

        assertEquals(0, board.getPontuacao());
    }
}