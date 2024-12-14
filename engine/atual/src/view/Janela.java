package src.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class Janela extends JFrame {
    
    private BufferedImage canvas; // tela a ser pintadA
    private RenderPanel renderPanel; // Painel customizado para por o canvas

    public Janela(int nCol, int nLin){
        
        //Configura o JFrame
        setSize(nCol, nLin);
        setTitle("Esfera lumiada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        adicionarComponentes();
        setVisible(true);

    }

    public RenderPanel getRenderPanel(){
        return this.renderPanel;
    }

    public void setCanvas(BufferedImage canvas){
        this.canvas = canvas;
    }

    public void adicionarComponentes(){
        //Painel customizado para renderizar a imagem
        renderPanel = new RenderPanel();
        renderPanel.setFocusable(true);
        add(renderPanel); 
    
    }

    public class RenderPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(canvas, 0, 0, null); // Desenha o canvas atualizado
        }
    }
}
