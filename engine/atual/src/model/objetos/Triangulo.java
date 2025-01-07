package src.model.objetos;

import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public class Triangulo extends Objeto3D implements Intersectable {
    private Vector3 v1, v2, v3; // Vértices do triângulo
    private Vector3 normal;

    public Triangulo(Vector3 v1, Vector3 v2, Vector3 v3, Material material) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        atualizarNormal();
        setMaterial(material);
    }

    private void atualizarNormal() {
        Vector3 edge1 = v2.subtract(v1);
        Vector3 edge2 = v3.subtract(v1);
        this.normal = edge1.cross(edge2).normalize();
        if (normal.length() == 0) {
            throw new IllegalArgumentException("Os vértices fornecidos não formam um triângulo válido.");
        }
    }

    @Override
    public Intersection intersect(Ray raio) {
        double denom = raio.direction.dot(normal);
        if (Math.abs(denom) < 1e-6) return null;

        double t = -(raio.origin.subtract(v1).dot(normal)) / denom;
        if (t < 0) return null;

        Vector3 pontoInterseccao = raio.origin.add(raio.direction.multiply(t));

        Vector3 edge1 = v2.subtract(v1);
        Vector3 edge2 = v3.subtract(v2);
        Vector3 edge3 = v1.subtract(v3);

        if (normal.dot(edge1.cross(pontoInterseccao.subtract(v1))) < 0 ||
            normal.dot(edge2.cross(pontoInterseccao.subtract(v2))) < 0 ||
            normal.dot(edge3.cross(pontoInterseccao.subtract(v3))) < 0) {
            return null;
        }

        return new Intersection(pontoInterseccao, t);
    }

    @Override
    public Vector3 calcularNormal(Vector3 ponto) {
        return normal;
    }

    @Override
    public void transladar(double dx, double dy, double dz) {
        Vector3 deslocamento = new Vector3(dx, dy, dz);
        v1 = v1.add(deslocamento);
        v2 = v2.add(deslocamento);
        v3 = v3.add(deslocamento);
        atualizarNormal();

        /*  Utilizando multiplicação de matrizes, ao invés de uma forma direta, ficaria assim: 
        double[][] translationMatrix = {
            {1, 0, 0, dx},
            {0, 1, 0, dy},
            {0, 0, 1, dz},
            {0, 0, 0, 1}
        };
        v1 = v1.multiplyMatrix4x4(translationMatrix);
        v2 = v2.multiplyMatrix4x4(translationMatrix);
        v3 = v3.multiplyMatrix4x4(translationMatrix);
        atualizarNormal();
        */
    }

    @Override
    public void rotacionar(double angleDegrees, Vector3 axis) {
        this.v1 = this.v1.rotate(angleDegrees, axis);
        this.v2 = this.v2.rotate(angleDegrees, axis);
        this.v3 = this.v3.rotate(angleDegrees, axis);
        
        atualizarNormal();
    }

    @Override 
    public void escala(double sx, double sy, double sz){
        double[][] scaleMatrix = {
            {sx, 0, 0, 0},
            {0, sy, 0, 0},
            {0, 0, sz, 0},
            {0, 0, 0, 1}
        };
        v1 = v1.multiplyMatrix4x4(scaleMatrix);
        v2 = v2.multiplyMatrix4x4(scaleMatrix);
        v3 = v3.multiplyMatrix4x4(scaleMatrix);
        
        atualizarNormal();
    }
    @Override
    public String toString() {
        return "Triangulo {" +
                "  v1=" + this.v1 +
                ", v2=" + this.v2 +
                ", v3=" + this.v3 +
                ", material=" + this.material + 
                '}';
    }

}