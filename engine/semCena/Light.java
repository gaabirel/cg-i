import java.awt.Color;

import src.Vector3;

public class Light {

    // Atributos para a posição da luz e sua intensidade
    private Vector3 posicao;
    private Vector3 intensidade;
    private Color cor; // Cor da luz (RGB)
    

    // Construtor para Luz de tipo pontual
    public Light(Vector3 posicao, Vector3 intensidade, Color cor) {
        this.posicao = posicao;
        this.intensidade = intensidade;
        this.cor = cor;
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

    // Método para obter a cor da luz
    public Color getCor() {
        return cor;
    }

    // Método para definir a cor da luz
    public void setCor(Color cor) {
        this.cor = cor;
    }

    // Método para ajustar a cor da luz com base em um fator de intensidade
    public Color ajustarCor(double fator) {
        int r = Math.min(255, (int)(this.cor.getRed() * fator));
        int g = Math.min(255, (int)(this.cor.getGreen() * fator));
        int b = Math.min(255, (int)(this.cor.getBlue() * fator));
        return new Color(r, g, b);
    }

    // Método para calcular a direção da luz para um objeto específico
    public Vector3 calcularDirecaoLuz(Vector3 pontoIntersecao) {
        return this.posicao.subtract(pontoIntersecao).normalize();
    }
}
