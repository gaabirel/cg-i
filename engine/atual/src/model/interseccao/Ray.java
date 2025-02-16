package src.model.interseccao;
public class Ray {
    public Vector3 origin;    //O ponto de origem do raio
    public Vector3 direction; //A direção do raio, que deve ser normalizada

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction.normalize(); //Normaliza a direção ao criar o raio
    }
    public Vector3 getPoint(double t) {
        //Retorna o ponto a uma distância 't' ao longo do raio
        return origin.add(direction.multiply(t)); //Adiciona ao ponto de origem a direção multiplicada pela distância 't'
    }
    public void setOrigin(Vector3 newOrigin) {
        this.origin = newOrigin;
    }
    public void setDirection(Vector3 newDirection) {
        this.direction = newDirection.normalize();
    }
    public Vector3 getOrigin() {
        return origin;
    }
    public Vector3 getDirection() {
        return direction;
    }
    @Override
    public String toString() {
        return "Ray [origin=" + origin + ", direction=" + direction + "]";
    }
}