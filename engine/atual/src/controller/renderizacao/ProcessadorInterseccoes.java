package src.controller.renderizacao;

import java.awt.Color;
import java.util.ArrayList;
import src.model.interseccao.*;

public class ProcessadorInterseccoes {

    private final Color bgColor; //cor de fundo
    private final ArrayList<Intersectable> objetos;
    private final ArrayList<Light> luzes;
    private Vector3 energiaLuz;   //Energia da luz final
    private Vector3 intensidadeAmbiente;
    final double EPSILON = 1e-5; // Valor pequeno para evitar auto-interseção
    //Construtor
    public ProcessadorInterseccoes(ArrayList<Intersectable> objetos, ArrayList<Light> luzes) {
        this.bgColor = new Color(100, 100, 100); // Cor de fundo (cinza)
        this.objetos = objetos;
        this.luzes = luzes;
        this.intensidadeAmbiente = new Vector3(0.2, 0.2, 0.2);
    }

    //Método principal para calcular interseções e determinar a cor resultante
    public int interseccionarObjetos(Ray raio) {
        IntersectResult intersectResult = encontrarObjetoMaisProximo(raio);
        Intersectable objetoMaisProximo = intersectResult.getObject();
        double menorDistancia = intersectResult.getDistance();

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
            Intersection intersecao = objeto.intersect(raio);

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
        Vector3 vetorNormal = objeto.calcularNormal(pontoIntersecao);                   // Vetor normal (N)
        Vector3 shadowRayOrigin = pontoIntersecao.add(vetorNormal.multiply(EPSILON));    // Evitar auto-interseção
    
        externo:
        for (Light luz : luzes) {

            Vector3 lightDirection = luz.calcularDirecaoLuz(pontoIntersecao); // possivel recalculo do sqrt aqui em
            double distanciaAteLuz = lightDirection.length();
            lightDirection = lightDirection.normalize();
            
            Ray raioDaSombra = new Ray(shadowRayOrigin, lightDirection);
            for (Intersectable objetoSombra : objetos) {
                if (objetoSombra == objeto) {
                    continue; //Ignorar o proprio objeto
                }
                Intersection interseccaoSombra = objetoSombra.intersect(raioDaSombra);
                if (interseccaoSombra != null 
                    && interseccaoSombra.distance > EPSILON
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