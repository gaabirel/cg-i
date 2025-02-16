package src.model.objetos;

import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public class Esfera extends Objeto3D implements Intersectable {

    private double radius;    //Raio da esfera
    private Vector3 center;   //Centro da esfera
    private double squareRadius; //Raio ao quadrado

    public Esfera(double radius, Vector3 center, Material material) {
        this.radius = radius;
        this.center = center;
        this.squareRadius = radius * radius;
        setMaterial(material);
    }

    @Override
    public Intersection intersect(Ray ray) {
        Vector3 oc = ray.origin.subtract(center);
        double a = ray.direction.dot(ray.direction);
        double b = 2.0 * oc.dot(ray.direction);
        double c = oc.dot(oc) - squareRadius;
        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) return null; //Sem interseção

        double t = calcularMenorRaiz(a, b, discriminant);
        if (t == Double.MAX_VALUE) return null; //Raízes negativas ou inexistentes

        Vector3 point = ray.origin.add(ray.direction.multiply(t));
        return new Intersection(point, t);
    }


    @Override
    public Vector3 calcularNormal(Vector3 ponto) {
        return ponto.subtract(center).normalize();
    }

    @Override
    public void transladar(double dx, double dy, double dz) {
        this.center = this.center.add(new Vector3(dx, dy, dz));
    }

    @Override
    public void escala(double sx, double sy, double sz) {
        //a escala tem que ser uniforme, pq se nao for não faz sentido
        if (sx != sy || sy != sz) {
            throw new IllegalArgumentException("Escala nao-uniforme não eh suportada para esferas.");
        }
        this.radius *= sx;
        this.squareRadius = this.radius * this.radius;
    }

    @Override
    public void rotacionar(double angleDegrees, Vector3 axis) {
        throw new UnsupportedOperationException("Rotacao nao eh suportada para esferas.");
        // Rotaciona o centro da esfera
        //this.center = this.center.rotate(angleDegrees, axis);
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


    @Override
    public String toString() {
        return "esfera {" +
                "centro=" + this.center +
                ", raio=" + this.radius +
                ", material=" + this.material +
                '}';
    }

   

 
}