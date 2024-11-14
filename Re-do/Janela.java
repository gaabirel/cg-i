import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

    private Esfera[] esferas;
    private Color bgColor;
    private RenderPanel renderPanel; // Painel customizado para renderização

    //parametros da luz
    private Vector3 luzPos;             //posiçao da fonte de luz
    private double kd;                   //coeficiente da reflexividade do material para a luz difusa
    private double luzIntensidade;       //intensidade da luz
    private double energiaLuzAmbiente;   // Fator de luz ambiente
    private double n;                    //expoente de brilho, ou coeficiente de especularidade
    private double ks;                   //coeficiente da reflexividade do material para a luz especular

    
    public Janela(double w, double h, double d, int nCol, int nLin, Esfera[] esferas) {
        this.w = w;
        this.h = h;
        this.d = d;
        this.nCol = nCol;
        this.nLin = nLin;
        this.canvas = new BufferedImage(nCol, nLin, BufferedImage.TYPE_INT_RGB);
        this.Dx = w / nCol;
        this.Dy = h / nLin;
        this.esferas = esferas;
        this.bgColor = new Color(100, 100, 100); // cor de fundo (cinza)
        this.luzPos = new Vector3(0, 2, 3);
        this.luzIntensidade = 1.0; 
        this.kd = 0.7; 
        this.energiaLuzAmbiente = 0.3; 
        this.ks = 0.2;
        this.n = 10;
        // Configura o JFrame
        setTitle("Esfera lumiada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(nCol, nLin);

        // Painel customizado para renderizar a imagem
        renderPanel = new RenderPanel();
        renderPanel.setFocusable(true);

        //Lendo as teclas do teclado para realizar o movimento das esferas
        renderPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                double deslocamentoLuz = 0.1;
                double deslocamento = 0.1; // distância para mover a cada tecla
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        esferas[0].mover(0, deslocamento, 0);
                        break;
                    case KeyEvent.VK_DOWN:
                        esferas[0].mover(0, -deslocamento, 0);
                        break;
                    case KeyEvent.VK_LEFT:
                        esferas[0].mover(-deslocamento, 0, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        esferas[0].mover(deslocamento, 0, 0);
                        break;
                    case KeyEvent.VK_W:
                        esferas[0].mover( 0, 0, deslocamento);
                        break;
                    case KeyEvent.VK_S:
                        esferas[0].mover(0, 0, -deslocamento);
                        break;
                    case KeyEvent.VK_T:
                    luzPos.y += deslocamentoLuz;
                    //moverEsfera(esferas[2], 0, deslocamentoLuz, 0);
                    break;
                    case KeyEvent.VK_G:
                    luzPos.y -= deslocamentoLuz;
                    //moverEsfera(esferas[2], 0, -deslocamentoLuz, 0);
                    break;
                    case KeyEvent.VK_H:
                    luzPos.x += deslocamentoLuz;
                    //moverEsfera(esferas[2], deslocamentoLuz, 0, 0);
                    break;
                    case KeyEvent.VK_F:
                    luzPos.x -= deslocamentoLuz;
                    //moverEsfera(esferas[2], -deslocamentoLuz, 0, 0);
                    break;
                    case KeyEvent.VK_U:
                    luzPos.z -= deslocamentoLuz;
                    //moverEsfera(esferas[2], 0, 0, -deslocamentoLuz);
                    break;
                    case KeyEvent.VK_I:
                    luzPos.z += deslocamentoLuz;
                    //moverEsfera(esferas[2], 0, 0, deslocamentoLuz);
                    break;
                    case KeyEvent.VK_M:
                    n += 0.5; //aumentar o coeficiente especularidade
                    break;
                    case KeyEvent.VK_N:
                    n -= 0.5; //diminuir o coeficiente de especularidade
                    break;
                }
                pintarCanvas();
                renderPanel.repaint(); // Redesenha o painel
            }
        });

        pintarCanvas(); // Desenha a cena inicial
        add(renderPanel); // Adiciona o painel ao JFrame
        setVisible(true);
    }


    public ResultadoIntersecao interseccionaEsfera(Esfera[] esferas, double dx, double dy, double dz) {
        double menorT = Double.MAX_VALUE; // Inicializando o T como o maior possível
        int corPintar = bgColor.getRGB(); // Cor de fundo
        Esfera esferaMaisProxima = null;
        Intersection intersecao = null;

        for (Esfera esfera : esferas) {
            Ray raio = new Ray(new Vector3(0,0,0), new Vector3(dx, dy, dz));
            intersecao = esfera.intersect(raio);
            if ((intersecao != null) && (intersecao.distance < menorT)) {
                menorT = intersecao.distance;
                esferaMaisProxima = esfera;
            }   
        }
    
        // Se tiver uma esfera, teve intersecao
        if (esferaMaisProxima != null) {
            // Posição do ponto de interseção
            double px = menorT * dx, py = menorT * dy, pz = menorT * dz;
            Vector3 pontoIntersecao = new Vector3(px, py, pz);
            //criando o vetor da luz
            Vector3 vetorLuz = luzPos;
            vetorLuz = vetorLuz.subtract(pontoIntersecao);

            // -------  Calcular vetor em direção à luz (L) ----------
            double comprimentoL = Math.sqrt(vetorLuz.dot(vetorLuz));
            vetorLuz = vetorLuz.multiply(1/comprimentoL);

            // Verificar se o ponto está em sombra
            boolean sombra = false; // inicialmente consideramos que nao tem sombra
            Ray raio2 = new Ray(new Vector3(px, py, pz), new Vector3(vetorLuz.x, vetorLuz.y, vetorLuz.z));

            for (Esfera sombraEsfera : esferas) {
                if (sombraEsfera == esferaMaisProxima) continue; // Ignorar a própria esfera
            
                Intersection intersecao2 = sombraEsfera.intersect(raio2);
                if((intersecao2 != null) && (intersecao2.distance > 0) && intersecao2.distance < comprimentoL) {
                    sombra = true;
                    break;
                }            
            }

            //pegar a cor da esfera que teve intersecao mais proxima
            int esferaCor = esferaMaisProxima.getColor();

            // Adicionar a energia da luz ambiente
            int r = Math.min(255, (int) (this.energiaLuzAmbiente * (esferaCor >> 16 & 0xFF)));
            int g = Math.min(255, (int) (this.energiaLuzAmbiente * (esferaCor >> 8 & 0xFF)));
            int bCor = Math.min(255, (int) (this.energiaLuzAmbiente * (esferaCor & 0xFF)));
            
            
            if (!sombra) {//se nao tiver sombra, adicionar luz difusa e especular

                // Calcular vetor normal (N)
                double radius = esferaMaisProxima.getR();
                Vector3 N = pontoIntersecao.subtract(esferaMaisProxima.getCenter());
                N = N.multiply(1/radius);
                N = N.normalize();
        
                // Produto escalar N * L e intensidade difusa
                double produtoEscalarNL = Math.max(0, vetorLuz.dot(N));
                double energiaLuzDifusa = this.luzIntensidade * this.kd * produtoEscalarNL;

                // Cálculo da iluminação especular

                // Vetor de visão (V) na direção oposta ao raio
                Vector3 V = new Vector3(-dx, -dy, -dz).normalize();

                // Cálculo do vetor refletido (R)
                Vector3 R = N.multiply(2 * produtoEscalarNL).subtract(vetorLuz);

                // Produto escalar entre R e V (com proteção para não ser negativo)
                double produtoEscalarRV = Math.max(0, R.dot(V));

                // Cálculo da energia especular
                double energiaLuzEspecular = this.luzIntensidade * this.ks * Math.pow(produtoEscalarRV, this.n);

                // Adicionar a energia da luz difusa
                r = Math.min(255, (int) (r + (esferaCor >> 16 & 0xFF) * energiaLuzDifusa));
                g = Math.min(255, (int) (g + (esferaCor >> 8 & 0xFF) * energiaLuzDifusa));
                bCor = Math.min(255, (int) (bCor + (esferaCor & 0xFF) * energiaLuzDifusa));

                // Adicionar a energia especular à cor final
                r = Math.min(255, (int) (r + 255 * energiaLuzEspecular));
                g = Math.min(255, (int) (g + 255 * energiaLuzEspecular));
                bCor = Math.min(255, (int) (bCor + 255 * energiaLuzEspecular));
            }

            corPintar = new Color(r, g, bCor).getRGB();
}

        return new ResultadoIntersecao(esferaMaisProxima != null, corPintar);
    }
    public void pintarCanvas() {

        for (int l = 0; l < nLin; l++) {
            double y = h / 2.0 - Dy / 2.0 - l * Dy;      //pos no meio da altura do retangulo
            for (int c = 0; c < nCol; c++) {
                double x = -w / 2.0 + Dx / 2.0 + c * Dx; //pos no meio da largura do retangulo
                double dz = -d;                          //distancia da tela de mosquito pro olho do pintor   

                ResultadoIntersecao resultado = interseccionaEsfera(this.esferas, x, y, dz);
                canvas.setRGB(c, l, resultado.getColor());
            }
        }
    }
    public double getN(){
        return this.n;
    }
    
    private class RenderPanel extends JPanel {
        private final Font textFont;
        private final String[] textos;
        private final int xPosition = 10; // Posição X do texto
        private int yPosition; // Posição Y inicial do texto
        private int qtdTextos;
    
        public RenderPanel() {
            textFont = new Font("Arial", Font.BOLD, 20); // Inicializa a fonte
            textos = new String[]{
                "Controles:",
                "Bola branca: movimento com setinhas, e profundidade com W e S",
                "Luz: T, G, F, H  profundidade: U, I",
                "M aumentar especularidade, N diminuir especularidade"
            };

            yPosition = 30; // Inicializa a posição Y
            qtdTextos = textos.length + 1;
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(canvas, 0, 0, null); // Desenha o canvas atualizado

            g.setFont(textFont); // Fonte do texto
            // Desenhar o texto no topo
            for (int i = 0; i < textos.length; i++) {
                g.drawString(textos[i], xPosition, (i+1)*yPosition);
            }
            g.drawString("Coeficiente de especularidade: " + getN(), xPosition, (qtdTextos)*yPosition);
            
        }

    }

}
