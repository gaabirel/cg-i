package src.model.objetos;

import src.model.interseccao.Intersectable;
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
        Vector3 v = this.eixo.negate(); // Vetor do vértice para a base
        double tan2Theta = Math.pow(raioBase / altura, 2); // Tangente ao quadrado do ângulo do cone

        Vector3 deltaP = ray.origin.subtract(vertice);
        Vector3 d = ray.direction;

        double vDotD = v.dot(d);
        double vDotDeltaP = v.dot(deltaP);

        double a = vDotD * vDotD - tan2Theta;
        double b = 2 * (vDotD * vDotDeltaP - d.dot(deltaP) * tan2Theta);
        double c = vDotDeltaP * vDotDeltaP - deltaP.dot(deltaP) * tan2Theta;
    
        // Delta (discriminante) da equação quadrática
        double discriminante = b * b - 4 * a * c;
    
        if (discriminante < 0) {
            // Não há interseção
            return null;
        }
    
        // Calculando as duas raízes da equação quadrática
        double sqrtDelta = Math.sqrt(discriminante);
        double t1 = (-b - sqrtDelta) / (2 * a);
        double t2 = (-b + sqrtDelta) / (2 * a);
    
        // Se t1 ou t2 for negativo, isso significa que a interseção está atrás da origem do raio.
        // Podemos ignorar esses casos.
        if(t1 < 0 && t2 < 0) return null;
        //double t = (t1 > 0 ? t1 : t2);
        double t = Math.min(t1 > 0 ? t1 : Double.POSITIVE_INFINITY, t2 > 0 ? t2 : Double.POSITIVE_INFINITY);
        if(t == Double.POSITIVE_INFINITY) return null;
        Vector3 pontoIntersecao = ray.origin.add(ray.direction.multiply(t));
        double alturaIntersecao = eixo.dot(pontoIntersecao.subtract(vertice));

        // Verifica se a interseção está dentro dos limites do cone
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
        Vector3 vetorVerticeParaPonto = pontoIntersecao.subtract(vertice);
        double alturaProjetada = eixo.dot(vetorVerticeParaPonto);

        // Verifica se está na base
        if (alturaProjetada == altura) {
            return eixo; // Normal da base
        }

        // Caso contrário, está na superfície lateral
        Vector3 pontoProjetadoNoEixo = vertice.add(eixo.multiply(alturaProjetada));
        Vector3 normal = pontoIntersecao.subtract(pontoProjetadoNoEixo);
        return normal.normalize();
    }

    public void alterarEixo(Vector3 novoEixo) {
        if (novoEixo.length() == 0) {
            throw new IllegalArgumentException("O eixo não pode ter comprimento zero.");
        }
        this.eixo = this.eixo.add(novoEixo).normalize();
    }

    @Override
    public String toString() {
        return "Cone {" +
                "vertice=" + vertice +
                ", eixo=" + eixo +
                ", altura=" + altura +
                ", raioBase=" + raioBase +
                '}';
    }

    @Override
    public void transladar(double dx, double dy, double dz) {
        // Movendo o cilindro alterando o centro da base
        this.vertice = this.vertice.add(new Vector3(dx, dy, dz));
    }
}