package src.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import javax.swing.*;
import src.controller.*;
import src.model.interseccao.Intersectable;
import src.model.interseccao.Light;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;


public class Janela extends JFrame {
    
    private double w; // largura da janela
    private double h; // altura da janela
    private double d; // distância do olho do pintor até a janela

    private int nCol; // número de colunas do canvas
    private int nLin; // número de linhas do canvas

    private BufferedImage canvas; // tela a ser pintada
    private double Dx; // largura do retângulo
    private double Dy; // altura do retângulo

    private ArrayList<Intersectable> objetos;
    private ArrayList<Light> luzes;

    private RenderPanel renderPanel; // Painel customizado para renderização

    public ProcessadorInterseccoes processador;
    
    public Vector3 origemRaio;

    public Janela(double w, double h, double d, int nCol, int nLin, Cena cena) {
        //Config da window na cena
        this.w = w;
        this.h = h;
        this.d = d;
        this.nCol = nCol;
        this.nLin = nLin;
        this.canvas = new BufferedImage(nCol, nLin, BufferedImage.TYPE_INT_RGB);
        this.Dx = w / nCol;
        this.Dy = h / nLin;
        origemRaio = new Vector3(0, 0, 0); // origem do raio saindo do olho do observador

        //Objetos e cor de fundo
        this.objetos = cena.getObjetos();
        this.luzes = cena.getLuzes();

        //classe que vai processar as interseccoes dos objetos
        this.processador = new ProcessadorInterseccoes(objetos, luzes);   

        //Configura o JFrame
        setTitle("Esfera lumiada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(nCol, nLin);
        
        adicionarComponentes();
        setVisible(true);

    }

    public void adicionarComponentes(){
        //Painel customizado para renderizar a imagem
        renderPanel = new RenderPanel();
        renderPanel.setFocusable(true);

        //Um listener do teclado para realizar o movimento das esferas
        TecladoListener tecladoListener = new TecladoListener(objetos, luzes.get(0).getPosicao(), this);
        renderPanel.addKeyListener(tecladoListener);
        renderPanel.setLayout(new BoxLayout(renderPanel, BoxLayout.PAGE_AXIS));

        MouseListener mouseListener = new MouseListener(this, objetos);
        renderPanel.addMouseListener(mouseListener);

        /*
        String texto1 = "Bola branca: movimento com setinhas, e profundidade com W e S ";
        String texto2 = "Luz: T, G, F, H  profundidade: U, I";

        int tamanhoFonte = 18;
        //cria botao e labels 
        TextoNaTela controle1 = new TextoNaTela(texto1, tamanhoFonte);
        TextoNaTela controle2 = new TextoNaTela(texto2, tamanhoFonte);
        TextoNaTela label = new TextoNaTela("Bolas e Planos", 24);
        Button botao = new Button(this, "Clique aqui", label, renderPanel, luzes);
        TextField caixa = new TextField(20);
        JLabel LadoTexto = new JLabel();
    
        // Cria um painel secundário com BoxLayout horizontal
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));
        
        // Adiciona componentes ao subPanel
        subPanel.add(caixa);
        subPanel.add(new JButton("Botão 1"));
        subPanel.add(new JButton("Botão 2"));

        LadoTexto.setLayout(new BoxLayout(LadoTexto, BoxLayout.LINE_AXIS));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  
        controle1.setAlignmentX(Component.CENTER_ALIGNMENT);  
        controle2.setAlignmentX(Component.CENTER_ALIGNMENT); 
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        //adicionar tudo que foi criado
        renderPanel.add(label);
        renderPanel.add(controle1);
        renderPanel.add(controle2);
        for(int i = 0; i < 5; i++){
            renderPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        }
        renderPanel.add(botao);
        renderPanel.add(subPanel);
        */
        pintarCanvas(); // Desenha a cena inicial
        add(renderPanel); // Adiciona o painel ao JFrame
    
    }
    public void pintarCanvas() {
    // Pegando o Raster pra manipular os pixels diretamente
    WritableRaster raster = canvas.getRaster();
    int[] pixelColor = new int[3]; // Vetor pra pegar as cores R G B
   

    for (int l = 0; l < nLin; l++) {
        double y = h / 2.0 - Dy / 2.0 - l * Dy; // Posiçao do meio da altura do retangulo
        for (int c = 0; c < nCol; c++) {
            double x = -w / 2.0 + Dx / 2.0 + c * Dx; // posiçao do meio da largura do retangulo
            double dz = -d; // distancia da tela projetada pro olho do observador

            Vector3 direcaoRaio = new Vector3(x, y, dz); // direcao do raio na direcao do centro do retangulo
            Ray raio = new Ray(this.origemRaio, direcaoRaio);

            // Convertendo a cor do resultado pra R G B
            int color = processador.interseccionarObjetos(raio);
            pixelColor[0] = (color >> 16) & 0xFF; // Red component
            pixelColor[1] = (color >> 8) & 0xFF;  // Green component
            pixelColor[2] = color & 0xFF;         // Blue component

            // Set the pixel color in the raster
            raster.setPixel(c, l, pixelColor);
        }
    }

}

    public double getDistancia(){
        return this.d;
    }
    public double getLargura(){
        return this.w;
    }
    public double getAltura(){
        return this.h;
    }

    private class RenderPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(canvas, 0, 0, null); // Desenha o canvas atualizado

        }

    }


}
