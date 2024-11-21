package src.model;

public class Vector3 {
    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //retorna o tamanho do vetor
    public double length() {
        return Math.sqrt(this.dot(this));
    }

    //Subtrai outro vetor deste vetor
    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    //Produto escalar (dot product)
    public double dot(Vector3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    //Multiplica este vetor por um escalar
    public Vector3 multiply(double scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    //Método para calcular a projeção de 'this' sobre 'outro'
    public Vector3 projectOnto(Vector3 outro) {
        //Calcula o produto escalar de 'this' e 'outro'
        double dotProduct = this.dot(outro);

        //Calcula o produto escalar de 'outro' consigo mesmo
        double lengthSquared = outro.dot(outro);

        //Calcula a projeção
        double scalar = dotProduct / lengthSquared;

        //Retorna o vetor resultante da projeção
        return new Vector3(outro.x * scalar, outro.y * scalar, outro.z * scalar);
    }

    //Normaliza o vetor (torna seu comprimento igual a 1)
    public Vector3 normalize() {
        double length = this.length();
        return new Vector3(this.x / length, this.y / length, this.z / length);
        
    }
    //cross product
    public Vector3 cross(Vector3 other) {
        double cx = this.y * other.z - this.z * other.y;
        double cy = this.z * other.x - this.x * other.z;
        double cz = this.x * other.y - this.y * other.x;
        return new Vector3(cx, cy, cz);
    }
    public Vector3 negate(){
        return new Vector3(-this.x, -this.y, -this.z);
    }
    public boolean saoParalelos(Vector3 U) {
        // Calcula o produto vetorial D x U
        Vector3 produtoVetorial = this.cross(U);
    
        // Define uma tolerância para considerar erros numéricos
        double tolerancia = 1e-6;
    
        // Verifica se o produto vetorial é próximo de (0, 0, 0)
        return Math.abs(produtoVetorial.x) < tolerancia &&
               Math.abs(produtoVetorial.y) < tolerancia &&
               Math.abs(produtoVetorial.z) < tolerancia;
    }
    //Adiciona outro vetor a este vetor
    public Vector3 add(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }
    public Vector3 arroba(Vector3 other){
        return new Vector3(this.x * other.x, this.y * other.y, this.z * other.z);
    }
    public Boolean equals(Vector3 other){
        if(other.x == this.x && other.y == this.y && other.z == this.z) return true;
        else return false;
    }
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

}
