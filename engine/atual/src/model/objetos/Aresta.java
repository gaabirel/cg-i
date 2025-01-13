package src.model.objetos;

import src.model.interseccao.Vector3;

public class Aresta {
    Vector3 inicio;
    Vector3 fim;

    public Aresta(Vector3 inicio, Vector3 fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public Vector3 getInicio() {
        return inicio;
    }

    public Vector3 getFim() {
        return fim;
    }
}