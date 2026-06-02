import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void testaPontuacaoInicial() {

        Board board = new Board(false);

        assertEquals(0, board.getPontuacao());
    }
}