import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.Test;

public class TetrominoTest {

    @Test
    public void testaBlocoI() {

        Tetromino bloco = Tetromino.blocoI();

        assertNotNull(bloco);
    }

    @Test
    public void testaCorBlocoO() {

        Tetromino bloco = Tetromino.blocoO();

        assertEquals(Color.yellow, bloco.getCor());
    }

    @Test
    public void testaSpawn() {

        Tetromino bloco = Tetromino.blocoT();

        bloco.spawn(10);

        assertEquals(-1, bloco.getY());
    }

    @Test
    public void testaMoverDireita() {

        Tetromino bloco = Tetromino.blocoL();

        bloco.moveDireita();

        assertEquals(1, bloco.getX());
    }

    @Test
    public void testaMoverEsquerda() {

        Tetromino bloco = Tetromino.blocoL();

        bloco.moveEsquerda();

        assertEquals(-1, bloco.getX());
    }

    @Test
    public void testaDescerBloco() {

        Tetromino bloco = Tetromino.blocoS();

        bloco.descerBloco();

        assertEquals(1, bloco.getY());
    }

    @Test
    public void testaRotacao() {

        Tetromino bloco = Tetromino.blocoT();

        bloco.spawn(10);

        bloco.rotacionarBloco(10);

        assertNotNull(bloco.getBloco());
    }

    @Test
    public void testaQuantidadeRotacoes() {

        Tetromino bloco = Tetromino.blocoZ();

        assertEquals(4, bloco.getBlocos().length);
    }
}
