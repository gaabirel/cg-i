package src.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;


public class Janela extends JFrame {
    
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

    
    public void adicionarComponentes(){
        renderPanel = new RenderPanel();
        renderPanel.setFocusable(true);
        add(renderPanel); 
    }
    
    public void setCanvas(BufferedImage canvas){
        renderPanel.setCanvas(canvas);
    }
    
    public RenderPanel getRenderPanel(){
        return this.renderPanel;
    }
    
    //classe do painel que vai suportar o canvas e os listeners
    public class RenderPanel extends JPanel {
        private BufferedImage canvas;
        
        public void setCanvas(BufferedImage canvas){
            this.canvas = canvas;
        }
        public BufferedImage getCanvas(){
            return this.canvas;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(renderPanel.getCanvas(), 0, 0, null);
        }
    }
}
