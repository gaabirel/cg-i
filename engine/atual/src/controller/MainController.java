package src.controller;

import java.awt.image.BufferedImage;

import src.controller.listeners.MouseListener;
import src.controller.listeners.TecladoListener;
import src.controller.renderizacao.Renderizador;
import src.view.Janela;

public class MainController {
    /* O canvas é um objeto de imagem compartilhado entre a janela e o renderizador */
    Janela janela;
    Renderizador renderizador;
    BufferedImage canvas; 

    public MainController(Janela janela, Renderizador renderizador){
        this.janela = janela;
        this.renderizador = renderizador;

        //Inicializando a cena e repassando para a janela
        this.canvas = renderizador.getCanvas();
        this.janela.setCanvas(canvas);
        atualizarCena();

        janela.addMouseListener(new MouseListener(this));
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
