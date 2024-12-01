package src.model.objetos;

import java.awt.Color;

import src.model.interseccao.Vector3;
import src.model.materiais.Material;

public abstract class Objeto3D{

    protected Material material;


    public void setCor(Color cor) {
        // Extrair os componentes RGB da cor
        float r = cor.getRed() / 255.0f; // Valor entre 0 e 1
        float g = cor.getGreen() / 255.0f; // Valor entre 0 e 1
        float b = cor.getBlue() / 255.0f; // Valor entre 0 e 1

        Vector3 kAmbiente = new Vector3(r * 0.2f, g * 0.2f, b * 0.2f); // Ajuste para ambiente
        Vector3 kDifuso = new Vector3(r * 0.7f, g * 0.7f, b * 0.7f); // Ajuste para difuso
        float max = Math.max(r, Math.max(g, b));
        Vector3 kEspecular = new Vector3(max, max, max); // Ajuste para especular

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

     /**
     *Converte uma cor RGB para um vetor normalizado.
     */
    public Vector3 colorToVector(Color color) {
        return new Vector3(
            color.getRed() / 255.0,
            color.getGreen() / 255.0,
            color.getBlue() / 255.0
        );
    }

}
