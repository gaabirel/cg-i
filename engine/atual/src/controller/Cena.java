package src.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import src.model.*;


public class Cena {
    private ArrayList<Intersectable> objetosCena;
    private ArrayList<Light> luzes;

    private Random random;
  
    public Cena() {
        this.random = new Random();
        this.objetosCena = new ArrayList<>();

        //gera as luzes da cena
        luzes = gerarLuzes();

        //gerando os vetores de objetos
        ArrayList<Esfera> esferas = gerarEsferas();
        ArrayList<Plano> planos = gerarPlanos();

        //adicionando + esferas aleatorias
        esferas.addAll(gerarEsferasAleatorias(1));


        Vector3[] k_iluminacao = {new Vector3(1, 0, 0), new Vector3(1, 1, 1), new Vector3(1, 1, 1)};
        //adicionar os objetos para a lista de objetos da cena
        Cilindro cilindro = new Cilindro(new Vector3(0, 0, -10), 0.2, 0.5, k_iluminacao);
        objetosCena.add(cilindro); //em construção
        objetosCena.addAll(esferas);
        //objetosCena.addAll(planos);
    }
    
    public ArrayList<Intersectable> getObjetos() {
        return objetosCena;
    }

    public ArrayList<Light> getLuzes() {
        return luzes;
    }

