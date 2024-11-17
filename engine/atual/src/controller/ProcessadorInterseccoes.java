package src.controller;

import java.awt.Color;
import java.util.ArrayList;

import src.model.*;

public class ProcessadorInterseccoes {

    //parametros da luz
    private Vector3 energiaLuzAmbiente;   // Fator de luz ambiente
    private double n;                    //expoente de brilho, ou coeficiente de especularidade
    private Color bgColor; 
    private Vector3 intensidadeAmbiente;

    public ProcessadorInterseccoes(){
        this.bgColor = new Color(100, 100, 100); // cor de fundo (cinza)
        this.intensidadeAmbiente = new Vector3(0.3, 0.3, 0.3);
        this.n = 5;

    }
    
    public ResultadoIntersecao interseccionaObjetos(ArrayList<Intersectable> objetos, Ray raio, ArrayList<Light> luzes) {
        double menorDistanciaT = Double.MAX_VALUE; // Inicializando o T (distancia) como o maior possível
        int corPintar = bgColor.getRGB(); // Cor de fundo
        Intersectable objetoMaisProximo = null;
        Intersection intersecao = null; //ponto de intersecao

        //calcular a intersecao de cada objeto na cena
        for (Intersectable objeto : objetos) {
            intersecao = objeto.intersect(raio);
            if ((intersecao != null) && (intersecao.distance < menorDistanciaT)) {
                menorDistanciaT = intersecao.distance;
                objetoMaisProximo = objeto;
            }   
        }
        // Se o objeto nao for nulo, teve intersecao
        if (objetoMaisProximo != null) {
            // Posição do ponto de interseção
            double  px = menorDistanciaT * raio.direction.x, 
                    py = menorDistanciaT * raio.direction.y, 
                    pz = menorDistanciaT * raio.direction.z;
            Vector3 pontoIntersecao = new Vector3(px, py, pz);

            //pegar a cor do objeto que teve intersecao mais proxima
            //int objetoCor = objetoMaisProximo.getColor();

            int r, g, bCor;
            //calcular energia ambiente
            energiaLuzAmbiente = intensidadeAmbiente.arroba(objetoMaisProximo.getKdifuso());
            // Adicionar a energia da luz ambiente
            r = Math.min(255, (int) (255 * this.energiaLuzAmbiente.x));
            g = Math.min(255, (int) (255 * this.energiaLuzAmbiente.y ));
            bCor = Math.min(255, (int) (255 * this.energiaLuzAmbiente.z));
       

            for(Light luz : luzes){
                //criando o vetor da luz
                Vector3 vetorLuz = luz.calcularDirecaoLuz(pontoIntersecao);

                // -------  Calcular vetor em direção à luz (L) ----------
                double comprimentoL = Math.sqrt(vetorLuz.dot(vetorLuz));
                vetorLuz = vetorLuz.multiply(1/comprimentoL);

                //Verificar se o ponto está em sombra
                boolean sombra = false; //inicialmente consideramos que nao tem sombra
                Ray raio2 = new Ray(pontoIntersecao, vetorLuz);

                for (Intersectable sombraObjeto : objetos) {
                    if (sombraObjeto == objetoMaisProximo) continue; //Ignorar o proprio objeto
                
                    Intersection intersecao2 = sombraObjeto.intersect(raio2);
                    if((intersecao2 != null) && (intersecao2.distance > 0) && intersecao2.distance < comprimentoL) {
                        sombra = true;
                        break;
                    }            
                }
            
                if (!sombra) {//se nao tiver sombra, adicionar luz difusa e especular

                    // Calcular vetor normal (N)
                    Vector3 N = objetoMaisProximo.calcularNormal(pontoIntersecao);        
                    
                    // Produto escalar N * L e intensidade difusa
                    double produtoEscalarNL = Math.max(0, vetorLuz.dot(N));
                    Vector3 energiaLuzDifusa = luz.getIntensidade().arroba(objetoMaisProximo.getKdifuso()).multiply(produtoEscalarNL);

                    // Cálculo da iluminação especular

                    // Vetor de visão (V) na direção oposta ao raio
                    Vector3 V = new Vector3(-raio.direction.x, -raio.direction.y, -raio.direction.z).normalize();

                    // Cálculo do vetor refletido (R)
                    Vector3 R = N.multiply(2 * produtoEscalarNL).subtract(vetorLuz);

    
                    // Produto escalar entre R e V (com proteção para não ser negativo)
                    double produtoEscalarRV = Math.max(0, R.dot(V));

               
                    //double a = 1.0;
                    //double b = 0.1;
                    //double c = 0.01;
                    double d = vetorLuz.subtract(pontoIntersecao).length();
                    double fatorAtenuacao = 1/(1+(0.1*d)+(0.01*Math.pow(d, 2)));

                    // Cálculo da energia especular
                    Vector3 energiaLuzEspecular = luz.getIntensidade().arroba(objetoMaisProximo.getKespecular()).multiply(Math.pow(produtoEscalarRV, this.n));
                    
                    //calculo da energia final: (energia especular + energia difusa) * fator de atenuacao
                    Vector3 energiaFinal = (energiaLuzEspecular.add(energiaLuzDifusa)).multiply(fatorAtenuacao);

                    // Adicionar a energia da luz final
                    r = Math.min(255, (int) (r + 255 * energiaFinal.x));
                    g = Math.min(255, (int) (g + 255 * energiaFinal.y));
                    bCor = Math.min(255, (int) (bCor + 255 * energiaFinal.z));
           
                }
            }
            corPintar = new Color(r, g, bCor).getRGB();
            return new ResultadoIntersecao(intersecao, corPintar); //teve intersecao
        }
        return new ResultadoIntersecao(intersecao, corPintar);     //nao teve intersecao, vai retornar false e bgcolor
    }
}
