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

    public static double calcularMenorRaiz(double a, double b, double discriminant) {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        a *= 2;
        double t1 = (-b + sqrtDiscriminant) / a;
        double t2 = (-b - sqrtDiscriminant) / a;

        return menorValorPositivo(t1, t2);
    }

    public static double menorValorPositivo(double a, double b) {
        if (a <= 0 && b <= 0) {
            return Double.POSITIVE_INFINITY; 
        }

        if (a > 0 && b > 0) {
            return Math.min(a, b);
        }
    
        return (a > 0) ? a : b;
    }

    public Vector3 colorToVector(Color color) {
        return new Vector3(
            color.getRed() / 255.0,
            color.getGreen() / 255.0,
            color.getBlue() / 255.0
        );
    }

    public void rotacionar(double d, Vector3 axis){
       
    }
    /* Apenas o que deveria ser feito caso utilizasse gpu pela eficiência 
    public void translate(double dx, double dy, double dz) {
        double[][] translationMatrix = {
                {1, 0, 0, dx},
                {0, 1, 0, dy},
                {0, 0, 1, dz},
                {0, 0, 0, 1}
        };
        for(ponto : pontosObjeto){
            ponto = pontos.multiply(translationMatrix);
        }
    }
        */
}
