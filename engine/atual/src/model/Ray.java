package src.model;
public class Ray {
    public Vector3 origin;    //O ponto de origem do raio
    public Vector3 direction; //A direção do raio, que deve ser normalizada

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction; //Normaliza a direção ao criar o raio
    }

    @Override
    public String toString() {
        return "Ray [origin=" + origin + ", direction=" + direction + "]";
    }
}