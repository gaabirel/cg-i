package src.model;

public class ResultadoIntersecao {
    private int color;
    private Intersection intersecao;

    public ResultadoIntersecao(Intersection intersecao, int color) {
        this.color = color;
        this.intersecao = intersecao;
    }

    public Intersection getIntersection() {
        return intersecao;
    }

    public int getColor() {
        return color;
    }

}
