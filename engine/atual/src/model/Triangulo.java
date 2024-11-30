package src.model;

public class Triangulo  extends Objeto3D implements Intersectable{
    private Vector3 v1, v2, v3; // Vértices do triângulo
    private Vector3 n;

    public Triangulo(Vector3 v1, Vector3 v2, Vector3 v3, Vector3[] kIluminacao) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.setKdifuso(kIluminacao[0]);
        this.setKespecular(kIluminacao[1]);
        this.setKambiente(kIluminacao[2]);
        Vector3 r1 = v2.subtract(v1); // v2 - v1
        Vector3 r2 = v3.subtract(v1); // v3 - v1
        this.n = r1.cross(r2).normalize(); // Produto vetorial
    }

    /**
     * Verifica a interseção de um raio com o triângulo.
     * @param raio.origin Ponto de origem do raio.
     * @param raio.direction Vetor unitário da direção do raio.
     * @return O ponto de interseção ou null, se não houver interseção.
     */
    public Intersection intersect(Ray raio) {
        //etapa 1: Cálculo do vetor normal ao plano do triângulo
        Vector3 r1 = v2.subtract(v1);
        Vector3 r2 = v3.subtract(v1);
        Vector3 normal = r1.cross(r2).normalize();

        //etapa 2: Verifica se o raio é paralelo ao plano
        double denom = raio.direction.dot(normal);
        if (Math.abs(denom) < 1e-6) {
            return null; //sem interseção (raio paralelo ao plano)
        }

        //etapa 3: Cálculo do ponto de interseção no plano
        double t = -(raio.origin.subtract(v1).dot(normal)) / denom;
        if (t < 0) {
            return null; //interseção atrás da origem do raio
        }
        Vector3 pontoInterseccao = raio.origin.add(raio.direction.multiply(t));

        //etapa 4: Verificação se o ponto está dentro do triângulo
        //usando coordenadas baricêntricas
        Vector3 c;

        //vetores do ponto de interseção para os vértices
        Vector3 vp1 = pontoInterseccao.subtract(v1);
        Vector3 vp2 = pontoInterseccao.subtract(v2);
        Vector3 vp3 = pontoInterseccao.subtract(v3);

        //testa cada borda usando a regra da mão direita
        Vector3 cross1 = r1.cross(vp1);
        if (normal.dot(cross1) < 0) return null;

        Vector3 r2Reversed = v3.subtract(v2);
        Vector3 cross2 = r2Reversed.cross(vp2);
        if (normal.dot(cross2) < 0) return null;

        Vector3 r3Reversed = v1.subtract(v3);
        Vector3 cross3 = r3Reversed.cross(vp3);
        if (normal.dot(cross3) < 0) return null;

        // e passar por todos os testes, o ponto está dentro do triângulo
        return new Intersection(pontoInterseccao, t);
    }

    @Override
    public Vector3 calcularNormal(Vector3 ponto) {
        return n;
    }

    public void setNewNormalTriangulo(){
        Vector3 r1 = v2.subtract(v1); // v2 - v1
        Vector3 r2 = v3.subtract(v1); // v3 - v1
        this.n = r1.cross(r2).normalize(); // Produto vetorial
    }

    @Override
    public void mover(double dx, double dy, double dz) {
        this.v1.x += dx;
        this.v1.y += dy;
        this.v1.z += dz;
        this.v2.x += dx;
        this.v2.y += dy;
        this.v2.z += dz;
        this.v3.x += dx;
        this.v3.y += dy;
        this.v3.z += dz;
        setNewNormalTriangulo();
    }
}
