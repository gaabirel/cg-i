package src.controller;

import java.awt.Color;
import java.util.ArrayList;
import src.model.interseccao.*;

public class ProcessadorInterseccoes {

    private final Color bgColor; // Cor de fundo
    private final ArrayList<Intersectable> objetos;
    private final ProcessadorLuzSombra processadorLuzSombra;
    //Construtor
    public ProcessadorInterseccoes(ArrayList<Intersectable> objetos, ArrayList<Light> luzes) {
        this.bgColor = new Color(100, 100, 100); // Cor de fundo (cinza)
        this.objetos = objetos;
        this.processadorLuzSombra = new ProcessadorLuzSombra(luzes, objetos);
    }

    //Método principal para calcular interseções e determinar a cor resultante
    public int interseccionarObjetos(Ray raio) {
        Intersectable objetoMaisProximo = encontrarObjetoMaisProximo(raio);

        //Sem interseção: Retornar cor de fundo
        if (objetoMaisProximo == null) {
            return bgColor.getRGB();
        }

        //Com interseção: Calcular a cor baseada na iluminação
        return calcularCorInterseccao(objetoMaisProximo, raio);
    }

    //Encontra o objeto mais próximo que intersecta com o raio
    public Intersectable encontrarObjetoMaisProximo(Ray raio) {
        double menorDistancia = Double.MAX_VALUE;
        Intersectable objetoMaisProximo = null;

        for (Intersectable objeto : objetos) {
            Intersection intersecao = objeto.intersect(raio);

            if (intersecao != null && intersecao.distance < menorDistancia) {
                menorDistancia = intersecao.distance;
                objetoMaisProximo = objeto;
            }
        }

        return objetoMaisProximo;
    }

    
    //Calcula a cor resultante na interseção
    private int calcularCorInterseccao(Intersectable objeto, Ray raio) {
        double menorDistancia = objeto.intersect(raio).distance;
        Vector3 pontoIntersecao = raio.origin.add(raio.direction.multiply(menorDistancia));

        int[] corPintar = processadorLuzSombra.processar(objeto, pontoIntersecao, raio);

        return converterArrayParaRGB(corPintar);
    }

    //Converte um array de cores (RGB) para um inteiro
    private int converterArrayParaRGB(int[] corPintar) {
        return (corPintar[0] << 16) | (corPintar[1] << 8) | corPintar[2];
    }

}