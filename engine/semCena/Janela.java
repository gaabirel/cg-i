import javax.swing.*;

import src.Intersectable;
import src.Light;
import src.ProcessadorInterseccoes;
import src.Ray;
import src.ResultadoIntersecao;
import src.TecladoListener;
import src.Vector3;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Janela extends JFrame {
    
    private double w; // largura da janela
    private double h; // altura da janela
    private double d; // distância do olho do pintor até a janela

    private int nCol; // número de colunas do canvas
    private int nLin; // número de linhas do canvas

    private BufferedImage canvas; // tela a ser pintada
    private double Dx; // largura do retângulo
    private double Dy; // altura do retângulo

    private Intersectable[] objetos;
    private Light[] luzes;

    private RenderPanel renderPanel; // Painel customizado para renderização

    ProcessadorInterseccoes processador;
    
    public Janela(double w, double h, double d, int nCol, int nLin, Intersectable[] objetos, Light[] luzes) {
        //Config da window na cena
        this.w = w;
        this.h = h;
        this.d = d;
        this.nCol = nCol;
        this.nLin = nLin;
        this.canvas = new BufferedImage(nCol, nLin, BufferedImage.TYPE_INT_RGB);
        this.Dx = w / nCol;
        this.Dy = h / nLin;
        //Objetos e cor de fundo
        this.objetos = objetos;
        this.processador = new ProcessadorInterseccoes();   
        this.luzes = luzes;

        //Configura o JFrame
        setTitle("Esfera lumiada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(nCol, nLin);

        //Painel customizado para renderizar a imagem
        renderPanel = new RenderPanel();
        renderPanel.setFocusable(true);

        //Um listener do teclado para realizar o movimento das esferas
        TecladoListener tecladoListener = new TecladoListener(objetos, luzes[0].getPosicao(), this);
        renderPanel.addKeyListener(tecladoListener);

        pintarCanvas(); // Desenha a cena inicial
        add(renderPanel); // Adiciona o painel ao JFrame
        setVisible(true);
    }

    public void pintarCanvas() {

        for (int l = 0; l < nLin; l++) {
            double y = h / 2.0 - Dy / 2.0 - l * Dy;      //pos no meio da altura do retangulo
            for (int c = 0; c < nCol; c++) {
                double x = -w / 2.0 + Dx / 2.0 + c * Dx; //pos no meio da largura do retangulo
                double dz = -d;                          //distancia da tela de mosquito pro olho do pintor   

                Vector3 origemRaio = new Vector3(0,0,0); //origem do raio partindo olho do pintor
                Vector3 direcaoRaio = new Vector3(x, y, dz);   //direcao do raio ao centro do retangulo
                Ray raio = new Ray(origemRaio, direcaoRaio);    
                ResultadoIntersecao resultado = processador.interseccionaObjetos(this.objetos, raio, luzes );
                canvas.setRGB(c, l, resultado.getColor());
            }
        }
    }

    private class RenderPanel extends JPanel {
        private final Font textFont;
        private final String[] textos;
        private final int xPosition = 10; // Posição X do texto
        private int yPosition; // Posição Y inicial do texto
    
        public RenderPanel() {
            textFont = new Font("Arial", Font.BOLD, 20); // Inicializa a fonte
            textos = new String[]{
                "Controles:",
                "Bola branca: movimento com setinhas, e profundidade com W e S",
                "Luz: T, G, F, H  profundidade: U, I",
            };

            yPosition = 30; // Inicializa a posição Y
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(canvas, 0, 0, null); // Desenha o canvas atualizado

            g.setFont(textFont); // Fonte do texto
            // Desenhar o texto no topo
            for (int i = 0; i < textos.length; i++) {
                g.drawString(textos[i], xPosition, (i+1)*yPosition);
            }
            
        }

    }

}
