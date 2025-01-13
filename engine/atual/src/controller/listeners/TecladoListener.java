package src.controller.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import src.controller.MainController;
import src.controller.renderizacao.Renderizador;
import src.model.Camera;
import src.model.interseccao.Vector3;
import src.model.objetos.*;




public class TecladoListener extends KeyAdapter {
    private ArrayList<Intersectable> objetos;  // Objetos que serão movimentados
    private MainController mainController;
    private Renderizador renderizador;
    private int idxObjetoTransformado;
    // Construtor para inicializar os objetos e a posição da luz
    public TecladoListener(MainController mainController) {
        this.renderizador = mainController.getRenderizador();
        this.objetos = renderizador.getObjetos();
        this.mainController = mainController;
        this.idxObjetoTransformado = 0;
    }

    public void setIdxObjetoTransformado(int idx){
        this.idxObjetoTransformado = idx;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        // fechar o programa caso o usuario aperte ESC
        if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) System.exit(0);

        try {
            System.out.println(idxObjetoTransformado);
            checarMovimento(e);
            checarRotacao(e);
            checarEscala(e);
            checarCisalhamento(e);
            checarEspelhamento(e);
            checarCameraTranslacao(e);
            mainController.atualizarCena();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void checarMovimento(KeyEvent e){
        double deslocamento = 0.1; 
        Intersectable objeto = objetos.get(idxObjetoTransformado);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP    -> objeto.transladar(0, deslocamento, 0); 
            case KeyEvent.VK_DOWN  -> objeto.transladar(0, -deslocamento, 0);
            case KeyEvent.VK_LEFT  -> objeto.transladar(-deslocamento, 0, 0);
            case KeyEvent.VK_RIGHT -> objeto.transladar(deslocamento, 0, 0);
            case KeyEvent.VK_W     -> objeto.transladar(0, 0, deslocamento);
            case KeyEvent.VK_S     -> objeto.transladar(0, 0, -deslocamento);
        };
    }

    public void checarRotacao(KeyEvent e){
        double anguloRotacao = 5;
        Intersectable objeto = objetos.get(idxObjetoTransformado);
        switch (e.getKeyCode()){
            case KeyEvent.VK_N -> objeto.rotacionar(anguloRotacao, new Vector3(1, 0, 0));
            case KeyEvent.VK_M -> objeto.rotacionar(anguloRotacao, new Vector3(0, 0, 1));
            case KeyEvent.VK_B -> objeto.rotacionar(anguloRotacao, new Vector3(0, 1, 0));
        }
    }

    public void checarEscala(KeyEvent e){
        Intersectable objeto = objetos.get(idxObjetoTransformado);
        switch (e.getKeyCode()){
            case KeyEvent.VK_X -> objeto.escala(0.8, 0.8, 0.8);
            case KeyEvent.VK_Z -> objeto.escala(1.2, 1.2, 1.2);
        }
    }

    public void checarCisalhamento(KeyEvent e) {
        Intersectable objeto = objetos.get(idxObjetoTransformado);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q -> objeto.cisalhar(0.3, 0.2, 0, 0, 0, 0);
            case KeyEvent.VK_A -> objeto.cisalhar(-0.3, -0.2, 0, 0, 0, 0);
        }
    }

    public void checarEspelhamento(KeyEvent e) {
        Intersectable objeto = objetos.get(idxObjetoTransformado);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_I -> objeto.espelhar("yz"); // Espelhar em relação ao plano YZ
            case KeyEvent.VK_O -> objeto.espelhar("zx"); // Espelhar em relação ao plano ZX
            case KeyEvent.VK_P -> objeto.espelhar("xy"); // Espelhar em relação ao plano XY
        }
    }

    public void checarCameraTranslacao(KeyEvent e){
        Camera camera  = renderizador.getCamera();
        
        switch (e.getKeyCode()){
            case KeyEvent.VK_4 -> camera.setPosEye(camera.getPosEye().add(new Vector3(0.1, 0, 0)));
            case KeyEvent.VK_3 -> camera.setPosEye(camera.getPosEye().add(new Vector3(-0.1, 0, 0)));
        }
        renderizador.getProcessador().setObjetos(camera.aplicarMatrixCamera(objetos));
    }

}