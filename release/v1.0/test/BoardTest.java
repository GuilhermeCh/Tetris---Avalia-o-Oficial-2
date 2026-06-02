import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void testaPontuacao1Linha() {

        Board board = new Board(false);

        board.atualizaPontuacao(1);

        assertEquals(100, board.getPontuacao());
    }
    @Test
    public void testaHardDrop() {

    Board board = new Board(false);

    board.hardDrop();

    assertTrue(board.getPontuacao() >= 0);
    }
    @Test
    public void testaLevel() {

    Board board = new Board(false);

    board.atualizaPontuacao(4);

    assertEquals(2, board.getLevel());
    }
}

