package src.model.objetos;

import src.model.interseccao.Intersectable;
import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public class Cilindro extends Objeto3D implements Intersectable {
    private double raio;           // Raio do cilindro
    private Vector3 centroBase;    // Ponto central da base do cilindro
    private double altura;         // Altura do cilindro
    private Vector3 eixo;        // Vetor unitário que define o eixo do cilindro
    private double squareRadius;

    public Cilindro(Vector3 centroBase, double raio, double altura,
                    Vector3 eixo, Material material) {
        this.centroBase = centroBase;
        this.raio = raio;
        this.altura = altura;
        setMaterial(material);
        this.eixo = eixo.normalize();
        this.squareRadius = raio * raio;
    }

    @Override
    public Intersection intersect(Ray ray) {
        // Vetores de origem e direção do raio
        Vector3 O = ray.origin;  // Ponto de origem do raio
        Vector3 D = ray.direction;  // Direção do raio


        Vector3 W = D.subtract((this.eixo.multiply(D.dot(this.eixo))));

 
        Vector3 OC = O.subtract(centroBase);  // Vetor de origem até o centro da base
        Vector3 V = OC.subtract(this.eixo.multiply(this.eixo.dot(OC)));
        // Definição dos vetores conforme as equações
        double A = W.dot(W);  // Coeficiente A
        double B = 2*(V.dot(W));  // Coeficiente B
        double C = V.dot(V) - squareRadius;  // Coeficiente C

        
        // Calculando o discriminante
        double delta = B * B - 4 * A * C;
        
        if (delta < 0) {
            return null; // Não há interseção
        }
        double delta_sqr = Math.sqrt(delta);
        double Ax2 = 2 * A;
        // Calculando os valores de t para a interseção
        double t1 = (-B - delta_sqr) / Ax2;
        double t2 = (-B + delta_sqr) / Ax2;
        
        //se ambos forem menor que 0 nao possui interseccao, pois esta atras do olho do pintor
        //double t = Math.min(t1 > 0 ? t1 : Double.POSITIVE_INFINITY, t2 > 0 ? t2 : Double.POSITIVE_INFINITY);
        //if(t == Double.POSITIVE_INFINITY) return null;
        double t = Math.min(t1, t2);
        Vector3 pontoInterseccao = ray.getPoint(t);
        
        // Calculando a projeção dos pontos sobre o this.eixo do cilindro
        double distanciaNothisEixo1 = (pontoInterseccao.subtract(centroBase)).dot(this.eixo);
        // Verifica se o primEiro ponto está dentro da altura do cilindro
        if (((distanciaNothisEixo1 >= 0) && (distanciaNothisEixo1 <= altura))) {
            return new Intersection(pontoInterseccao, t);
        }

        /* Verifica se o segundo ponto está dentro da altura do cilindro
        double distanciaNothis.eixo2 = (pontoInterseccao.subtract(centroBase)).dot(this.eixo);
         pra caso nao tenha tampa
        if (((distanciaNothis.eixo2 >= 0) && (distanciaNothis.eixo2 <= altura))) {
            return new Intersection(pontoInterseccao, t2);
        }
            */

        Vector3 centroBase2 = centroBase.add(this.eixo.multiply(altura));  // base do topo do cilindro
        double dem = (this.eixo.dot(D));                                   //denominador dos calculos

        double d = (this.eixo.dot(centroBase) / dem);                      //distancia a base inferior 
        double d2 = (this.eixo.dot(centroBase2) / dem);                    //distancia a base superior
        Intersection interseccaoBase = null;
        Intersection interseccaoTampa = null;

        // Verifica interseção com a base inferior
        if (d < d2 && d > 0) { // Garante que está na direção do raio
            Vector3 Qp1 = D.multiply(d).subtract(centroBase);
            double teste1 = Qp1.dot(Qp1);
            if (teste1 < squareRadius) {
                //.multiply(d).subtract(centroBase)
                Vector3 pontoInterseccaoBase = D.multiply(d).subtract(centroBase);
                interseccaoBase = new Intersection(pontoInterseccaoBase, d);
                return interseccaoBase;
            }        }

        //verifica interseção com a tampa superior
        if (d2 > 0) { //garante que está na direção do raio
            Vector3 Qp2 = D.multiply(d2).subtract(centroBase2);
            double teste2 = Qp2.dot(Qp2);
            if (teste2 < squareRadius) {
                Vector3 pontoInterseccaoTampa = D.multiply(d2).subtract(centroBase);
                interseccaoTampa = new Intersection(pontoInterseccaoTampa, d2);
                return interseccaoTampa;
            }
        }
        //nenhuma intersecao encontrada
        return null;
            
    }

    @Override
    public Vector3 calcularNormal(Vector3 pontoInterseccao) {
        Vector3 vetorCentroBaseParaPonto = pontoInterseccao.subtract(centroBase);
        double alturaProjetada = this.eixo.dot(vetorCentroBaseParaPonto);

        double tolerancia = 1e-6;

        //verifica se está na base inferior
        if (alturaProjetada < tolerancia) {
            return this.eixo.multiply(-1).normalize(); //Normal apontando para baixo
        }

        //Verifica se está na tampa superior
        if (Math.abs(alturaProjetada - altura) < tolerancia) {
            return this.eixo.normalize(); //Normal apontando para cima
        }
        //Caso contrario, esta na superficie lateral
        Vector3 pontoProjetadoNoEixo = centroBase.add(this.eixo.multiply(alturaProjetada));
        Vector3 normalLateral = pontoInterseccao.subtract(pontoProjetadoNoEixo).normalize();
        return normalLateral;
    }

    public void alterarEixo(Vector3 ajuste) {
        this.eixo =  this.eixo.add(ajuste).normalize();
    }

    public Vector3 getNormal() {
        return this.eixo;
    }

    @Override
    public void transladar(double dx, double dy, double dz) {
        this.centroBase = this.centroBase.add(new Vector3(dx, dy, dz));
    }

    @Override
    public String toString() {
        return "Cilindro {" +
                "centroBase=" + this.centroBase +
                ", raio=" + this.raio +
                ", altura=" + this.altura +
                '}';
    }

}
