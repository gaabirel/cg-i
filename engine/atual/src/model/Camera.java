package src.model;

import java.util.ArrayList;

import src.model.interseccao.Light;
import src.model.interseccao.Vector3;
import src.model.objetos.Intersectable;

public class Camera {

    private Vector3 posEye;
    private Vector3 lookAt;
    private Vector3 viewUp;
    private Vector3 k_c;

    public Camera(Vector3 posEye, Vector3 lookAt, Vector3 viewUp) {
        this.posEye = posEye;
        this.lookAt = lookAt;
        this.viewUp = viewUp;
        this.k_c = posEye.subtract(lookAt).normalize(); // Vetor da câmera
    }

    public double[][] getCameraMatrix() {
        k_c = posEye.subtract(lookAt).normalize();
        // Calculando os vetores da base da câmera
        Vector3 i_c = viewUp.cross(k_c).normalize();   // Vetor "direita"
        Vector3 j_c = k_c.cross(i_c).normalize();      // Vetor "para cima"

        // Matriz de rotação (orientação da câmera)
        double[][] rotationMatrix = {
            { i_c.getX(), i_c.getY(), i_c.getZ(), 0 },
            { j_c.getX(), j_c.getY(), j_c.getZ(), 0 },
            { k_c.getX(), k_c.getY(), k_c.getZ(), 0 },
            { 0,          0,          0,          1 }
        };

        // Matriz de translação (posição da câmera)
        double[][] translationMatrix = {
            { 1, 0, 0, -posEye.getX() },
            { 0, 1, 0, -posEye.getY() },
            { 0, 0, 1, -posEye.getZ() },
            { 0, 0, 0,  1 }
        };

        // Multiplicando as duas matrizes (translação e rotação)
        return multiplyMatrices(rotationMatrix, translationMatrix);
    }

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
        return viewUp;
    }

    public void setViewUp(Vector3 viewUp) {
        this.viewUp = viewUp;
    }

    public Vector3 getK_c() {
        return k_c;
    }

    public void setK_c(Vector3 k_c) {
        this.k_c = k_c;
    }


}