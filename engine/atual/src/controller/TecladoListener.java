package src.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import src.model.interseccao.Intersectable;
import src.model.interseccao.Vector3;
import src.model.objetos.*;




public class TecladoListener extends KeyAdapter {
    private ArrayList<Intersectable> objetos;  // Objetos que serão movimentados
    private MainController mainController;
    private Renderizador renderizador;

    // Construtor para inicializar os objetos e a posição da luz
    public TecladoListener(MainController mainController) {
        this.renderizador = mainController.getRenderizador();
        this.objetos = renderizador.getObjetos();
        this.mainController = mainController;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        double deslocamento = 0.1; // Distância para mover a cada tecla

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> objetos.get(0).mover(0, deslocamento, 0); 
            case KeyEvent.VK_DOWN -> objetos.get(0).mover(0, -deslocamento, 0);
            case KeyEvent.VK_LEFT -> objetos.get(0).mover(-deslocamento, 0, 0);
            case KeyEvent.VK_RIGHT -> objetos.get(0).mover(deslocamento, 0, 0);
            case KeyEvent.VK_W -> objetos.get(0).mover(0, 0, deslocamento);
            case KeyEvent.VK_S -> objetos.get(0).mover(0, 0, -deslocamento);
          
            case KeyEvent.VK_N -> ((Cilindro) objetos.get(0)).alterarEixo(new Vector3(0, -0.1, 0));
            case KeyEvent.VK_M -> ((Cilindro) objetos.get(0)).alterarEixo(new Vector3(0, 0.1, 0));
        };
        mainController.atualizarCena();
    }

}