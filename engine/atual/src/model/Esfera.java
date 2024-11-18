package src.model;
import java.awt.Color;

public class Esfera extends Objeto3D implements Intersectable {
    
    private double radius; // raio da esfera
    private Vector3 center; //centro da esfera

    public Esfera(double radius, Vector3 center, Vector3[] k_iluminacao) {
        this.radius = radius;
        this.center = center;
        this.setKdifuso(k_iluminacao[0]);
        this.setKespecular(k_iluminacao[1]);
        this.setKambiente(k_iluminacao[2]);
    }

    //construtor para caso só queira passar uma cor sem definir os coeficientes de reflexao
    public Esfera(double radius, Vector3 center, Color colorDifuso) {
        //chamando o 1°construtor 
        this( 
            radius, 
            center,new Vector3[]{
                new Vector3(colorDifuso.getRed() / 255.0, colorDifuso.getGreen() / 255.0, colorDifuso.getBlue() / 255.0),  //k_difuso a partir da cor
                new Vector3(0.2, 0.2, 0.2),  //Valor padrão para k_especular
                new Vector3(0.3, 0.3, 0.3)}  //Valor padrão para k_ambiente
        );
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
    public Vector3 calcularNormal(Vector3 ponto) {
        // Normal da esfera: vetor do centro até o ponto de interseção, normalizado
        return ponto.subtract(center).normalize();
    }

    

}
