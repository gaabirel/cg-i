package src.controller.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import src.controller.MainController;
import src.controller.renderizacao.Renderizador;
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
            checarMovimento(e);
            checarRotacao(e);
            checarEscala(e);
            checarCisalhamento(e);
            checarEspelhamento(e);
            checarMovimentoCamera(e);
            checarOlharCamera(e);
            checarZoom(e);
            checarScreenshot(e);
            mainController.atualizarCena();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void checarScreenshot(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F12) { // Exemplo: Pressionar F12 para tirar screenshot
            BufferedImage imagem = renderizador.getCanvas();
            salvarImagem(imagem);
        }
    }

    private void salvarImagem(BufferedImage imagem) {
        // Define o diretório onde a imagem será salva
        String pastaDestino = "../../imagens/";
        File diretorio = new File(pastaDestino);

        // Cria a pasta caso não exista
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        // Gera um nome único para a imagem baseado na data/hora
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nomeArquivo = "screenshot_" + timestamp + ".png";

        File arquivo = new File(diretorio, nomeArquivo);

        try {
            // Salva a imagem no formato PNG
            ImageIO.write(imagem, "png", arquivo);
            System.out.println("Screenshot salva em: " + arquivo.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void checarZoom(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_0 -> renderizador.zoomIn(1.1);
            case KeyEvent.VK_MINUS -> renderizador.zoomOut(0.9);
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
        double anguloRotacao = 30;
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

    public void checarMovimentoCamera(KeyEvent e) {
        double deslocamento = 0.1;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_6 -> renderizador.transladarCamera(0, deslocamento, 0);
            case KeyEvent.VK_5 -> renderizador.transladarCamera(0, -deslocamento, 0);
            case KeyEvent.VK_3 -> renderizador.transladarCamera(-deslocamento, 0, 0);
            case KeyEvent.VK_4 -> renderizador.transladarCamera(deslocamento, 0, 0);
            case KeyEvent.VK_7 -> renderizador.transladarCamera(0, 0, deslocamento);
            case KeyEvent.VK_8 -> renderizador.transladarCamera(0, 0, -deslocamento);
        }

    }
    public void checarOlharCamera(KeyEvent e){
        double anguloRotacao = 5;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_K -> renderizador.mudarLookAt(0, anguloRotacao, 0);
            case KeyEvent.VK_L -> renderizador.mudarLookAt(0, -anguloRotacao, 0);
        }
    }
}