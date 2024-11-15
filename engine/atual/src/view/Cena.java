package src.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import src.model.*;


public class Cena {
    private ArrayList<Intersectable> objetosCena;
    private ArrayList<Light> luzes;

    public Cena() {
        this.objetosCena = new ArrayList<>();

        //criando as luzes
        this.luzes = new ArrayList<>(); 
        //Vector3 intensidade = new Vector3(0.3, 0.3, 0.3);
        Vector3 intensidadeRosa = new Vector3(1.0, 0.4, 0.7);
        intensidadeRosa = intensidadeRosa.multiply(0.2);
        Light luz1 = new Light(new Vector3(1, 2, -1) , intensidadeRosa);
        Light luz2 = new Light(new Vector3(0, 3, -6) , intensidadeRosa);
        Light luz3 = new Light(new Vector3(0, 2, -4), intensidadeRosa);
        luzes.add(luz1);
        luzes.add(luz2);
        luzes.add(luz3);

        //criando as esferas
        ArrayList<Esfera> esferas = gerarEsferas();
        esferas.addAll(gerarEsferasAleatorias(1));

        //criando um plano
        double xmin = -2.0;
        double xmax = 2.0;
        double ymin = -2.0;
        double ymax = 2.0;
        Vector3 pontoPlano = new Vector3(0.0, -1, -16); // Ponto no plano
        Vector3 normalPlano = new Vector3(0.0, 2, 1); // Normal para cima
        Plano plano = new Plano(pontoPlano, normalPlano, new Color(255, 255, 255), xmin, xmax, ymin, ymax);

        //adicionar os objetos para a lista de objetos da cena
        objetosCena.add(plano);
        objetosCena.addAll(esferas);
    }
    
    public ArrayList<Intersectable> getObjetos() {
        return objetosCena;
    }

    public ArrayList<Light> getLuzes() {
        return luzes;
    }

    public static ArrayList<Esfera> gerarEsferas(){
        ArrayList<Esfera> esferas = new ArrayList<>();
        Vector3 vetor1 = new Vector3(0, 0, -10);
        Vector3 vetor2 = new Vector3(0, 0, -15);
        Vector3 vetor3 = new Vector3(2, 0, -15);
        esferas.add(new Esfera(0.5, vetor1, new Color(255, 255, 255)));
        esferas.add(new Esfera(1.0, vetor2, new Color(255, 0, 0)));
        esferas.add(new Esfera(1.0, vetor3, new Color(122, 55, 50)));

        return esferas;
    }
    public static ArrayList<Esfera> gerarEsferasAleatorias(int quantidade) {
        ArrayList<Esfera> esferas = new ArrayList<>();
        Random random = new Random();

        // Definindo limites para as posições aleatórias (você pode ajustar conforme necessário)
        double limitePosicao = 3; // Limite máximo para as coordenadas x, y e z
        double raioEsfera = 1.0; // Raio fixo para as esferas

        for (int i = 0; i < quantidade; i++) {
            // Gerando posições aleatórias para x, y, z
            double x = random.nextDouble() * 2 * limitePosicao - limitePosicao; // Posição aleatória entre -limitePosicao e limitePosicao
            double y = random.nextDouble() * 2 * limitePosicao - limitePosicao;
            double z = -10 - (5 * random.nextDouble());

            int r = random.nextInt(256);  // Valor aleatório entre 0 e 255
            int g = random.nextInt(256);  // Valor aleatório entre 0 e 255
            int b = random.nextInt(256);  // Valor aleatório entre 0 e 255
    
            // Criando a esfera com a posição e o material gerado
            Esfera esfera = new Esfera(raioEsfera, new Vector3(x, y, z), new Color(r,g,b)); 

            // Adicionando a esfera à lista
            esferas.add(esfera);
        }

        return esferas;
    }
}