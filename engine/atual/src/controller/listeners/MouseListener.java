package src.controller.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.view.Janela;
import src.view.OpcaoJanela;
import src.controller.MainController;
import src.controller.renderizacao.Renderizador;
import src.model.interseccao.Intersectable;

public class MouseListener extends MouseAdapter {

    private Renderizador renderizador;
    private Janela janela;
    private MainController mainController;

    public MouseListener(MainController mainController) {
        this.renderizador = mainController.getRenderizador();
        this.janela = mainController.getJanela();
        this.mainController = mainController;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        pick(mouseX, mouseY);  // Chama a função pick quando o mouse é pressionado
    }

    //Função pick para detectar qual objeto foi clicado
    public void pick(int mouseX, int mouseY) {
        Ray raio = criarRaio(mouseX, mouseY);  
        Intersectable objeto = renderizador.processador.encontrarObjetoMaisProximo(raio).getObject();
        System.out.println("Objeto clicado: " + objeto);
        if (objeto != null) { 
            @SuppressWarnings("unused")
            OpcaoJanela opcaoJanela = new OpcaoJanela(janela, objeto, mainController);
        }
              
    }

    // Função que cria o raio a partir das coordenadas do mouse
    private Ray criarRaio(int mouseX, int mouseY) {
        double Dx = renderizador.getLargura() / renderizador.getNcol();
        double Dy = renderizador.getAltura() / renderizador.getNlin();
        
        double y = (renderizador.getAltura() / 2.0) - (Dy / 2.0) - mouseY * Dy; // Posiçao do meio da altura do retangulo
        double x = (-renderizador.getLargura() / 2.0) + (Dx / 2.0) + mouseX * Dx;
        Vector3 direcaoRaio = new Vector3(x, y, -renderizador.getDistancia()).normalize();
        Vector3 origemRaio = new Vector3(0, 0, 0);  // Origem do raio (posição da câmera)

        return new Ray(origemRaio, direcaoRaio);  // Retorna o raio gerado
    }

}
