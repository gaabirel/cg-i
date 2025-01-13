package src.model.objetos;

import src.model.interseccao.Vector3;

public class Face {
    Aresta[] arestas;

    public Face(Aresta a1, Aresta a2, Aresta a3) {
        this.arestas = new Aresta[] { a1, a2, a3 };
    }

    public Vector3[] getVertices() {
        // Extrair os vértices únicos das arestas
        return new Vector3[] { arestas[0].getInicio(), arestas[1].getInicio(), arestas[2].getInicio() };
    }
}