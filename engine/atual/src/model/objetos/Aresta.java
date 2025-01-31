package src.model.objetos;

import src.model.interseccao.Vector3;

public class Aresta {
    private Vector3 v1, v2;

    public Aresta(Vector3 v1, Vector3 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vector3 getV1() {
        return v1;
    }

    public Vector3 getV2() {
        return v2;
    }
}
