package src.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;
import src.view.Janela;
import src.view.OpcaoJanela;
import src.model.interseccao.Intersectable;


import java.util.List;

public class MouseListener extends MouseAdapter {

    private Janela janela;  // Referência para a janela
    public MouseListener(Janela janela, List<Intersectable> objetos) {
        this.janela = janela;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        pick(mouseX, mouseY);  // Chama a função pick quando o mouse é pressionado
    }

    // Função pick para detectar qual objeto foi clicado
    public void pick(int mouseX, int mouseY) {
        Ray raio = criarRaio(mouseX, mouseY);  // Cria o raio a partir do clique
        Intersectable objeto = janela.processador.encontrarObjetoMaisProximo(raio);
        System.out.println("Objeto clicado: " + objeto);
        if (objeto != null) {  // Verifica se o raio intersecta com o objeto
            OpcaoJanela opcaoJanela = new OpcaoJanela(janela, objeto);
            opcaoJanela.setVisible(true);  // Torna a janela visível
        }
              
    }

    // Função que cria o raio a partir das coordenadas do mouse
    private Ray criarRaio(int mouseX, int mouseY) {
        double Dx = janela.getLargura() / janela.getNcol();
        double Dy = janela.getAltura() / janela.getNlin();
        double y = janela.getAltura() / 2.0 - Dy / 2.0 - mouseY * Dy; // Posiçao do meio da altura do retangulo
        double x = -janela.getLargura() / 2.0 + Dx / 2.0 + mouseX * Dx;
        Vector3 direcaoRaio = new Vector3(x, y, -janela.getDistancia()).normalize();
        Vector3 origemRaio = new Vector3(0, 0, 0);  // Origem do raio (posição da câmera)

        return new Ray(origemRaio, direcaoRaio);  // Retorna o raio gerado
    }

}
