package src.model.objetos;

import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public class Cone extends Objeto3D implements Intersectable {
    private Vector3 vertice;    // Vértice do cone
    private Vector3 eixo;       // Eixo do cone (deve ser normalizado)
    private double altura;      // Altura do cone
    private double raioBase;    // Raio da base do cone

    public Cone(Vector3 vertice, Vector3 eixo, double altura, double raioBase,  Material material) {
        this.vertice = vertice;
        this.eixo = eixo.normalize();
        this.altura = altura;
        this.raioBase = raioBase;
        setMaterial(material);
    }

    public Intersection intersect(Ray ray) {
        Vector3 v = this.eixo;
        double cos2Theta = Math.pow(altura / Math.sqrt(altura * altura + raioBase * raioBase), 2);
        Vector3 d = ray.direction;

        double vDotD = v.dot(d);

        double a = vDotD * vDotD - d.dot(d) * cos2Theta;
        double b = 2 * (vertice.dot(d) * cos2Theta - vertice.dot(v) * vDotD);
        double c = vertice.dot(v) * vertice.dot(v) - vertice.dot(vertice) * cos2Theta;
    
        //delta (discriminante) da equação quadrática
        double discriminante = b * b - 4 * a * c;
    
        if (discriminante < 0) {
            //nao ha interseção
            return null;
        }
    
        //calculando as duas raízes da equação quadrática
        double sqrtDelta = Math.sqrt(discriminante);
        double A2 = 2 * a;
        double t1 = (-b - sqrtDelta) / (A2);
        double t2 = (-b + sqrtDelta) / (A2);
    
        //ponto atras da origem do raio
        if(t1 < 0 && t2 < 0) return null;

        //calcula o ponto mais proximo
        double t = Math.min(t1 > 0 ? t1 : Double.POSITIVE_INFINITY, t2 > 0 ? t2 : Double.POSITIVE_INFINITY);
        if(t == Double.POSITIVE_INFINITY) return null;
        
        Vector3 pontoIntersecao = ray.origin.add(ray.direction.multiply(t));
        
        //verifica se a interseção está dentro dos limites do cone
        double alturaIntersecao = eixo.dot(pontoIntersecao.subtract(vertice));
        if (alturaIntersecao >= 0 && alturaIntersecao <= altura) {
            return new Intersection(pontoIntersecao, t);
        }

        t = Math.max(t1, t2); //como o primeiro não é válido, testando a segunda distancia

        Vector3 pontoIntersecao2 = ray.origin.add(ray.direction.multiply(t));
        double alturaIntersecao2 = eixo.dot(pontoIntersecao2.subtract(vertice));

        // Verifica se a interseção está dentro dos limites do cone
        if (alturaIntersecao2 >= 0 && alturaIntersecao2 <= altura) {
            return new Intersection(pontoIntersecao2, t);
        }
        return null;

    }

    @Override
    public Vector3 calcularNormal(Vector3 pontoIntersecao) {
        Vector3 vetorVerticeParaPonto = vertice.subtract(pontoIntersecao);
        Vector3 eixoCrossVp = vetorVerticeParaPonto.cross(eixo.negate());
        Vector3 normal  = eixoCrossVp.cross(vetorVerticeParaPonto);
        return normal.normalize();
    }

    @Override
    public void rotacionar(double anguloGraus, Vector3 eixoRotacao) {
        //Rotaciona o vetor eixo do cone em torno do eixo de rotação
        this.eixo = this.eixo.rotate(anguloGraus, eixoRotacao).normalize();
    }

    @Override
    public void escala(double sx, double sy, double sz) {
        //Escala uniforme
        if (sx != sy || sy != sz) {
            throw new IllegalArgumentException("Escala não-uniforme não é suportada para cones.");
        }

        this.raioBase *= sx;
        this.altura *= sy;
    }

    @Override
    public void transladar(double dx, double dy, double dz) {
        //Movendo o cilindro alterando o centro da base
        this.vertice = this.vertice.add(new Vector3(dx, dy, dz));
    }

    @Override
    public String toString() {
        return "Cone {" +
                "vertice=" + vertice +
                ", eixo=" + eixo +
                ", altura=" + altura +
                ", raioBase=" + raioBase +
                ", material=" + material +
                '}';
    }

}