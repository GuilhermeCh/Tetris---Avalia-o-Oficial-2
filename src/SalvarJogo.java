import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Color;
import java.io.*;

/**
 * Responsável por salvar e carregar o estado completo do jogo em um arquivo JSON.
 * <p>
 * O estado salvo inclui a grade de blocos fixados, a pontuação atual,
 * o level, o tipo da peça atual e da próxima peça.
 * </p>
 */
public class SalvarJogo {

    private static final String ARQUIVO_SAVE = "savegame.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Representa o estado do jogo em forma serializável para JSON.
     */
    public static class EstadoJogo {
        public int pontuacao;
        public int level;
        public String[][] grade;       // cores da grade como strings "#RRGGBB" ou null
        public int tipoBlocoAtual;     // 0=I, 1=O, 2=T, 3=L, 4=J, 5=S, 6=Z
        public int tipoProximoBloco;
        public int blocoX;
        public int blocoY;
    }

    /**
     * Salva o estado atual do jogo no arquivo savegame.json.
     *
     * @param board O painel de jogo cujo estado será salvo
     */
    public void salvar(Board board) {
        EstadoJogo estado = new EstadoJogo();
        estado.pontuacao = board.getPontuacao();
        estado.level = board.getLevel();
        estado.tipoBlocoAtual = board.getTipoBlocoAtual();
        estado.tipoProximoBloco = board.getTipoProximoBloco();
        estado.blocoX = board.getBlocoAtual().getX();
        estado.blocoY = board.getBlocoAtual().getY();

        // Converte a grade de Color[][] para String[][]
        Color[][] grade = board.getFundoBlocos();
        estado.grade = new String[grade.length][grade[0].length];
        for (int l = 0; l < grade.length; l++) {
            for (int c = 0; c < grade[0].length; c++) {
                if (grade[l][c] != null) {
                    Color cor = grade[l][c];
                    estado.grade[l][c] = String.format("#%02X%02X%02X",
                            cor.getRed(), cor.getGreen(), cor.getBlue());
                }
            }
        }

        try (FileWriter writer = new FileWriter(ARQUIVO_SAVE)) {
            gson.toJson(estado, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega o estado salvo no savegame.json direto no Board recebido.
     *
     * @param board O painel de jogo que receberá o estado carregado
     */
    public void carregar(Board board) {
        File arquivo = new File(ARQUIVO_SAVE);
        if (!arquivo.exists() || arquivo.length() == 0) return;
    
        try (FileReader reader = new FileReader(arquivo)) {
            EstadoJogo estado = gson.fromJson(reader, EstadoJogo.class);
            if (estado == null) return;
    
            board.setPontuacaoCarregada(estado.pontuacao);
    
            Color[][] grade = board.getFundoBlocos();
            for (int l = 0; l < estado.grade.length; l++) {
                for (int c = 0; c < estado.grade[0].length; c++) {
                    if (estado.grade[l][c] != null) {
                        grade[l][c] = Color.decode(estado.grade[l][c]);
                    }
                }
            }
    
            board.setBlocoAtual(estado.tipoBlocoAtual, estado.blocoX, estado.blocoY);
            board.setProximoBloco(estado.tipoProximoBloco);
            board.repaint();
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica se existe um arquivo de save disponível para carregar.
     *
     * @return true se existir um save, false caso contrário
     */
    public boolean existeSave() {
        File arquivo = new File(ARQUIVO_SAVE);
        return arquivo.exists() && arquivo.length() > 0;
    }
}
