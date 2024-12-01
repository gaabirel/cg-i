package src.model.interseccao;

import java.awt.Color;

import src.model.materiais.Material;

public interface Intersectable {
    Intersection intersect(Ray ray);
    Vector3 getKdifuso();
    Vector3 getKespecular();
    Vector3 getKambiente();
    double getBrilho();
    Material getMaterial();
    void setCor(Color cor);
    void setMaterial(Material material);
    Vector3 calcularNormal(Vector3 ponto);
    void mover(double dx, double dy, double dz);
}
