import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * Feito by Marcos Antônio and Gabriel, primeira atividade de Computação Gráfica
 * 
 */

public class RayCastingEsfera {
    public static void main(String[] args) {
        // Definições dos parâmetros
        double wJanela = 2.0; // largura da janela em metros
        double hJanela = 2.0; // altura da janela em metros
        double dJanela = 5.0; // distância da janela em metros
        double rEsfera = 1.0; // raio da esfera
        double zCentroEsfera = -15.0; // posição z do centro da esfera

        int nCol = 800; // número de colunas na tela de mosquito
        int nLin = 800; // número de linhas na tela de mosquito

        // Cores
        Color esfColor = new Color(255, 0, 0); // cor da esfera (vermelho)
        Color bgColor = new Color(100, 100, 100); // cor do background (cinza)

        // Criação do "canvas" para a imagem
        BufferedImage canvas = new BufferedImage(nCol, nLin, BufferedImage.TYPE_INT_RGB);

        // Dimensões dos retângulos na tela de mosquito
        double Dx = wJanela / nCol;
        double Dy = hJanela / nLin;

        // Loop para percorrer cada retângulo na tela de mosquito
        for (int l = 0; l < nLin; l++) {
            // Coordenada y do centro do retângulo
            double y = hJanela / 2.0 - Dy / 2.0 - l * Dy;
            for (int c = 0; c < nCol; c++) {
                // Coordenada x do centro do retângulo
                double x = -wJanela / 2.0 + Dx / 2.0 + c * Dx;

                // Definindo o ponto do raio que parte do olho do pintor (0,0,0) e passa por (x, y, -dJanela)
                double dx = x;
                double dy = y;
                double dz = -dJanela;

                //Olho do pintor (px, py, pz)
                double px = 0;
                double py = 0;
                double pz = 0;
                double esferaX = 0;
                double esferaY = 0;

                // Parâmetros para verificar a interseção do raio com a esfera
                double a = dx * dx + dy * dy + dz * dz;                         //dr * dr
                double b = 2 * (dx * px + dy * py + dz * (pz - zCentroEsfera)); // 2*(w * dr)
                double cEsfera = (px - esferaX) * (px - esferaX) +              // cEsfera = (w*w) - R²
                                 (py - esferaY) * (py - esferaY) + 
                                 (pz - zCentroEsfera) * (pz - zCentroEsfera) - 
                                 rEsfera * rEsfera;  
          

                // Discriminante para determinar interseção
                double discriminante = b * b - 4 * a * cEsfera;

                // Verifica se o raio intersecciona a esfera
                if (discriminante >= 0) {
                    // O raio tem interseção com a esfera
                    canvas.setRGB(c, l, esfColor.getRGB());
                } else {
                    // O raio não tem interseção com a esfera
                    canvas.setRGB(c, l, bgColor.getRGB());
                }
            }
        }

        // Exibição do canvas em uma janela
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(nCol, nLin);
        frame.add(new JLabel(new ImageIcon(canvas)));
        frame.setVisible(true);
    }
}