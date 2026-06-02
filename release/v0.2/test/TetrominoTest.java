import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TetrominoTest {

    @Test
    public void testaBlocoI() {

        Tetromino bloco = Tetromino.blocoI();

        assertEquals(1, bloco.getWidth());
        assertEquals(4, bloco.getHeight());
    }
}