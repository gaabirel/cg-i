package src.model.interseccao;
public class Light {

    // Atributos para a posição da luz e sua intensidade
    private Vector3 posicao;
    private Vector3 intensidade;

    // Construtor para Luz de tipo pontual
    public Light(Vector3 posicao, Vector3 intensidade) {
        this.posicao = posicao;
        this.intensidade = intensidade;

    }

    // Método para obter a posição da luz
    public Vector3 getPosicao() {
        return posicao;
    }

    // Método para definir a posição da luz (para luzes pontuais)
    public void setPosicao(Vector3 posicao) {
        this.posicao = posicao;
    }

    // Método para obter a intensidade da luz
    public Vector3 getIntensidade() {
        return intensidade;
    }

    // Método para alterar a intensidade da luz
    public void setIntensidade(Vector3 intensidade) {
        this.intensidade = intensidade;
    }

    // Método para calcular a direção da luz para um objeto específico
    public Vector3 calcularDirecaoLuz(Vector3 pontoIntersecao) {
        return this.posicao.subtract(pontoIntersecao).normalize();
    }
}
