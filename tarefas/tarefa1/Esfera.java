import java.awt.Color;

public class Esfera {
    
    private double r; // raio da esfera
    private double z; // posição z do centro da esfera
    private double x; // posição x da esfera
    private double y; // posição y da esfera

    private Color cor; // cor da esfera

    public Esfera(double r, double z, double x, double y, Color cor) {
        this.r = r;
        this.z = z;
        this.x = x;
        this.y = y;
        this.cor = cor;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getCor() {
        return cor.getRGB();
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    

}
