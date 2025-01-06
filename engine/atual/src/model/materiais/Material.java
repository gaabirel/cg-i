package src.model.materiais;

import java.awt.Color;

import src.model.interseccao.Vector3;

public class Material {
    private Vector3 kDifuso;       // Coeficiente de reflexão difusa
    private Vector3 kEspecular;   // Coeficiente de reflexão especular
    private Vector3 kAmbiente;    // Coeficiente de iluminação ambiente
    private double brilho;        // Expoente para o brilho especular
    private Color corBase;        // Cor base do material
    private String nome;

    public Material(Vector3 kDifuso, Vector3 kEspecular, Vector3 kAmbiente) {
        this.kDifuso = kDifuso;
        this.kEspecular = kEspecular;
        this.kAmbiente = kAmbiente;
        this.brilho = 5;
        this.nome = null;
    }
    public Material(String nome, Vector3 kDifuso, Vector3 kEspecular, Vector3 kAmbiente) {
        this(kDifuso, kEspecular, kAmbiente);
        this.nome = nome;
    }

    public Material(Vector3 kDifuso, Vector3 kEspecular, Vector3 kAmbiente, double brilho) {
        this(kDifuso, kEspecular, kAmbiente);
        setBrilho(brilho);
    }
    public Material(String nome, Vector3 kDifuso, Vector3 kEspecular, Vector3 kAmbiente, double brilho) {
        this(nome, kDifuso, kEspecular, kAmbiente);
        setBrilho(brilho);
    }
    
    public String getNome(){
        return this.nome;
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

    @Override
    public String toString() {
        return "  \n Material {" +
                " \n nome=" + nome +
                " \n kDifuso=" + kDifuso +
                " \n kEspecular=" + kEspecular +
                " \n kAmbiente=" + kAmbiente +
                " \n brilho=" + brilho +
                " \n corBase=" + corBase +
                "}";
    }
}
