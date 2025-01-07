package src.controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import src.controller.listeners.MouseListener;
import src.controller.listeners.TecladoListener;
import src.controller.renderizacao.Renderizador;
import src.view.Janela;

public class MainController {
    /* O canvas é um objeto de imagem compartilhado entre a janela e o renderizador */
    Janela janela;
    Renderizador renderizador;
    BufferedImage canvas; 

    //painel que vai suportar o canvas para por na janela principal
    public class Painel extends JPanel{
        BufferedImage imagem;
        public Painel(BufferedImage imagem){
            this.imagem = imagem;
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.imagem, 0, 0, null);
        }
        
    }

    public MainController(Janela janela, Renderizador renderizador){
        this.janela = janela;
        this.renderizador = renderizador;

        //Inicializando a cena e repassando para a janela
        this.canvas = renderizador.getCanvas();
        janela.setCanvas(canvas);
        atualizarCena();
       
        //Adicionando os listeners
        janela.getRenderPanel().addMouseListener(new MouseListener(this));
        janela.getRenderPanel().addKeyListener(new TecladoListener(this));
    }
    
    public void atualizarCena(){
        renderizador.renderizar();
        janela.repaint();
    }

    public Janela getJanela(){
        return this.janela;
    }

    public Renderizador getRenderizador(){
        return this.renderizador;
    }
}
