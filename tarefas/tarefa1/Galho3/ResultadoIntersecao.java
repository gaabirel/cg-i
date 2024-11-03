package Galho3;

public class ResultadoIntersecao {
    private boolean haIntersecao;
    private int cor;

    public ResultadoIntersecao(boolean haIntersecao, int cor) {
        this.haIntersecao = haIntersecao;
        this.cor = cor;
    }

    public boolean haIntersecao() {
        return haIntersecao;
    }

    public int getCor() {
        return cor;
    }

}
