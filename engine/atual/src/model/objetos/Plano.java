package src.model.objetos;
import java.awt.Color;

import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;
import src.config.Config;

public class Plano extends Objeto3D implements Intersectable  {

    private Vector3 Ppl;
    private Vector3 N;

    //Valores padrão para os coeficientes de iluminação
    private static final Vector3 DEFAULT_K_ESPECULAR = new Vector3(0.2, 0.2, 0.2);
    private static final Vector3 DEFAULT_K_AMBIENTE = new Vector3(0.9, 0.9, 0.9);

    public Plano(Vector3 Ppl, Vector3 N, Color colorDifuso) {
        this.Ppl = Ppl;
        this.N = N.normalize();
       setMaterial(new Material(colorToVector(colorDifuso), DEFAULT_K_ESPECULAR, DEFAULT_K_AMBIENTE));
    }

    public Plano(Vector3 Ppl, Vector3 N, Material material){
        this(Ppl, N, new Color(0, 0, 0));
        setMaterial(material);
    }   

    @Override
    public Intersection intersect(Ray ray) {
        double denom = N.dot(ray.direction);
        if (Math.abs(denom) > 1e-6) { // Verifica se o raio não é paralelo ao plano
            Vector3 p0l0 = Ppl.subtract(ray.origin);
            double t = p0l0.dot(N) / denom;
            if (t >= 1e-5) { // Usamos epsilon para evitar interseções muito próximas
                Vector3 pontoInterseccao = ray.getPoint(t);
                return new Intersection(pontoInterseccao, t);
            }
        }
        return null; // Não há interseção 
    }

    @Override
    public Vector3 calcularNormal(Vector3 ponto) {
        return this.N;
    }

    @Override
    public void transladar(double dx, double dy, double dz) {
        Ppl.add(new Vector3(Ppl.getX() + dx, Ppl.getY() + dy, Ppl.getZ() + dz));
    }

    @Override
    public void rotacionar(double angleDegrees, Vector3 axis) {
        this.Ppl = this.Ppl.rotate(angleDegrees, axis);
        this.N = this.N.rotate(angleDegrees, axis);
    }

    @Override
    public String toString() {
        return "Plano {" +
                "Ppl=" + this.Ppl +
                ", normal=" + this.N+
                ", material=" + material +
                '}';
    }

}
