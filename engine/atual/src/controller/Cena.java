package src.controller;

import java.util.ArrayList;
import java.util.Random;
import src.model.interseccao.*;
import src.model.materiais.*;
import src.model.objetos.*;

public class Cena {
    private ArrayList<Intersectable> objetosCena;
    private ArrayList<Light> luzes;
    private MateriaisPadrao materiais;
    private Random random;
  
    public Cena() {
        this.materiais = new MateriaisPadrao();
        this.random = new Random();
        this.objetosCena = new ArrayList<>();
        
        //gera as luzes da cena
        luzes = gerarLuzes();

        //gerando os vetores de objetos
        ArrayList<Esfera> esferas = gerarEsferas();
        ArrayList<Plano> planos = gerarPlanos();

        //adicionando + esferas aleatorias
        esferas.addAll(gerarEsferasAleatorias(1));

        //Cone cone = new Cone(new Vector3(0, 0, -10), new Vector3(0, 0, 1), 1.5, 1, material);
        //adicionar os objetos para a lista de objetos da cena

        // Criação do cilindro
        Vector3 eixo = new Vector3(0, 0, -1);
        Cilindro cilindro = new Cilindro(new Vector3(0, 0, -10), 0.2, 0.5, eixo, materiais.COBRE);
        objetosCena.add(cilindro);
        //objetosCena.add(cone);
        //objetosCena.add(cilindro); //em construção

        Vector3 v1 = new Vector3(0, 0, -10);
        Vector3 v2 = new Vector3(1, 0, -10);
        Vector3 v3 = new Vector3(0, 1, -10);

        // Criar triângulo com iluminação
        Triangulo triangulo = new Triangulo(v1, v2, v3, materiais.PLASTICO_BRILHANTE);
        objetosCena.add(triangulo);
        objetosCena.addAll(esferas);
        objetosCena.addAll(planos);
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

        luzesGeradas.add(new Light(new Vector3(0, 2, -8) , intensidade));
        //luzes.add(new Light(new Vector3(0, 3, -15) , intensidadeRosa));
        //luzes.add(new Light(new Vector3(0, 2, -4), intensidadeRosa));

        return luzesGeradas;
    }
    public ArrayList<Plano> gerarPlanos(){
        ArrayList<Plano> planos = new ArrayList<>();
        Plano chao = new Plano(
            new Vector3(0, -1, -20),          // Ponto Ppl
            new Vector3(0, 1, 0),             // Vetor normal n
            materiais.MADEIRA_ENVELHECIDA
        );

        Plano paredeDireita = new Plano(
            new Vector3(2, 0, -20),           // Ponto Ppl
            new Vector3(-1.0, 0, 0),          // Vetor normal n
            materiais.MADEIRA_ENVELHECIDA
        );

        Plano paredeFrontal = new Plano(
            new Vector3(0, 0, -20),           // Ponto Ppl
            new Vector3(0, 0, -1),            // Vetor normal n
            materiais.MADEIRA_ENVELHECIDA
        );

        Plano paredeEsquerda = new Plano(
            new Vector3(-2, 0, -20),          // Ponto Ppl
            new Vector3(1, 0, 0),             // Vetor normal n
            materiais.MADEIRA_ENVELHECIDA
        );

        Plano teto = new Plano(
            new Vector3(0, 2, -20),           // Ponto Ppl
            new Vector3(0, -1.0, 0),          // Vetor normal n
            materiais.MADEIRA_ENVELHECIDA
        );

        planos.add(paredeEsquerda);
        planos.add(chao);
        planos.add(paredeFrontal);
        planos.add(paredeDireita);
        planos.add(teto);
        
        return planos;
    }
    public ArrayList<Esfera> gerarEsferas(){
        ArrayList<Esfera> esferas = new ArrayList<>();

        // Criando esferas
        esferas.add(new Esfera(0.5, new Vector3(0, 0, -10), materiais.OBSIDIANA));
        esferas.add(new Esfera(1.0, new Vector3(0, 0, -15), materiais.MADEIRA));
        esferas.add(new Esfera(1.0, new Vector3(2, 0, -15), materiais.VIDRO));
        esferas.add(new Esfera(0.2, new Vector3(0, 1.2, -8), materiais.getMaterialAleatorio()));
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

            // Criando a esfera com a posição e o material gerado
            Esfera esfera = new Esfera(raioEsfera, new Vector3(x, y, z), materiais.getMaterialAleatorio()); 

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