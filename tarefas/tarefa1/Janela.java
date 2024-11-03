import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Janela {

    private double w; // largura da janela
    private double h; // altura da janela
    private double d; // distância do olho do pintor até a janela

    private int nCol; // numero de colunas do galinheiro
    private int nLin; // numero de linhas do galinheiro

    private BufferedImage canvas; // tela a ser pintada

    private double Dx;  // largura do retangulo 
    private double Dy;  // altura do retangulo

    private ArrayList<Esfera> esferas;

    private Color bgColor;

    public Janela(double w, double h, double d, int nCol, int nLin, ArrayList<Esfera> esferas) {
        this.w = w;
        this.h = h;
        this.d = d;
        this.nCol = nCol;
        this.nLin = nLin;
        this.canvas = new BufferedImage(nCol, nLin, BufferedImage.TYPE_INT_RGB);
        Dx = w / nCol;
        Dy = h / nLin;
        
        this.esferas = esferas;
        this.bgColor = new Color(100, 100, 100); // cinza
        
        pintarCanvas();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(nCol, nLin);
        frame.add(new JLabel(new ImageIcon(canvas)));
        frame.setVisible(true);
    }

    public Boolean interseccionaEsfera(double dx, double dy, double dz, Esfera esfera) {
        double a = dx * dx + dy * dy + dz * dz;                         //dr * dr
            double b = 2 * (dx * 0 + dy * 0 + dz * (0 - esfera.getZ())); // 2*(w * dr)
            double cEsfera = (0 - esfera.getX()) * (0 - esfera.getX()) +              // cEsfera = (w*w) - R²
                                (0 - esfera.getY()) * (0 - esfera.getY()) + 
                                (0 - esfera.getZ()) * (0 - esfera.getZ()) - 
                                esfera.getR() * esfera.getR();  
        

            // Discriminante para determinar interseção
            double discriminante = b * b - 4 * a * cEsfera;

            // Verifica se o raio intersecciona a esfera
            if (discriminante >= 0) {
                // O raio tem interseção com a esfera
                return true;
            } else {
                // O raio não tem interseção com a esfera
                return false;
            }
    }

    public BufferedImage pintarCanvas(){
        for (int l = 0; l < this.nLin; l++){
            double y = this.h / 2.0 - this.Dy/2.0 - l * this.Dy;
            for(int c = 0; c < this.nCol ; c++){
                double x = -this.w / 2.0 + this.Dx / 2.0 + c*this.Dx;
                double dz = -this.d;
                for ( Esfera esfera : esferas ) {
                    if(interseccionaEsfera(x, y, dz, esfera) && this.canvas.getRGB(c, l) == bgColor.getRGB()){
                        // pintar o pixel
                        this.canvas.setRGB(c, l, esfera.getCor());
                    } else{
                        // pintar background
                        if (this.canvas.getRGB(c, l) != bgColor.getRGB()) 
                            this.canvas.setRGB(c, l, bgColor.getRGB());
                    }
                }
            }
        }
        return this.canvas;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public int getnCol() {
        return nCol;
    }

    public void setnCol(int nCol) {
        this.nCol = nCol;
    }

    public int getnLin() {
        return nLin;
    }

    public void setnLin(int nLin) {
        this.nLin = nLin;
    }

    public BufferedImage getCanvas() {
        return canvas;
    }

    public void setCanvas(BufferedImage canvas) {
        this.canvas = canvas;
    }

    public double getDx() {
        return Dx;
    }

    public void setDx(double dx) {
        Dx = dx;
    }

    public double getDy() {
        return Dy;
    }

    public void setDy(double dy) {
        Dy = dy;
    }

}
