package src.controller;

import java.util.ArrayList;

import src.model.Intersectable;
import src.model.Intersection;
import src.model.Light;
import src.model.Ray;
import src.model.Vector3;

public class ProcessadorLuzSombra {
    ArrayList<Light> luzes;
    ArrayList<Intersectable> objetos;
    private Vector3 energiaLuzAmbiente;   //Fator de luz ambiente
    private double n;                    //expoente de brilho, ou coeficiente de especularidade
    private Vector3 intensidadeAmbiente;

    public ProcessadorLuzSombra(ArrayList<Light> luzes, ArrayList<Intersectable> objetos){
        this.luzes = luzes;
        this.objetos = objetos;
        this.intensidadeAmbiente = new Vector3(0.2, 0.2, 0.2);
        this.n = 10;
    }

    public int[] processar(Intersectable objeto, Vector3 pontoIntersecao, Ray raio){
        int[] corPintar = {0, 0, 0};
        //adicionar a luz ambiente na cor
        energiaLuzAmbiente = intensidadeAmbiente.arroba(objeto.getKdifuso());
        adicionarEnergia(energiaLuzAmbiente, corPintar);

        Vector3 V = raio.direction.negate(); //Vetor de visão (V) na direção oposta ao raio
        Vector3 N = objeto.calcularNormal(pontoIntersecao);  //Calcular vetor normal (N)
        for(Light luz : luzes){
            Vector3 vetorLuz = luz.calcularDirecaoLuz(pontoIntersecao);
            double comprimentoL = vetorLuz.length();

            Ray raioDaSombra = new Ray(pontoIntersecao, vetorLuz);
            Boolean sombra = false;
            for (Intersectable sombraObjeto : objetos) {
                if (sombraObjeto == objeto) continue; //Ignorar o proprio objeto
                Intersection interseccaoSombra = sombraObjeto.intersect(raioDaSombra);
                if((interseccaoSombra != null) && (interseccaoSombra.distance > 0) && interseccaoSombra.distance < comprimentoL) {
                    //tem sombra, então retorne sem calcular a luz difusa e especular
                    sombra = true; 
                }            
            }
            if (!sombra){
                //se nao tiver sombra, adicionar luz difusa e especular
                double produtoEscalarNL = Math.max(0, vetorLuz.dot(N));
                Vector3 energiaLuzDifusa = luz.getIntensidade().arroba(objeto.getKdifuso()).multiply(produtoEscalarNL);

                Vector3 R = N.multiply(2 * produtoEscalarNL).subtract(vetorLuz); //Cálculo do vetor refletido (R)
                double produtoEscalarRV = Math.max(0, R.dot(V));

                double fatorAtenuacao = 1 / (1 + 0.1 * comprimentoL + 0.001 * comprimentoL * comprimentoL);

                Vector3 energiaLuzEspecular = luz.getIntensidade().arroba(objeto.getKespecular()).multiply(Math.pow(produtoEscalarRV, this.n));
                Vector3 energiaFinal = (energiaLuzEspecular.add(energiaLuzDifusa)).multiply(fatorAtenuacao);

                adicionarEnergia(energiaFinal, corPintar);
            }
        }
        return corPintar;
    }

    public void adicionarEnergia(Vector3 energia, int[] cor) {
        cor[0] = Math.min(255, (int) (cor[0] + 255 * energia.x));  //Red
        cor[1] = Math.min(255, (int) (cor[1] + 255 * energia.y));  //Green
        cor[2] = Math.min(255, (int) (cor[2] + 255 * energia.z));  //Blue
    }
}
