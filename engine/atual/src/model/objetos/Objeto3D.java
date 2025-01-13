package src.model.objetos;

import java.awt.Color;

import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public abstract class Objeto3D{

    protected Material material;

    public void setCor(Color cor) {
        // Extrair os componentes RGB da cor
        float r = cor.getRed() / 255.0f;
        float g = cor.getGreen() / 255.0f; 
        float b = cor.getBlue() / 255.0f; 

        Vector3 kAmbiente = new Vector3(r * 0.2f, g * 0.2f, b * 0.2f); //Ajuste para ambiente
        Vector3 kDifuso = new Vector3(r * 0.7f, g * 0.7f, b * 0.7f); //Ajuste para difuso
        float max = Math.max(r, Math.max(g, b));
        Vector3 kEspecular = new Vector3(max, max, max); //Ajuste para especular

        Material novoMaterial = new Material(kDifuso, kEspecular, kAmbiente);
        this.material = novoMaterial;
    }

    public double getBrilho(){
        return this.material.getBrilho();
    }

    public void setBrilho(double brilho){
        this.material.setBrilho(brilho);
    }
    
    public void setMaterial(Material newMaterial){
        this.material = newMaterial;
    }

    public Material getMaterial(){
        return this.material;
    }

    public Vector3 getKdifuso(){
        return this.material.getkDifuso();
    }
    
    public Vector3 getKespecular(){
        return this.material.getkEspecular();
    }
    
    public Vector3 getKambiente(){
        return this.material.getkAmbiente();
    }
    public void setKambiente(Vector3 novoK){
        this.material.setkAmbiente(novoK);
    }
    public void setKdifuso(Vector3 novoK){
        this.material.setkDifuso(novoK);
    }

    public void setKespecular(Vector3 novoK){
        this.material.setkEspecular(novoK);
    }

    public Intersectable aplicarMatrixCamera(double[][] matriz){
        return new Esfera(getBrilho(), getKambiente(), material);
    }

    public static double calcularMenorRaiz(double a, double b, double discriminant) {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        a *= 2;
        double t1 = (-b + sqrtDiscriminant) / a;
        double t2 = (-b - sqrtDiscriminant) / a;

        return menorValorPositivo(t1, t2);
    }

    public static double menorValorPositivo(double a, double b) {
        double tolerancia = 1e-6;
        if (a <=  tolerancia && b <=  tolerancia) {
            return Double.POSITIVE_INFINITY; 
        }

        if (a >  tolerancia && b >  tolerancia) {
            return Math.min(a, b);
        }
    
        return (a >  tolerancia) ? a : b;
    }

    public Vector3 colorToVector(Color color) {
        return new Vector3(
            color.getRed() / 255.0,
            color.getGreen() / 255.0,
            color.getBlue() / 255.0
        );
    }

    // Método para multiplicar duas matrizes 4x4
    public double[][] multiplyMatrices(double[][] m1, double[][] m2) {
        double[][] result = new double[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }

        return result;
    }
    public void escala(double sx, double sy, double sz) {
        /*double[][] scaleMatrix = {
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        };
        for(ponto : pontosObjeto){
            ponto = pontos.multiply(scaleMatrix);
        }*/
    }

    public void cisalhar(double shXY, double shXZ, double shYX, double shYZ, double shZX, double shZY) {
        throw new UnsupportedOperationException("Cisalhamento não suportado.");
    }
    
    protected Vector3 aplicarCisalhamento(Vector3 ponto, double[][] shearMatrix) {
        double x = ponto.getX();
        double y = ponto.getY();
        double z = ponto.getZ();
        
        // Aplicação da matriz de cisalhamento
        double novoX = shearMatrix[0][0] * x + shearMatrix[0][1] * y + shearMatrix[0][2] * z;
        double novoY = shearMatrix[1][0] * x + shearMatrix[1][1] * y + shearMatrix[1][2] * z;
        double novoZ = shearMatrix[2][0] * x + shearMatrix[2][1] * y + shearMatrix[2][2] * z;

        return new Vector3(novoX, novoY, novoZ);
    }

    public void espelhar(String eixo) {
        
    }

}
