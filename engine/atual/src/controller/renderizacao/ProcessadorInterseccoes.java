package src.controller.renderizacao;

import java.awt.Color;
import java.util.ArrayList;

import src.model.Camera;
import src.model.interseccao.*;
import src.model.objetos.Intersectable;
import src.config.Config;

public class ProcessadorInterseccoes {

    private final Color bgColor; //cor de fundo
    private ArrayList<Intersectable> objetos;
    private ArrayList<Light> luzes;
    private Vector3 energiaLuz;   //Energia da luz final
    private Vector3 intensidadeAmbiente;
    private Camera camera;
    private double[][] matrizTransformacao;
    //Construtor
    public ProcessadorInterseccoes(ArrayList<Intersectable> objetosMundo, ArrayList<Light> luzesMundo, Camera camera) {
        this.bgColor = new Color(100, 100, 100); // Cor de fundo (cinza)
        this.camera = camera;
        this.objetos = objetosMundo;
        this.luzes = luzesMundo;
        this.intensidadeAmbiente = new Vector3(0.2, 0.2, 0.2);
        this.matrizTransformacao = camera.getCameraMatrix();
    }

    //Método principal para calcular interseções e determinar a cor resultante
    public int interseccionarObjetos(Ray raio) {
        IntersectResult intersectResult = encontrarObjetoMaisProximo(raio);
        Intersectable objetoMaisProximo = intersectResult.getObject();
        double menorDistancia = intersectResult.getDistance();
        matrizTransformacao = camera.getCameraMatrix();
        //Sem interseção: Retornar cor de fundo
        if (objetoMaisProximo == null) {
            return bgColor.getRGB();
        }

        //Com intersecao: Calcular a cor baseada na iluminação
        Vector3 pontoIntersecao = raio.origin.add(raio.direction.multiply(menorDistancia));

        int[] corPintar = calcularCorComLuz(objetoMaisProximo, pontoIntersecao, raio);

         //Converte um array de cores (RGB) para um inteiro
        return (corPintar[0] << 16) | (corPintar[1] << 8) | corPintar[2];
    }

    //Encontra o objeto mais próximo que intersecta com o raio
    public IntersectResult encontrarObjetoMaisProximo(Ray raio) {
        double menorDistancia = Double.MAX_VALUE;
        Intersectable objetoMaisProximo = null;

        for (Intersectable objeto : objetos) {
            Intersection intersecao = objeto.intersect(raio, matrizTransformacao);

            if (intersecao != null && intersecao.distance < menorDistancia) {
                menorDistancia = intersecao.distance;
                objetoMaisProximo = objeto;
            }
        }

        return new IntersectResult(objetoMaisProximo, menorDistancia);
    }

    /*
     * Calcula a cor de um ponto de interseção com a luz 
     */

    public int[] calcularCorComLuz(Intersectable objeto, Vector3 pontoIntersecao, Ray raio) {
        int[] corPintar = {0, 0, 0};

        energiaLuz = intensidadeAmbiente.arroba(objeto.getKambiente());                 // Adicionar luz ambiente
        
        Vector3 vetorVisao = raio.direction.negate();                                   // Vetor de visão (V)
        Vector3 vetorNormal = objeto.calcularNormal(pontoIntersecao, matrizTransformacao);                   // Vetor normal (N)
        Vector3 shadowRayOrigin = pontoIntersecao.add(vetorNormal.multiply(Config.EPSILON));    // Evitar auto-interseção
        
        externo:
        for (Light luz : luzes) {
            luz = luz.aplicarMatrixCamera(matrizTransformacao);
            Vector3 lightDirection = luz.calcularDirecaoLuz(pontoIntersecao); 
            double distanciaAteLuz = lightDirection.length();
            lightDirection = lightDirection.multiply(1/distanciaAteLuz);

            Ray raioDaSombra = new Ray(shadowRayOrigin, lightDirection);
            for (Intersectable objetoSombra : objetos) {
                
                Intersection interseccaoSombra = objetoSombra.intersect(raioDaSombra, matrizTransformacao);
                if (interseccaoSombra != null 
                    && interseccaoSombra.distance > Config.EPSILON
                    && interseccaoSombra.distance < distanciaAteLuz) {
                    break externo; //Objeto está na sombra
                }
            }
            energiaLuz = energiaLuz.add(ContribuicoesDeLuz(luz, objeto, vetorNormal, vetorVisao, lightDirection, distanciaAteLuz, corPintar, energiaLuz));
            }

        corPintar[0] = Math.min(255, (int) (corPintar[0] + 255 * energiaLuz.getX()));  //Red
        corPintar[1] = Math.min(255, (int) (corPintar[1] + 255 * energiaLuz.getY()));  //Green
        corPintar[2] = Math.min(255, (int) (corPintar[2] + 255 * energiaLuz.getZ()));  //Blue
        return corPintar;
    }
    
    private Vector3 ContribuicoesDeLuz(Light luz, Intersectable objeto, Vector3 vetorNormal, Vector3 vetorVisao,
                                             Vector3 direcaoLuz, double comprimentoLuz, int[] corPintar, Vector3 energiaLuz) {
        double produtoEscalarNL = Math.max(0, direcaoLuz.dot(vetorNormal));
        Vector3 vetorRefletido = vetorNormal.multiply(2 * produtoEscalarNL).subtract(direcaoLuz);
        double produtoEscalarRV = Math.max(0, vetorRefletido.dot(vetorVisao));
        
        Vector3 energiaDifusa = luz.getIntensidade().arroba(objeto.getKdifuso()).multiply(produtoEscalarNL);
        Vector3 energiaEspecular = luz.getIntensidade().arroba(objeto.getKespecular()).multiply(Math.pow(produtoEscalarRV, objeto.getBrilho()));
        double fatorAtenuacao = 1 / (1 + 0.1 * comprimentoLuz + 0.001);
        //double fatorAtenuacao = 1 / (1 + 0.1 * comprimentoLuz + 0.001 * comprimentoLuz * comprimentoLuz);

        return (energiaDifusa.add(energiaEspecular)).multiply(fatorAtenuacao);      
    }
    
}