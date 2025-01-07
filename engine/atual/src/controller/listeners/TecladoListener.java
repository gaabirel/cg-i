package src.controller.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import src.controller.MainController;
import src.controller.renderizacao.Renderizador;
import src.model.interseccao.Vector3;
import src.model.objetos.*;




public class TecladoListener extends KeyAdapter {
    private ArrayList<Intersectable> objetos;  // Objetos que serão movimentados
    private MainController mainController;
    private Renderizador renderizador;
    private int idx_objeto_deslocado;
    // Construtor para inicializar os objetos e a posição da luz
    public TecladoListener(MainController mainController) {
        this.renderizador = mainController.getRenderizador();
        this.objetos = renderizador.getObjetos();
        this.mainController = mainController;
        this.idx_objeto_deslocado = 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        checarMovimento(e);
        checarRotacao(e);
        mainController.atualizarCena();
    }

    public void checarMovimento(KeyEvent e){
        double deslocamento = 0.1; // Distância para mover a cada tecla
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> objetos.get(idx_objeto_deslocado).transladar(0, deslocamento, 0); 
            case KeyEvent.VK_DOWN -> objetos.get(idx_objeto_deslocado).transladar(0, -deslocamento, 0);
            case KeyEvent.VK_LEFT -> objetos.get(idx_objeto_deslocado).transladar(-deslocamento, 0, 0);
            case KeyEvent.VK_RIGHT -> objetos.get(idx_objeto_deslocado).transladar(deslocamento, 0, 0);
            case KeyEvent.VK_W -> objetos.get(idx_objeto_deslocado).transladar(0, 0, deslocamento);
            case KeyEvent.VK_S -> objetos.get(idx_objeto_deslocado).transladar(0, 0, -deslocamento);
        };
    }

    public void checarRotacao(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_N -> ( objetos.get(idx_objeto_deslocado)).rotacionar(2, new Vector3(1, 0, 0));
            case KeyEvent.VK_M -> ( objetos.get(idx_objeto_deslocado)).rotacionar(2, new Vector3(0, 0, 1));
        }
    }

}