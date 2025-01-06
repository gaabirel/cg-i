package src.model.objetos;

import java.awt.Color;

import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public interface Intersectable {
    Intersection intersect(Ray ray);
    Vector3 calcularNormal(Vector3 ponto);
    Vector3 getKdifuso();
    Vector3 getKespecular();
    Vector3 getKambiente();
    double getBrilho();
    Material getMaterial();
    void setCor(Color cor);
    void setMaterial(Material material);
    
    void transladar(double dx, double dy, double dz);
    void rotacionar(double angulo, Vector3 axis);
}
