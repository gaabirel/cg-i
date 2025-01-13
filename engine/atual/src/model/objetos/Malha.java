package src.model.objetos;

import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;




public class Malha extends Objeto3D implements Intersectable {

    private Vector3[] vertices;
    private Aresta[] arestas;
    private Face[] faces;

    public Malha(Vector3[] vertices, Aresta[] arestas, Face[] faces, Material material) {   
        this.vertices = vertices;
        this.arestas = arestas;
        this.faces = faces;
        setMaterial(material);
    }

    @Override
    public Intersection intersect(Ray ray) {
        Intersection closestIntersection = null;
        double minDistance = Double.MAX_VALUE;

        for (Face face : faces) {
            Intersection intersection = intersectTriangle(ray, face);
            if (intersection != null && intersection.distance < minDistance) {
                minDistance = intersection.distance;
                closestIntersection = intersection;
            }
        }
        return closestIntersection;
    }

    private Intersection intersectTriangle(Ray ray, Face face) {
        // Extrair os vértices da face a partir das arestas
        Vector3[] vertices = face.getVertices();
        Vector3 v0 = vertices[0];
        Vector3 v1 = vertices[1];
        Vector3 v2 = vertices[2];

        // Calcular os vetores da face
        Vector3 edge1 = v1.subtract(v0);
        Vector3 edge2 = v2.subtract(v0);

        // Calcular o vetor perpendicular à direção do raio e edge2
        Vector3 h = ray.getDirection().cross(edge2);
        double a = edge1.dot(h);

        if (a > -1e-8 && a < 1e-8) {
            // O raio é paralelo ao triângulo
            return null;
        }

        double f = 1.0 / a;
        Vector3 s = ray.getOrigin().subtract(v0);
        double u = f * s.dot(h);

        if (u < 0.0 || u > 1.0) {
            return null;
        }

        Vector3 q = s.cross(edge1);
        double v = f * ray.getDirection().dot(q);

        if (v < 0.0 || u + v > 1.0) {
            return null;
        }

        double t = f * edge2.dot(q);

        if (t > 1e-8) {
            // Interseção detectada
            Vector3 intersectionPoint = ray.getOrigin().add(ray.getDirection().multiply(t));
            return new Intersection(intersectionPoint, t);
        }

        return null;
    }

    @Override
    public Vector3 calcularNormal(Vector3 ponto) {
        // Percorrer as faces da malha
        for (Face face : faces) {
            // Obter os vértices da face
            Vector3[] vertices = face.getVertices();
            Vector3 v0 = vertices[0];
            Vector3 v1 = vertices[1];
            Vector3 v2 = vertices[2];

            // Calcular vetores das arestas
            Vector3 edge1 = v1.subtract(v0);
            Vector3 edge2 = v2.subtract(v0);

            // Calcular a normal da face (produto vetorial das arestas)
            Vector3 normal = edge1.cross(edge2).normalize();

            // Verificar se o ponto está no plano do triângulo usando a equação do plano
            Vector3 pontoRelativo = ponto.subtract(v0);
            double distanciaDoPlano = pontoRelativo.dot(normal);

            // Considerar ponto como pertencente se estiver próximo ao plano
            if (Math.abs(distanciaDoPlano) < 1e-6) {
                // Adicional: Verificar se o ponto está dentro dos limites do triângulo
                if (isPointInTriangle(ponto, v0, v1, v2)) {
                    return normal;
                }
            }
        }

        // Caso nenhuma face corresponda, retornar null
        return null;
    }

    // Método auxiliar para verificar se o ponto está dentro do triângulo usando coordenadas baricêntricas
    private boolean isPointInTriangle(Vector3 ponto, Vector3 v0, Vector3 v1, Vector3 v2) {
        Vector3 u = v1.subtract(v0);
        Vector3 v = v2.subtract(v0);
        Vector3 w = ponto.subtract(v0);

        double uu = u.dot(u);
        double uv = u.dot(v);
        double vv = v.dot(v);
        double wu = w.dot(u);
        double wv = w.dot(v);

        double denominator = (uv * uv - uu * vv);
        double s = (uv * wv - vv * wu) / denominator;
        double t = (uv * wu - uu * wv) / denominator;

        return s >= 0 && t >= 0 && (s + t) <= 1;
    }



    @Override
    public void transladar(double dx, double dy, double dz) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transladar'");
    }

    @Override
    public void rotacionar(double angulo, Vector3 axis) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rotacionar'");
    }
}
