package src.model;
import java.awt.Color;

public class Plano extends Objeto3D implements Intersectable  {

    private Vector3 Ppl;
    private Vector3 N;
    private double distance;  // Pra calcular o limite do plano
    private double ymax, ymin, xmax, xmin;
    public Plano(Vector3 Ppl, Vector3 N, Color color, double distance) {
        this.Ppl = Ppl;
        this.N = N.normalize(); //normalizamos para garantir que a normal seja unitária
        this.distance = distance;

        double r = color.getRed()/255.0;
        double g = color.getGreen()/255.0;
        double b = color.getBlue()/255.0;
        this.k_difuso = new Vector3(r, g, b);

        this.k_especular = new Vector3(0.2, 0.2, 0.2);
        this.k_ambiente  = new Vector3(0.3, 0.3, 0.3);
        this.xmin = Ppl.x - distance;
        this.xmax = Ppl.x + distance;
        this.ymin = Ppl.y - distance;
        this.ymax = Ppl.y + distance;

    }

    public Plano(Vector3 Ppl, Vector3 N, double distance, Vector3[] k_iluminacao){
        this(Ppl, N, new Color(0, 0, 0), distance);
        this.setKdifuso(k_iluminacao[0]);
        this.setKespecular(k_iluminacao[1]);
        this.setKambiente(k_iluminacao[2]);
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

        //Verifica se o ponto de interseção está dentro dos limites do plano
        /*  Meio bugado
        if (pontoInterseccao.x >= xmin && pontoInterseccao.x <= xmax && 
            pontoInterseccao.y >= ymin && pontoInterseccao.y <= ymax) {
            // Se estiver dentro dos limites, retorna a interseção
            return new Intersection(pontoInterseccao, t);
        }
        
        //Se o ponto não estiver dentro dos limites, não há interseção
        return null;
        */
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
        this.xmin += dx;
        this.xmax += dx;
        this.ymin += dy;
        this.ymax += dy;
        this.Ppl = new Vector3(Ppl.x, Ppl.y, Ppl.z + dz);
    }




}
