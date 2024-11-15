package src.model;
import java.awt.Color;

public class Esfera implements Intersectable{
    
    private double radius; // raio da esfera
    private Vector3 center; //centro da esfera
    private Color color; // cor da esfera

    private Vector3 k_especular;
    private Vector3 k_difuso;

    public Esfera(double radius, Vector3 center, Color color, Vector3 k_especular, Vector3 k_difuso) {
        this.radius = radius;
        this.color = color;
        this.center = center;
        this.k_especular = k_especular;
        this.k_difuso = k_difuso;
    }
    public Esfera(double radius, Vector3 center, Color color) {
        this.radius = radius;
        this.color = color;
        this.center = center;
        double r = color.getRed()/255.0;
        double g = color.getGreen()/255.0;
        double b = color.getBlue()/255.0;
        this.k_especular = new Vector3(0.2, 0.2, 0.2);
        this.k_difuso = new Vector3(r, g, b);
    }

    @Override
    public Intersection intersect(Ray ray) {
        //Fórmula matemática para interseção de um raio com uma esfera
        Vector3 oc = ray.origin.subtract(center);
        double a = ray.direction.dot(ray.direction);
        double b = 2.0 * oc.dot(ray.direction);
        double c = oc.dot(oc) - radius * radius;
        double discriminant = b * b - 4 * a * c;
        if (discriminant >= 0) {
           
            double raizDiscriminante = Math.sqrt(discriminant);
            a *= 2;
            double t1 = (-b + raizDiscriminante) / a;
            double t2 = (-b - raizDiscriminante) / a;

            //t = (t1 > 0 && t2 > 0) ? Math.min(t1, t2) : (t1 > 0 ? t1 : t2);
            double t = Math.min(t1, t2); // o que considero mais simples
            
            Vector3 point = ray.origin.add(ray.direction.multiply(t));
            return new Intersection(point, t);
        }
        else{
            return null;
        }
    }
    @Override
    public void mover(double dx, double dy, double dz) {
        this.center.x += dx;
        this.center.y += dy;
        this.center.z += dz;
    }

    public double getR() {
        return radius;
    }

    public void setR(double radius) {
        this.radius = radius;
    }

    public Vector3 getCenter() {
        return center;
    }
    public void setX(double x) {
        this.center.x = x;
    }
    public void setY(double y) {
        this.center.y = y;
    }
    public void setZ(double z) {
        this.center.z = z;
    }
    public double getX() {
        return this.center.x;
    }
    public double getY() {
        return this.center.y;
    }
    public double getZ() {
        return this.center.z;
    }

    @Override
    public int getColor() {
        return color.getRGB();
    }

    public void setcolor(Color color) {
        this.color = color;
    }

    public Vector3 getKdifuso(){
        return this.k_difuso;
    }
    
    public Vector3 getKespecular(){
        return this.k_especular;
    }

    public void setKdifuso(Vector3 novoK){
        this.k_difuso = novoK;
    }

    public void setKespecular(Vector3 novoK){
        this.k_especular = novoK;
    }

    @Override
    public Vector3 calcularNormal(Vector3 ponto) {
        // Normal da esfera: vetor do centro até o ponto de interseção, normalizado
        return ponto.subtract(center).normalize();
    }

    

}
