package src.model;

public class Cilindro extends Objeto3D implements Intersectable {
    private double raio;           // Raio do cilindro
    private Vector3 centroBase;    // Ponto central da base do cilindro
    private double altura;         // Altura do cilindro
    private Vector3 eixo;        // Vetor unitário que define o eixo do cilindro

    public Cilindro(Vector3 centroBase, double raio, double altura,
                    Vector3 k_difuso, Vector3 k_especular, Vector3 k_ambiente) {
        this.centroBase = centroBase;
        this.raio = raio;
        this.altura = altura;
        this.k_difuso = k_difuso;
        this.k_especular = k_especular;
        this.k_ambiente = k_ambiente;
        this.eixo = new Vector3(1, 1, 0);
    }

    @Override
    public Intersection intersect(Ray ray) {
        // Vetores de origem e direção do raio
        Vector3 O = ray.origin;  // Ponto de origem do raio
        Vector3 D = ray.direction;  // Direção do raio

        // Definição dos vetores conforme as equações
        Vector3 OC = O.subtract(centroBase);  // Vetor de origem até o centro da base
        double A = D.dot(D) - Math.pow(D.dot(eixo), 2);  // Coeficiente A
        double B = 2 * (D.dot(OC) - (D.dot(eixo) * OC.dot(eixo)));  // Coeficiente B
        double C = OC.dot(OC) - Math.pow(OC.dot(eixo), 2) - Math.pow(raio, 2);  // Coeficiente C

        // Calculando o discriminante
        double delta = B * B - 4 * A * C;

        if (delta < 0) {
            return null; // Não há interseção
        }

        // Calculando os valores de t para a interseção
        double t1 = (-B - Math.sqrt(delta)) / (2 * A);
        double t2 = (-B + Math.sqrt(delta)) / (2 * A);

        // Pegando o menor valor de t (interseção mais próxima)
        double t = Math.min(t1, t2);

        // Calculando o ponto de interseção
        Vector3 pontoInterseccao = ray.getPoint(t);

        // Calculando a projeção do ponto sobre o eixo do cilindro
        Vector3 projecaoEixo = pontoInterseccao.subtract(centroBase).projectOnto(eixo);
        
        // Verificando se a interseção está dentro da altura do cilindro
        double distanciaNoEixo = projecaoEixo.length();
        if (distanciaNoEixo < 0 || distanciaNoEixo > altura) {
            return null; // A interseção está fora do cilindro
        }

        // Criando a interseção
        return new Intersection(pontoInterseccao, t);
    }

    @Override
    public Vector3 calcularNormal(Vector3 ponto) {
        // A normal em qualquer ponto da superfície lateral do cilindro é dada pela diferença entre o ponto
        // e o centro da base, projetada no plano perpendicular ao eixo do cilindro
        Vector3 normal = ponto.subtract(centroBase);
        normal.z = 0;  // Ignoramos a componente Z para manter a normal na superfície lateral
        return normal.normalize();  // Retorna a normal unitária
    }

    @Override
    public void mover(double dx, double dy, double dz) {
        // Movendo o cilindro alterando o centro da base
        this.centroBase = this.centroBase.add(new Vector3(dx, dy, dz));
    }
}
