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
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
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
        if (length > 0) {
            return new Vector3(this.x / length, this.y / length, this.z / length);
        } else {
            return new Vector3(0, 0, 0); //Retorna um vetor nulo se o comprimento for zero
        }
        
    }

    //Adiciona outro vetor a este vetor
    public Vector3 add(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }
    public Vector3 arroba(Vector3 other){
        return new Vector3(this.x * other.x, this.y * other.y, this.z * other.z);
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

}
