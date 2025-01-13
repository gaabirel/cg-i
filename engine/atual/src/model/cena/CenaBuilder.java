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

    public ArrayList<Esfera> criarEsferasPadrao() {
        ArrayList<Esfera> esferas = new ArrayList<>();

        esferas.add(new Esfera(0.5, new Vector3(0, 0, -10), materiais.OBSIDIANA));
        esferas.add(new Esfera(1.0, new Vector3(0, 0, -15), materiais.MADEIRA));
        esferas.add(new Esfera(1.0, new Vector3(2, 0, -15), materiais.VIDRO));
        //esferas.add(new Esfera(0.2, new Vector3(0, 1.2, -8), materiais.getMaterialAleatorio()));

        return esferas;
    }

    public ArrayList<Malha> criarMalha(){
        ArrayList<Malha> malhas = new ArrayList<>();
        Vector3 v0 = new Vector3(0, 0, -8);
        Vector3 v1 = new Vector3(1, 0, -8);
        Vector3 v2 = new Vector3(0, 1, -8);

        Vector3 v3 = new Vector3(-0.5, 0, -10);

        // Criar arestas
        Aresta a1 = new Aresta(v0, v1);
        Aresta a2 = new Aresta(v1, v2);
        Aresta a3 = new Aresta(v2, v0);
        Aresta a4 = new Aresta(v0, v3);

        // Criar face a partir das arestas
        Face face = new Face(a1, a2, a3);
        Face face2 = new Face(a2, a3, a4);

        // Criar malha
        Malha malha = new Malha(
            new Vector3[] { v0, v1, v2, v3 },
            new Aresta[] { a1, a2, a3, a4 },
            new Face[] { face,  face2 },
            materiais.PLASTICO_BRILHANTE
        );

        malhas.add(malha);

        return malhas;
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
        Cone cone = new Cone(new Vector3(1, 1, -8), new Vector3(0, 1, 0), 0.2, 0.3, materiais.METALICO);
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
