package src.model;
import src.model.interseccao.Vector3;

public class Camera {

    private Vector3 posEye;
    private Vector3 lookAt;
    private Vector3 upPoint;

    private Vector3 k_c; // Vetor da câmera (direção do olhar)

    public Camera(Vector3 posEye, Vector3 lookAt, Vector3 viewUp) {
        this.posEye = posEye;
        this.lookAt = lookAt;
        this.upPoint = viewUp;
    }


    // Retorna a matriz da câmera
    public double[][] getCameraMatrix() {
        this.k_c = posEye.subtract(lookAt).normalize();
        Vector3 viewUp = upPoint.subtract(posEye);
        Vector3 i_c = viewUp.cross(k_c).normalize();
        Vector3 j_c = k_c.cross(i_c).normalize();

        double [][] matrix = {
            { i_c.getX(), i_c.getY(), i_c.getZ(), -i_c.dot(this.posEye) },
            { j_c.getX(), j_c.getY(), j_c.getZ(), -j_c.dot(this.posEye) },
            { k_c.getX(), k_c.getY(), k_c.getZ(), -k_c.dot(this.posEye) },
            { 0,          0,          0,          1 }
        };
        return matrix;
    }

    // Gera a matriz de rotação
    private double[][] getRotationMatrix() {
        this.k_c = posEye.subtract(lookAt).normalize();
        Vector3 viewUp = upPoint.subtract(posEye);
        Vector3 i_c = viewUp.cross(k_c).normalize();
        Vector3 j_c = k_c.cross(i_c).normalize();

        return new double[][] {
            { i_c.getX(), i_c.getY(), i_c.getZ(), 0 },
            { j_c.getX(), j_c.getY(), j_c.getZ(), 0 },
            { k_c.getX(), k_c.getY(), k_c.getZ(), 0 },
            { 0,          0,          0,          1 }
        };
    }

    // Gera a matriz de translação
    private double[][] getTranslationMatrix() {
        return new double[][] {
            { 1, 0, 0, -posEye.getX() },
            { 0, 1, 0, -posEye.getY() },
            { 0, 0, 1, -posEye.getZ() },
            { 0, 0, 0,  1 }
        };
    }

    // Multiplicação de matrizes 4x4
    private double[][] multiplyMatrices(double[][] m1, double[][] m2) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return result;
    }

    // Método para aumentar o zoom (aproximar a câmera)
    public void zoomIn(double fatorZoom) {
        Vector3 direcao = lookAt.subtract(posEye).normalize(); // direção da câmera
        this.posEye = posEye.add(direcao.multiply(fatorZoom)); // move a câmera mais perto do lookAt
    }

    // Método para diminuir o zoom (afastar a câmera)
    public void zoomOut(double fatorZoom) {
        Vector3 direcao = lookAt.subtract(posEye).normalize(); // direção da câmera
        this.posEye = posEye.subtract(direcao.multiply(fatorZoom)); // move a câmera mais longe do lookAt
    }

    // Getters e Setters
    public Vector3 getPosEye() {
        return posEye;
    }

    public void setPosEye(Vector3 posEye) {
        this.posEye = posEye;
    }

    public Vector3 getLookAt() {
        return lookAt;
    }

    public void setLookAt(Vector3 lookAt) {
        this.lookAt = lookAt;
    }

    public Vector3 getViewUp() {
        return upPoint;
    }

    public void setViewUp(Vector3 viewUp) {
        this.upPoint = viewUp;
    }

    public Vector3 getK_c() {
        return k_c;
    }

    public void setK_c(Vector3 k_c) {
        this.k_c = k_c;
    }
}
