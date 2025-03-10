package src.model.objetos;

import java.awt.image.BufferedImage;

import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public class Esfera extends Objeto3D implements Intersectable {

    private double radius;    //Raio da esfera
    private Vector3 center;   //Centro da esfera
    private double squareRadius; //Raio ao quadrado
    private BufferedImage textura;

    public Esfera(double radius, Vector3 center, Material material) {
        this.radius = radius;
        this.center = center;
        this.squareRadius = radius * radius;
        setMaterial(material);
    }
    public Esfera(double radius, Vector3 center, BufferedImage textura) {
        this.radius = radius;
        this.center = center;
        this.squareRadius = radius * radius;
        this.textura = textura;
        setMaterial(new Material(new Vector3(0.1, 0.1, 0.1), new Vector3(0.2, 0.2, 0.2), new Vector3(0.1, 0.1, 0.1)));
    }

    @Override
    public Intersection intersect(Ray ray, double[][] matrizTransformacao) {
        Vector3 center = this.center.multiplyMatrix4x4(matrizTransformacao);

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
    public Vector3 calcularNormal(Vector3 ponto, double[][] matrizTransformacao) {
        return ponto.subtract(center.multiplyMatrix4x4(matrizTransformacao)).normalize();
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
    public Intersectable aplicarMatrixCamera(double[][] matrix){
        Vector3 newCenter = center.multiplyMatrix4x4(matrix);
        Esfera esfera = new Esfera(radius, newCenter, material);
        return esfera;
    }

    @Override
    public String toString() {
        return "esfera {" +
                "centro=" + this.center +
                ", raio=" + this.radius +
                ", material=" + this.material +
                '}';
    }

    @Override
    public int[] getTexturaColor(Vector3 pontoIntersecao) {
        if (textura == null) {
            return new int[]{0, 0, 0};
        }
    
        // Converter ponto de interseção para coordenadas esféricas
        double x = pontoIntersecao.getX() - center.getX();
        double y = pontoIntersecao.getY() - center.getY();
        double z = pontoIntersecao.getZ() - center.getZ();
    
        // Normalizar para a superfície da esfera
        double r = Math.sqrt(x * x + y * y + z * z);
        double theta = Math.atan2(z, x); // Ângulo azimutal
        double phi = Math.acos(y / r);   // Ângulo polar
    
        // Mapear para coordenadas de textura (u, v)
        double u = (theta / (2 * Math.PI)) + 0.5; // Ajusta para o intervalo [0,1]
        double v = phi / Math.PI;                 // Ajusta para o intervalo [0,1]
    
        // Converter para coordenadas de pixel da textura
        int texX = (int) (u * (textura.getWidth() - 1));
        int texY = (int) ((1 - v) * (textura.getHeight() - 1)); // Inverter Y pois (0,0) geralmente está no topo
    
        // Obter a cor do pixel da textura
        int rgb = textura.getRGB(texX, texY);
    
        // Extrair componentes RGB
        int rC = (rgb >> 16) & 0xFF;
        int gC = (rgb >> 8) & 0xFF;
        int bC = rgb & 0xFF;
    
        // Atenuação da cor
        double fator_atenuacao = 0.5;
        return new int[]{
            (int) (rC * fator_atenuacao),
            (int) (gC * fator_atenuacao),
            (int) (bC * fator_atenuacao)
        };
    }

   

 
}