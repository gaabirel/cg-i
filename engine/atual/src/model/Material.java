package src.model;

import java.awt.Color;

public class Material {
    private Vector3 kDifuso;       // Coeficiente de reflexão difusa
    private Vector3 kEspecular;   // Coeficiente de reflexão especular
    private Vector3 kAmbiente;    // Coeficiente de iluminação ambiente
    private double brilho;        // Expoente para o brilho especular
    private Color corBase;        // Cor base do material

    public Material(Vector3 kDifuso, Vector3 kEspecular, Vector3 kAmbiente, double brilho, Color corBase) {
        this.kDifuso = kDifuso;
        this.kEspecular = kEspecular;
        this.kAmbiente = kAmbiente;
        this.brilho = brilho;
        this.corBase = corBase;
    }

    // Getters e Setters
    public Vector3 getkDifuso() {
        return kDifuso;
    }

    public void setkDifuso(Vector3 kDifuso) {
        this.kDifuso = kDifuso;
    }

    public Vector3 getkEspecular() {
        return kEspecular;
    }

    public void setkEspecular(Vector3 kEspecular) {
        this.kEspecular = kEspecular;
    }

    public Vector3 getkAmbiente() {
        return kAmbiente;
    }

    public void setkAmbiente(Vector3 kAmbiente) {
        this.kAmbiente = kAmbiente;
    }

    public double getBrilho() {
        return brilho;
    }

    public void setBrilho(double brilho) {
        this.brilho = brilho;
    }

    public Color getCorBase() {
        return corBase;
    }

    public void setCorBase(Color corBase) {
        this.corBase = corBase;
    }

    @Override
    public String toString() {
        return "Material {" +
                "kDifuso=" + kDifuso +
                ", kEspecular=" + kEspecular +
                ", kAmbiente=" + kAmbiente +
                ", brilho=" + brilho +
                ", corBase=" + corBase +
                '}';
    }
}
