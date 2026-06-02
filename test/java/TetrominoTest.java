import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TetrominoTest {

    @Test
    public void testaSpawnCentralizado() {

        Tetromino t = Tetromino.blocoI();

        t.spawn(10);

        // deve estar no meio da grade
        assertTrue(t.getX() >= 0 && t.getX() <= 10);
        assertTrue(t.getY() == -1);
    }
    @Test
    public void testaMovimentoHorizontal() {

        Tetromino t = Tetromino.blocoI();

        t.spawn(10);

        int xInicial = t.getX();

        t.moveDireita();
        assertEquals(xInicial + 1, t.getX());

        t.moveEsquerda();
        assertEquals(xInicial, t.getX());
    }
    @Test
    public void testaQueda() {

        Tetromino t = Tetromino.blocoI();

        t.spawn(10);

        int yInicial = t.getY();

        t.descerBloco();

        assertEquals(yInicial + 1, t.getY());
    }
}
