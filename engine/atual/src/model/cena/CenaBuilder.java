package src.model.cena;

import java.util.ArrayList;
import java.util.Random;
import src.model.interseccao.*;
import src.model.materiais.*;
import src.model.objetos.*;

public class CenaBuilder {
    private MateriaisPadrao materiais;
    private Random random;

    public CenaBuilder() {
        this.materiais = MateriaisPadrao.getInstance();
        this.random = new Random();
    }

    public Malha criarMalhasPadrao(){
        ArrayList<Vector3> vertices = new ArrayList<>();
        ArrayList<Aresta> arestas = new ArrayList<>();
        ArrayList<Triangulo> faces = new ArrayList<>();
        // Definindo os vértices do cubo
        Vector3 v1 = new Vector3(0, 0, 0);
        Vector3 v2 = new Vector3(1, 0, 0);
        Vector3 v3 = new Vector3(1, 1, 0);
        Vector3 v4 = new Vector3(0, 1, 0);
        Vector3 v5 = new Vector3(0, 0, -1);
        Vector3 v6 = new Vector3(1, 0, -1);
        Vector3 v7 = new Vector3(1, 1, -1);
        Vector3 v8 = new Vector3(0, 1, -1);

        // Adicionando os vértices à lista
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
        vertices.add(v5);
        vertices.add(v6);
        vertices.add(v7);
        vertices.add(v8);

        // Definindo as arestas do cubo
        arestas.add(new Aresta(v1, v2));
        arestas.add(new Aresta(v2, v3));
        arestas.add(new Aresta(v3, v4));
        arestas.add(new Aresta(v4, v1));
        arestas.add(new Aresta(v5, v6));
        arestas.add(new Aresta(v6, v7));
        arestas.add(new Aresta(v7, v8));
        arestas.add(new Aresta(v8, v5));
        arestas.add(new Aresta(v1, v5));
        arestas.add(new Aresta(v2, v6));
        arestas.add(new Aresta(v3, v7));
        arestas.add(new Aresta(v4, v8));

        // Definindo as faces do cubo
        faces.add(new Triangulo(v1, v2, v3, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v1, v3, v4, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v5, v6, v7, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v5, v7, v8, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v1, v2, v6, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v1, v6, v5, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v2, v3, v7, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v2, v7, v6, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v3, v4, v8, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v3, v8, v7, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v4, v1, v5, materiais.PLASTICO_BRILHANTE));
        faces.add(new Triangulo(v4, v5, v8, materiais.PLASTICO_BRILHANTE));
        Malha malha = new Malha(vertices, arestas, faces);
        return malha;
    }

    public ArrayList<Esfera> criarEsferasPadrao() {
        ArrayList<Esfera> esferas = new ArrayList<>();

        esferas.add(new Esfera(0.5, new Vector3(0, 0, -10), materiais.OBSIDIANA));
        esferas.add(new Esfera(1.0, new Vector3(0, 0, -15), materiais.MADEIRA));
        esferas.add(new Esfera(1.0, new Vector3(2, 0, -15), materiais.VIDRO));
        //esferas.add(new Esfera(0.2, new Vector3(0, 1.2, -8), materiais.getMaterialAleatorio()));

        return esferas;
    }

    public ArrayList<Esfera> criarEsferasAleatorias(int quantidade) {
        ArrayList<Esfera> esferas = new ArrayList<>();
        double limitePosicao = 3.0;
        double raioEsfera = 1.0;

        for (int i = 0; i < quantidade; i++) {
            double x = random.nextDouble() * 2 * limitePosicao - limitePosicao;
            double y = random.nextDouble() * 2 * limitePosicao - limitePosicao;
            double z = -10 - (5 * random.nextDouble());

            Esfera esfera = new Esfera(raioEsfera, new Vector3(x, y, z), materiais.getMaterialAleatorio());
            esferas.add(esfera);
        }

        return esferas;
    }

    public ArrayList<Plano> criarPlanosPadrao() {
        ArrayList<Plano> planos = new ArrayList<>();

        planos.add(new Plano(new Vector3(0, -1, 0), new Vector3(0, 1.0, 0), materiais.MADEIRA_ENVELHECIDA));
        planos.add(new Plano(new Vector3(2, 0, 0), new Vector3(-1, 0, 0), materiais.METALICO));
        planos.add(new Plano(new Vector3(0, 0, -20), new Vector3(0, 0, 1), materiais.MADEIRA_ENVELHECIDA));
        planos.add(new Plano(new Vector3(-2, 0, 0),  new Vector3(1, 0, 0), materiais.METALICO));
        planos.add(new Plano(new Vector3(0, 2, 0), new Vector3(0, -1, 0), materiais.MADEIRA_ENVELHECIDA));

        return planos;
    }
    public ArrayList<Cone> criarConesPadrao(){
        ArrayList<Cone> cones = new ArrayList<>();
        Cone cone = new Cone(new Vector3(1, 1, -8), new Vector3(0, 1, 1), 0.2, 0.1, materiais.METALICO);
        cones.add(cone);
        return cones;
    }
    public ArrayList<Cilindro> criarCilindrosPadrao() {
        ArrayList<Cilindro> cilindros = new ArrayList<>();

        Vector3 eixo = new Vector3(0, 0, -1);
        cilindros.add(new Cilindro(new Vector3(0, 0, -10), 0.2, 0.5, eixo, materiais.COBRE));

        return cilindros;
    }

    public ArrayList<Light> criarLuzesPadrao() {
        ArrayList<Light> luzes = new ArrayList<>();
        Vector3 intensidade = new Vector3(0.8, 0.8, 0.8);

        luzes.add(new Light(new Vector3(0, 1.8, -6), intensidade));

        return luzes;
    }

    public ArrayList<Triangulo> criarTriangulosPadrao() {
        ArrayList<Triangulo> triangulos = new ArrayList<>();
        Vector3 v1 = new Vector3(0, 0, -10);
        Vector3 v2 = new Vector3(1, 0, -10);
        Vector3 v3 = new Vector3(0, 1, -10);
        Triangulo triangulo = new Triangulo(v1, v2, v3, materiais.PLASTICO_BRILHANTE);
        triangulos.add(triangulo);
        return triangulos;
    }
}
