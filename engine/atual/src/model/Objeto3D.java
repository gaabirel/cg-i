package src.model;

public abstract class Objeto3D{
    protected Vector3 k_especular;
    protected Vector3 k_difuso;
    protected Vector3 k_ambiente;

    public Vector3 getKdifuso(){
        return this.k_difuso;
    }
    
    public Vector3 getKespecular(){
        return this.k_especular;
    }
    
    public Vector3 getKambiente(){
        return this.k_ambiente;
    }
    public void setKambiente(Vector3 novoK){
        this.k_ambiente = novoK;
    }
    public void setKdifuso(Vector3 novoK){
        this.k_difuso = novoK;
    }

    public void setKespecular(Vector3 novoK){
        this.k_especular = novoK;
    }

    // Outros métodos ou atributos comuns
}
