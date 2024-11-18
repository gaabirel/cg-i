package src.controller;

import java.awt.Color;
import java.util.ArrayList;

import src.model.*;

public class ProcessadorInterseccoes {

    private Color bgColor; 
    ArrayList<Intersectable> objetos;
    ArrayList<Light> luzes;

    ProcessadorLuzSombra processadorLuzSombra;
    public ProcessadorInterseccoes(ArrayList<Intersectable> objetos, ArrayList<Light> luzes){
        this.bgColor = new Color(100, 100, 100); // cor de fundo (cinza)
        this.objetos = objetos;
        this.luzes = luzes;
        this.processadorLuzSombra = new ProcessadorLuzSombra(luzes, objetos);
    }
    
    public int interseccionaObjetos(Ray raio) {
        double menorDistanciaT = Double.MAX_VALUE; // Inicializando o T (distancia) como o maior possível
        Intersectable objetoMaisProximo = null;

        //Loop de intersecção com objetos
        for (Intersectable objeto : objetos) {
            Intersection tempIntersecao = objeto.intersect(raio);
            if ((tempIntersecao != null) && (tempIntersecao.distance < menorDistanciaT)) {
                menorDistanciaT = tempIntersecao.distance;
                objetoMaisProximo = objeto;
            }   
        }
        //Sem interseccao
        if(objetoMaisProximo == null){
             return bgColor.getRGB();
        }
        //Com interseccao
        else {
            Vector3 pontoIntersecao = raio.direction.multiply(menorDistanciaT);
            int[] corPintar = processadorLuzSombra.processar(objetoMaisProximo, pontoIntersecao, raio);
            
            return (corPintar[0] << 16) | (corPintar[1] << 8) | corPintar[2];
        }
    }
}
