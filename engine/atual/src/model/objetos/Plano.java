package src.model.objetos;
import java.awt.Color;

import src.model.interseccao.Intersectable;
import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public class Plano extends Objeto3D implements Intersectable  {

    private Vector3 Ppl;
    private Vector3 N;

    //Valores padrão para os coeficientes de iluminação
    private static final Vector3 DEFAULT_K_ESPECULAR = new Vector3(0.2, 0.2, 0.2);
    private static final Vector3 DEFAULT_K_AMBIENTE = new Vector3(0.9, 0.9, 0.9);

    public Plano(Vector3 Ppl, Vector3 N, Color colorDifuso) {
        this.Ppl = Ppl;
        this.N = N.normalize(); //normalizamos para garantir que a normal seja unitária


       setMaterial(new Material(colorToVector(colorDifuso), DEFAULT_K_ESPECULAR, DEFAULT_K_AMBIENTE));

    }

    public Plano(Vector3 Ppl, Vector3 N, Material material){
        this(Ppl, N, new Color(0, 0, 0));
        setMaterial(material);
    }   

    @Override
    public Intersection intersect(Ray ray) {
        // Cálculo da interseção com o plano
        double t = this.calcularInterseccao(ray); //sem interseccao ou plano atras do pintor
        if ((t == Double.POSITIVE_INFINITY) || t <= 0){
            return null;
        }
        //Ponto de interseção
        Vector3 pontoInterseccao = ray.origin.add(ray.direction.multiply(t));

        return new Intersection(pontoInterseccao, t);
        
    }
    public double calcularInterseccao(Ray ray) {
        double denom = N.dot(ray.direction);
        if (denom == 0) {
            return Double.POSITIVE_INFINITY; //Não há interseção
        }

        double t = N.dot(Ppl.subtract(ray.origin)) / denom;
        return t;
    }

    @Override
    public Vector3 calcularNormal(Vector3 ponto) {
        return this.N;
    }

    @Override
    public void mover(double dx, double dy, double dz) {
        this.Ppl = new Vector3(Ppl.x, Ppl.y, Ppl.z + dz);
    }




}
