package src.model.objetos;

import src.model.interseccao.Intersectable;
import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public class Esfera extends Objeto3D implements Intersectable {

    private double radius;    // Raio da esfera
    private Vector3 center;   // Centro da esfera

    /**
     *Construtor principal para a classe Esfera.
     */
    public Esfera(double radius, Vector3 center, Material material) {
        this.radius = radius;
        this.center = center;
        setMaterial(material);
    }

    /**
     *Calcula a interseção de um raio com a esfera.
     */
    @Override
    public Intersection intersect(Ray ray) {
        Vector3 oc = ray.origin.subtract(center);
        double a = ray.direction.dot(ray.direction);
        double b = 2.0 * oc.dot(ray.direction);
        double c = oc.dot(oc) - radius * radius;
        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) return null; //Sem interseção

        double t = calcularMenorRaiz(a, b, discriminant);
        if (t == Double.MAX_VALUE) return null; //Raízes negativas ou inexistentes

        Vector3 point = ray.origin.add(ray.direction.multiply(t));
        return new Intersection(point, t);
    }

    /**
     *Calcula a menor raiz positiva da equação quadrática.
     */
    private double calcularMenorRaiz(double a, double b, double discriminant) {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        a *= 2; // Otimização, evitando divisão repetida
        double t1 = (-b + sqrtDiscriminant) / a;
        double t2 = (-b - sqrtDiscriminant) / a;

        if (t1 > 0 && (t2 <= 0 || t1 < t2)) return t1;
        if (t2 > 0) return t2;
        return Double.MAX_VALUE;
    }

    /**
     *Calcula a normal da esfera em um ponto específico.
     */
    @Override
    public Vector3 calcularNormal(Vector3 ponto) {
        return ponto.subtract(center).normalize();
    }

    /**
     *Move a esfera ao longo de um vetor (dx, dy, dz).
     */
    @Override
    public void mover(double dx, double dy, double dz) {
        this.center = this.center.add(new Vector3(dx, dy, dz));
    }

    //Getters e Setters 
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Vector3 getCenter() {
        return center;
    }

    public void setCenter(Vector3 center) {
        this.center = center;
    }
}