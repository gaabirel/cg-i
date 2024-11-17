package src.model;
public interface Intersectable {
    Intersection intersect(Ray ray);
    Vector3 getKdifuso();
    Vector3 getKespecular();
    Vector3 getKambiente();
    Vector3 calcularNormal(Vector3 ponto);
    void mover(double dx, double dy, double dz);
}
