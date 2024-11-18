package src.model;

public class Cilindro extends Objeto3D implements Intersectable {
    private double raio;           // Raio do cilindro
    private Vector3 centroBase;    // Ponto central da base do cilindro
    private double altura;         // Altura do cilindro
    private Vector3 eixo;        // Vetor unitário que define o eixo do cilindro

    public Cilindro(Vector3 centroBase, double raio, double altura,
                    Vector3[] k_iluminacao) {
        this.centroBase = centroBase;
        this.raio = raio;
        this.altura = altura;
        this.setKdifuso(k_iluminacao[0]);
        this.setKespecular(k_iluminacao[1]);
        this.setKambiente(k_iluminacao[2]);
        this.eixo = new Vector3(0, 0, 1);
    }

    @Override
    public Intersection intersect(Ray ray) {
        // Vetores de origem e direção do raio
        Vector3 O = ray.origin;  // Ponto de origem do raio
        Vector3 D = ray.direction;  // Direção do raio


        Vector3 W = D.subtract((eixo.multiply(D.dot(eixo))));
        Vector3 OC = O.subtract(centroBase);  // Vetor de origem até o centro da base
        Vector3 V = OC.subtract(eixo.multiply(eixo.dot(OC)));
        // Definição dos vetores conforme as equações
        double A = W.dot(W);  // Coeficiente A
        double B = 2*(V.dot(W));  // Coeficiente B
        double C = V.dot(V) - Math.pow(raio, 2);  // Coeficiente C

        
        // Calculando o discriminante
        double delta = B * B - 4 * A * C;
        
        if (delta < 0) {
            return null; // Não há interseção
        }

        // Calculando os valores de t para a interseção
        double t1 = (-B - Math.sqrt(delta)) / (2 * A);
        double t2 = (-B + Math.sqrt(delta)) / (2 * A);
        if(delta == 0) return new Intersection(ray.getPoint(t1), t1);

        //se ambos forem menor que 0 nao possui interseccao, pois esta atras do olho do pintor
        if(t1 < 0 && t2 < 0) return null;
        //double t = (t1 > 0 ? t1 : t2);
        double t = Math.min(t1, t2);
        // Pegando o menor valor de t (interseção mais próxima)

        Vector3 pontoInterseccao = ray.getPoint(t);
        
        // Calculando a projeção dos pontos sobre o eixo do cilindro
        double distanciaNoEixo1 = (pontoInterseccao.subtract(centroBase)).dot(eixo);

        // Verifica se o primeiro ponto está dentro da altura do cilindro
        if (((distanciaNoEixo1 >= 0) && (distanciaNoEixo1 <= altura))) {
            return new Intersection(pontoInterseccao, t);
        }
       
        Vector3 centroBase2 = centroBase.add(eixo.multiply(altura));  // base do topo do cilindro
        double dem = (eixo.dot(D));                                   //denominador dos calculos

        double d = (eixo.dot(centroBase) / dem);                      //distancia a base inferior 
        double d2 = (eixo.dot(centroBase2) / dem);                    //distancia a base superior
        Intersection interseccaoBase = null;
        Intersection interseccaoTampa = null;

        // Verifica interseção com a base inferior
        if (d > 0) { // Garante que está na direção do raio
            Vector3 Qp1 = D.multiply(d).subtract(centroBase);
            double teste1 = Qp1.dot(Qp1);
            if (teste1 < Math.pow(raio, 2)) {
                interseccaoBase = new Intersection(D.multiply(d).subtract(centroBase), d);
            }
        }

        // Verifica interseção com a tampa superior
        if (d2 > 0) { // Garante que está na direção do raio
            Vector3 Qp2 = D.multiply(d2).subtract(centroBase2);
            double teste2 = Qp2.dot(Qp2);
            if (teste2 < Math.pow(raio, 2)) {
                interseccaoTampa = new Intersection(D.multiply(d2).subtract(centroBase2), d2);
            }
        }

// Retorna a interseção mais próxima (menor `d` positivo)
if (interseccaoBase != null && interseccaoTampa != null) {
    return interseccaoBase.distance < interseccaoTampa.distance ? interseccaoBase : interseccaoTampa;
} else if (interseccaoBase != null) {
    return interseccaoBase;
} else if (interseccaoTampa != null) {
    return interseccaoTampa;
}

// Nenhuma interseção encontrada
return null;
       
    }

    @Override
    public Vector3 calcularNormal(Vector3 pontoInterseccao) {
        Vector3 vetorCentroBaseParaPonto = pontoInterseccao.subtract(centroBase);
        double alturaProjetada = eixo.dot(vetorCentroBaseParaPonto);
        
        //Verifica se está na base inferior
        if (alturaProjetada <= 0) {
            return eixo.multiply(-1); //Normal apontando para baixo
        }
        
        //Verifica se está na tampa superior
        if (alturaProjetada >= altura) {
            return eixo; // Normal apontando para cima
        }
        
        //Caso contrário, está na superfície lateral
        Vector3 pontoProjetadoNoEixo = centroBase.add(eixo.multiply(alturaProjetada));
        Vector3 normalLateral = pontoInterseccao.subtract(pontoProjetadoNoEixo).normalize();
        return normalLateral;
    }

    @Override
    public void mover(double dx, double dy, double dz) {
        // Movendo o cilindro alterando o centro da base
        this.centroBase = this.centroBase.add(new Vector3(dx, dy, dz));
    }
}
