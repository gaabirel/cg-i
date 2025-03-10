package src.model.objetos;
import java.awt.Color;

import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.model.materiais.Material;
import java.awt.image.BufferedImage;

public class Plano extends Objeto3D implements Intersectable  {

    private Vector3 Ppl;
    private Vector3 N;
    private BufferedImage textura;
    private boolean deitado;
    //Valores padrão para os coeficientes de iluminação
    private static final Vector3 DEFAULT_K_ESPECULAR = new Vector3(0.2, 0.2, 0.2);
    private static final Vector3 DEFAULT_K_AMBIENTE = new Vector3(0.9, 0.9, 0.9);

    public Plano(Vector3 Ppl, Vector3 N, Color colorDifuso, boolean deitado) {
        this.Ppl = Ppl;
        this.N = N.normalize();
       setMaterial(new Material(colorToVector(colorDifuso), DEFAULT_K_ESPECULAR, DEFAULT_K_AMBIENTE));
       this.deitado = deitado;
    }

    public Plano(Vector3 Ppl, Vector3 N, Material material, boolean deitado){
        this(Ppl, N, new Color(0, 0, 0), deitado);
        setMaterial(material);
    }   
    //Construtor para textura
    public Plano(Vector3 Ppl, Vector3 N, BufferedImage textura, boolean deitado) {
        this.deitado = deitado;
        this.Ppl = Ppl;
        this.N = N.normalize();
        this.textura = textura;
        setMaterial(new Material(new Vector3(0.1, 0.1, 0.1), DEFAULT_K_ESPECULAR, new Vector3(0.1, 0.1, 0.1)));
    }
    @Override
    public Intersection intersect(Ray ray, double[][] matrizTransformacao) {
        Vector3 Ppl = this.Ppl.multiplyMatrix4x4(matrizTransformacao);
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
    public int[] getTexturaColor(Vector3 pontoIntersecao) {
        if (textura == null) {
            return new int[]{0, 0, 0};
        }
        double escalaTextura = 9;
        double deslocamentoV = 0.5;
        double v;
        double u = ((pontoIntersecao.getX() / escalaTextura) % 1.0);
    
        if (deitado) {
            v = ((pontoIntersecao.getZ() / escalaTextura) % 1.0);
        } else {
            v = ((pontoIntersecao.getY() / escalaTextura) % 1.0);
        }
        
        // Ajustar para valores positivos
        if (u < 0) u += 1.0;
        if (v < 0) v += 1.0;
        v = (v + deslocamentoV) % 1.0;

        // Converter para coordenadas de pixel da textura
        int texX = (int) (u * (textura.getWidth() - 1));
        int texY = (int) ((1 - v) * (textura.getHeight() - 1)); // Inverter Y pois imagens geralmente têm (0,0) no topo
    
        // Obter a cor do pixel da textura
        int rgb = textura.getRGB(texX, texY);
    
        // Extrair componentes RGB
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
    
        double fator_atenuacao = 0.5;
        return new int[]{
            (int) (r * fator_atenuacao),
            (int) (g * fator_atenuacao),
            (int) (b * fator_atenuacao)
        };
    }
    

    @Override
    public Vector3 calcularNormal(Vector3 ponto, double[][] matrizTransformacao) {
        return this.N.multiplyMatrix4x4(matrizTransformacao).normalize();
    }

    @Override
    public void transladar(double dx, double dy, double dz) {
        Ppl.add(new Vector3(Ppl.getX() + dx, Ppl.getY() + dy, Ppl.getZ() + dz));
    }

    @Override
    public void rotacionar(double angleDegrees, Vector3 axis) {
        this.N = this.N.rotate(angleDegrees, axis);
    }

    @Override
    public void escala(double sx, double sy, double sz){
        // Não faz nada
        throw new UnsupportedOperationException("Não é possível escalar um plano infinito");
    }

    @Override 
    public Intersectable aplicarMatrixCamera(double[][] matrix){
        return new Plano(Ppl.multiplyMatrix4x4(matrix), DEFAULT_K_AMBIENTE, material, this.deitado);
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
