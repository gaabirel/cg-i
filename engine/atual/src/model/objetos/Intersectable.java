package src.model.objetos;

import java.awt.Color;

import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public interface Intersectable {
    Intersectable aplicarMatrixCamera(double[][] matrix);
    Intersection intersect(Ray ray, double[][] matrixCamera);
    Vector3 calcularNormal(Vector3 ponto, double[][] matrixCamera);
    Vector3 getKdifuso();
    Vector3 getKespecular();
    Vector3 getKambiente();
    double getBrilho();
    Material getMaterial();
    void setCor(Color cor);
    void setMaterial(Material material);
    
    void transladar(double dx, double dy, double dz);
    void rotacionar(double angulo, Vector3 axis);
    void escala(double sx, double sy, double sz);
    void cisalhar(double shXY, double shXZ, double shYX, double shYZ, double shZX, double shZY);
    void espelhar(String eixo);

}
