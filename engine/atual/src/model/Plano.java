package src.model;
import java.awt.Color;

public class Plano implements Intersectable {

    private Vector3 Ppl;
    private Vector3 N;
    private Color color;
    private double xmin, xmax, ymin, ymax;  // Limites do plano no espaço 2D (X, Y)
    private Vector3 k_difuso;
    private Vector3 k_especular;

    public Plano(Vector3 Ppl, Vector3 N, Color color, double xmin, double xmax, double ymin, double ymax) {
        this.Ppl = Ppl;
        this.N = N.normalize(); //normalizamos para garantir que a normal seja unitária
        this.color = color;
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        double r = color.getRed()/255.0;
        double g = color.getGreen()/255.0;
        double b = color.getBlue()/255.0;
        this.k_especular = new Vector3(0.2, 0.2, 0.2);
        this.k_difuso = new Vector3(r, g, b);
    }

    @Override
    public Intersection intersect(Ray ray) {
        // Cálculo da interseção com o plano
        double t = this.calcularInterseccao(ray);

        // Ponto de interseção
        Vector3 pontoInterseccao = ray.origin.add(ray.direction.multiply(t));

        // Verifica se o ponto de interseção está dentro dos limites do plano
        if (pontoInterseccao.x >= xmin && pontoInterseccao.x <= xmax && 
            pontoInterseccao.y >= ymin && pontoInterseccao.y <= ymax) {
            // Se estiver dentro dos limites, retorna a interseção
            return new Intersection(pontoInterseccao, t);
        }

        // Se o ponto não estiver dentro dos limites, não há interseção
        return null;
    }
    public double calcularInterseccao(Ray ray) {
        double denom = N.dot(ray.direction);
        if (denom == 0) {
            return Double.POSITIVE_INFINITY; // Não há interseção
        }

        double t = N.dot(Ppl.subtract(ray.origin)) / denom;
        return t;
    }

    @Override
    public int getColor() {
        return color.getRGB();
    }

    @Override
    public Vector3 calcularNormal(Vector3 ponto) {
        return this.N.normalize();
    }

    @Override
    public void mover(double dx, double dy, double dz) {
        this.xmin += dx;
        this.xmax += dx;
        this.ymin += dy;
        this.ymax += dy;
        this.Ppl = new Vector3(Ppl.x, Ppl.y, Ppl.z + dz);
    }

    @Override
    public Vector3 getKdifuso() {
        return this.k_difuso;
    }

    @Override
    public Vector3 getKespecular() {
        return this.k_especular;
    }
}
