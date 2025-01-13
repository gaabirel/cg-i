package src.model.interseccao;
public class Light {

    // Atributos para a posição da luz e sua intensidade
    private Vector3 posicao;
    private Vector3 intensidade;

    public Light(Vector3 posicao, Vector3 intensidade) {
        this.posicao = posicao;
        this.intensidade = intensidade;

    }

    public Light aplicarMatrixCamera(double[][] matrix){
        return new Light(posicao.multiplyMatrix4x4(matrix), intensidade);
    }

    public Vector3 getPosicao() {
        return posicao;
    }

    public void setPosicao(Vector3 posicao) {
        this.posicao = posicao;
    }

    public Vector3 getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(Vector3 intensidade) {
        this.intensidade = intensidade;
    }

    public Vector3 calcularDirecaoLuz(Vector3 pontoIntersecao) {
        return this.posicao.subtract(pontoIntersecao);
    }
}
