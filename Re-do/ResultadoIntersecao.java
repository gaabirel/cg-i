public class ResultadoIntersecao {
    private boolean haIntersecao;
    private int color;

    public ResultadoIntersecao(boolean haIntersecao, int color) {
        this.haIntersecao = haIntersecao;
        this.color = color;
    }

    public boolean haIntersecao() {
        return haIntersecao;
    }

    public int getColor() {
        return color;
    }

}
