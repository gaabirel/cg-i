package src.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import src.model.Intersectable;
import src.model.Vector3;
import src.view.Janela;



public class TecladoListener extends KeyAdapter {
    private ArrayList<Intersectable> objetos;  // Objetos que serão movimentados
    private Vector3 luzPos;           // Posição da luz
    private Janela janela;

    // Construtor para inicializar os objetos e a posição da luz
    public TecladoListener(ArrayList<Intersectable> objetos, Vector3 luzPos, Janela janela) {
        this.objetos = objetos;
        this.luzPos = luzPos;
        this.janela = janela;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        double deslocamentoLuz = 0.1;
        double deslocamento = 0.1; // Distância para mover a cada tecla

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                objetos.get(0).mover(0, deslocamento, 0);
                break;
            case KeyEvent.VK_DOWN:
                objetos.get(0).mover(0, -deslocamento, 0);
                break;
            case KeyEvent.VK_LEFT:
                objetos.get(0).mover(-deslocamento, 0, 0);
                break;
            case KeyEvent.VK_RIGHT:
                objetos.get(0).mover(deslocamento, 0, 0);
                break;
            case KeyEvent.VK_W:
                objetos.get(0).mover(0, 0, deslocamento);
                break;
            case KeyEvent.VK_S:
                objetos.get(0).mover(0, 0, -deslocamento);
                break;
            case KeyEvent.VK_T:
                luzPos.y += deslocamentoLuz;
                break;
            case KeyEvent.VK_G:
                luzPos.y -= deslocamentoLuz;
                break;
            case KeyEvent.VK_H:
                luzPos.x += deslocamentoLuz;
                break;
            case KeyEvent.VK_F:
                luzPos.x -= deslocamentoLuz;
                break;
            case KeyEvent.VK_U:
                luzPos.z -= deslocamentoLuz;
                break;
            case KeyEvent.VK_I:
                luzPos.z += deslocamentoLuz;
                break;
        }
        janela.pintarCanvas();
        janela.repaint();
    }

}