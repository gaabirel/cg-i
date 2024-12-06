package src.model.interseccao;

public class Vector3 {
    
    private double[] values;

    public Vector3(double x, double y, double z){
        this.values = new double[]{x, y, z};
    }

    public double getX(){
        return this.values[0];
    }

    public double getY(){
        return this.values[1];
    }

    public double getZ(){
        return this.values[2];
    }

    //retorna o tamanho do vetor
    public double length() {
        return Math.sqrt(this.dot(this));
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(getX() + other.getX(), getY() + other.getY(), getZ() + other.getZ());
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(getX() - other.getX(), getY() - other.getY(), getZ() - other.getZ());
    }

    //Produto escalar (dot product)
    public double dot(Vector3 other) {
        return getX() * other.getX() + getY() * other.getY() + getZ() * other.getZ();
    }

    //Multiplica este vetor por um escalar
    public Vector3 multiply(double scalar) {
        return new Vector3(getX() * scalar, getY() * scalar, getZ() * scalar);
    }

    //Método para calcular a projeção de 'this' sobre 'other'
    public Vector3 projectOnto(Vector3 other) {
        //Calcula o produto escalar de 'this' e 'other'
        double dotProduct = this.dot(other);

        //Calcula o produto escalar de 'other' consigo mesmo
        double lengthSquared = other.dot(other);

        //Calcula a projeção
        double scalar = dotProduct / lengthSquared;

        //Retorna o vetor resultante da projeção
        return new Vector3(other.getX() * scalar, other.getY() * scalar, other.getZ() * scalar);
    }

    //Normaliza o vetor (torna seu comprimento igual a 1)
    public Vector3 normalize() {
        double length = this.length();
        return new Vector3(getX() / length, getY() / length, getZ() / length);
        
    }
    //cross product
    public Vector3 cross(Vector3 other) {
        double cx = getY() * other.getZ() - getZ() * other.getY();
        double cy = getZ() * other.getX() - getX() * other.getZ();
        double cz = getX() * other.getY() - getY() * other.getX();
        return new Vector3(cx, cy, cz);
    }
    
    public Vector3 negate(){
        return new Vector3(-getX(), -getY(), -getZ());
    }
    public boolean saoParalelos(Vector3 U) {
        // Calcula o produto vetorial D x U
        Vector3 produtoVetorial = this.cross(U);
    
        // Define uma tolerância para considerar erros numéricos
        double tolerancia = 1e-6;
    
        // Verifica se o produto vetorial é próximo de (0, 0, 0)
        return Math.abs(produtoVetorial.getX()) < tolerancia &&
               Math.abs(produtoVetorial.getY()) < tolerancia &&
               Math.abs(produtoVetorial.getZ()) < tolerancia;
    }

    public Vector3 arroba(Vector3 other){
        return new Vector3(getX() * other.getX(), getY() * other.getY(), getZ() * other.getZ());
    }
    public Boolean equals(Vector3 other){
        if(other.getX() == getX() && other.getY() == getY() && other.getZ() == getZ()) return true;
        else return false;
    }
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }

}