    public static ArrayList<Light> gerarLuzes(){
        //criando as luzes
        ArrayList<Light> luzesGeradas = new ArrayList<>(); 
        Vector3 intensidade = new Vector3(0.8, 0.8, 0.8);
        Vector3 intensidadeRosa = new Vector3(1.0, 0.5, 0.7);
        intensidadeRosa = intensidadeRosa.multiply(0.4); //diminuir a intensidade

        luzesGeradas.add(new Light(new Vector3(-1, 2, -1) , intensidade));
        //luzes.add(new Light(new Vector3(0, 3, -15) , intensidadeRosa));
        //luzes.add(new Light(new Vector3(0, 2, -4), intensidadeRosa));

        return luzesGeradas;
    }
    public static ArrayList<Plano> gerarPlanos(){
        ArrayList<Plano> planos = new ArrayList<>();
        Plano chao = new Plano(
                    new Vector3(0, -1, -20),          // Ponto Ppl
                    new Vector3(0, 1, 0),           // Vetor normal n
                    -2, 2, -4, 0,               // Limites: xmin, xmax, ymin, ymax
                    new Vector3[]{
                    new Vector3(1.0, 1.0, 1.0),       // k_especular: Textura de madeira (representada como vetor branco para exemplo)
                    new Vector3(1.0, 1.0, 1.0),       // k_difuso: Textura de madeira (mesma representação)
                    new Vector3(1.0, 1.0, 1.0)}       // k_ambiente: Textura de madeira (mesma representação)
                );
        Plano paredeDireita = new Plano(
            new Vector3(2, 0, -20),        // Ponto Ppl
            new Vector3(-1.0, 0, 0),          // Vetor normal n
            -1.5, 1.5, -4, 0,               // Limites: xmin, xmax, ymin, ymax
            new Vector3[]{
                new Vector3(0.686, 0.933, 0.933), // k_difuso: mesma cor
                new Vector3(0.686, 0.933, 0.933), // k_especular: cor azul-claro
                new Vector3(0.686, 0.933, 0.933)}  // k_ambiente: mesma cor
        );
        Plano paredeFrontal = new Plano(
            new Vector3(0, 0, -20),     // Ponto Ppl
            new Vector3(0, 0, -1),           // Vetor normal n
            -2, 2, -1.5, 1.5,             // Limites: xmin, xmax, ymin, ymax
            new Vector3[]{
                new Vector3(0.686, 0.933, 0.933), // k_difuso: mesma cor
                new Vector3(0.686, 0.933, 0.933), // k_especular: cor azul-claro
                new Vector3(0.686, 0.933, 0.933)}  // k_ambiente: mesma cor
        );
        Plano paredeEsquerda = new Plano(
            new Vector3(-2, 0, -20),       // Ponto Ppl
            new Vector3(1, 0, 0),           // Vetor normal n
            -1.5, 1.5, -4, 0,               // Limites: xmin, xmax, ymin, ymax
            new Vector3[]{
                new Vector3(0.686, 0.933, 0.933), // k_difuso: mesma cor
                new Vector3(0.686, 0.933, 0.933), // k_especular: cor azul-claro
                new Vector3(0.686, 0.933, 0.933)}  // k_ambiente: mesma cor
        );
        Plano teto = new Plano(
            new Vector3(0, 2, -20),           // Ponto Ppl
            new Vector3(0, -1.0, 0),          // Vetor normal n
            -2.00, 2.00, -4.00, 0,               // Limites: xmin, xmax, ymin, ymax
            new Vector3[]{
                new Vector3(0.933, 0.933, 0.933), // k_difuso: mesma cor
                new Vector3(0.933, 0.933, 0.933), // k_especular: cor branca
                new Vector3(0.933, 0.933, 0.933)}  // k_ambiente: mesma cor
        );
                
        /* um plano ai 
        double xmin = -2.0;
        double xmax = 2.0;
        double ymin = -2.0;
        double ymax = 2.0;
        Vector3 pontoPlano = new Vector3(0.0, -1, -16); // Ponto no plano
        Vector3 normalPlano = new Vector3(0.0, 2, 1); // Normal para cima
        Color rosa = new Color(255, (int)(255 * 0.5), (int)(255 * 0.7));
        Plano plano = new Plano(pontoPlano, normalPlano, rosa, xmin, xmax, ymin, ymax);
        */

        //planos.add(plano);
        planos.add(paredeEsquerda);
        planos.add(chao);
        planos.add(paredeFrontal);
        planos.add(paredeDireita);
        planos.add(teto);
        
        return planos;
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
    public ArrayList<Esfera> gerarEsferasAleatorias(int quantidade) {
        ArrayList<Esfera> esferas = new ArrayList<>();

        // Definindo limites para as posições aleatórias (você pode ajustar conforme necessário)
        double limitePosicao = 3; // Limite máximo para as coordenadas x, y e z
        double raioEsfera = 1.0; // Raio fixo para as esferas

        for (int i = 0; i < quantidade; i++) {
            // Gerando posições aleatórias para x, y, z
            double x = this.random.nextDouble() * 2 * limitePosicao - limitePosicao; // Posição aleatória entre -limitePosicao e limitePosicao
            double y = this.random.nextDouble() * 2 * limitePosicao - limitePosicao;
            double z = -10 - (5 * this.random.nextDouble());

            int r = this.random.nextInt(256);  // Valor aleatório entre 0 e 255
            int g = this.random.nextInt(256);  // Valor aleatório entre 0 e 255
            int b = this.random.nextInt(256);  // Valor aleatório entre 0 e 255
    
            // Criando a esfera com a posição e o material gerado
            Esfera esfera = new Esfera(raioEsfera, new Vector3(x, y, z), new Color(r,g,b)); 

            // Adicionando a esfera à lista
            esferas.add(esfera);
        }

        return esferas;
    }

    public ArrayList<Cilindro> gerarCilindros(){
        ArrayList<Cilindro> cilindros = new ArrayList<>();
        /* TENTATIVA DE CILINDRO
        Vector3 centroBase = new Vector3(0, 0, -10); // Centro da base do cilindro
        double raio = 0.5;                         // Raio da base do cilindro
        double altura = 1.0;                       // Altura do cilindro
        Vector3 k_difuso = new Vector3(0.8, 0.3, 0.3);    // Reflexão difusa (cor)
        Vector3 k_especular = new Vector3(0.5, 0.5, 0.5); // Reflexão especular
        Vector3 k_ambiente = new Vector3(0.2, 0.2, 0.2);  // Reflexão ambiente
        Criando o cilindro
        Cilindro cilindro = new Cilindro(centroBase, raio, altura, k_difuso, k_especular, k_ambiente);
        */
    
        return cilindros;
    }
   
}