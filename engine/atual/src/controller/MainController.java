package src.controller;

import java.awt.image.BufferedImage;
import src.controller.listeners.MouseListener;
import src.controller.listeners.TecladoListener;
import src.controller.renderizacao.Renderizador;
import src.view.Janela;
import src.view.MenuBarraSuperior;

public class MainController {
    /* O canvas é um objeto de imagem compartilhado entre a janela e o renderizador */
    Janela janela;
    private Renderizador renderizador;
    private BufferedImage canvas; 

    private MouseListener mouseListener;
    private TecladoListener tecladoListener;

    public MainController(Janela janela, Renderizador renderizador){
        this.janela = janela;
        this.renderizador = renderizador;

        //Inicializando a cena e repassando para a janela
        this.canvas = renderizador.getCanvas();
        janela.setCanvas(canvas);
        atualizarCena();
       
        //Adicionando os listeners
        MouseListener mouseListener = new MouseListener(this);
        this.mouseListener = mouseListener;
        janela.getRenderPanel().addMouseListener(mouseListener);

        TecladoListener tecladoListener = new TecladoListener(this);
        this.tecladoListener = tecladoListener;
        janela.getRenderPanel().addKeyListener(tecladoListener);

        //Adicionando a barra de menu
        janela.setJMenuBar(new MenuBarraSuperior(this));

        //Mostrando a janela finalizada
        janela.setVisible(true);
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

    public BufferedImage getCanvas(){
        return this.canvas;
    }

    public void setCanvas(BufferedImage canvas){
        this.canvas = canvas;
    }

    public MouseListener getMouseListener(){
        return this.mouseListener;
    }

    public TecladoListener getTecladoListener(){
        return this.tecladoListener;
    }

}
