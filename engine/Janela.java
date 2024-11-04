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
    private double[] luzPos;             //posiçao da fonte de luz
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
        //posiçao da luz igual a da terceira esfera
        this.luzPos = new double[3];
        luzPos[0] = this.esferas[2].getX();
        luzPos[1] = this.esferas[2].getY();
        luzPos[2] = this.esferas[2].getZ();
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
                        moverEsfera(esferas[0], 0, deslocamento, 0);
                        break;
                    case KeyEvent.VK_DOWN:
                        moverEsfera(esferas[0], 0, -deslocamento, 0);
                        break;
                    case KeyEvent.VK_LEFT:
                        moverEsfera(esferas[0], -deslocamento, 0, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        moverEsfera(esferas[0], deslocamento, 0, 0);
                        break;
                    case KeyEvent.VK_W:
                        moverEsfera(esferas[0], 0, 0, deslocamento);
                        break;
                    case KeyEvent.VK_S:
                        moverEsfera(esferas[0], 0, 0, -deslocamento);
                        break;
                    case KeyEvent.VK_T:
                    luzPos[1] += deslocamentoLuz;
                    moverEsfera(esferas[2], 0, deslocamentoLuz, 0);
                    break;
                    case KeyEvent.VK_G:
                    luzPos[1] -= deslocamentoLuz;
                    moverEsfera(esferas[2], 0, -deslocamentoLuz, 0);
                    break;
                    case KeyEvent.VK_H:
                    luzPos[0] += deslocamentoLuz;
                    moverEsfera(esferas[2], deslocamentoLuz, 0, 0);
                    break;
                    case KeyEvent.VK_F:
                    luzPos[0] -= deslocamentoLuz;
                    moverEsfera(esferas[2], -deslocamentoLuz, 0, 0);
                    break;
                    case KeyEvent.VK_U:
                    luzPos[2] -= deslocamentoLuz;
                    moverEsfera(esferas[2], 0, 0, -deslocamentoLuz);
                    break;
                    case KeyEvent.VK_I:
                    luzPos[2] += deslocamentoLuz;
                    moverEsfera(esferas[2], 0, 0, deslocamentoLuz);
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

    public void moverEsfera(Esfera esfera, double dx, double dy, double dz) {
        esfera.setX(esfera.getX() + dx);
        esfera.setY(esfera.getY() + dy);
        esfera.setZ(esfera.getZ() + dz);
    }

    public ResultadoIntersecao interseccionaEsfera(Esfera[] esferas, double dx, double dy, double dz) {
        double menorT = Double.MAX_VALUE; // Inicializando o T como o maior possível
        int corPintar = bgColor.getRGB(); // Cor de fundo
        Esfera esferaMaisProxima = null;
    
        for (Esfera esfera : esferas) {
            double a = dx * dx + dy * dy + dz * dz;                                             //dr * dr
            double b = 2 * (dx * -esfera.getX() + dy * -esfera.getY() + dz * -esfera.getZ());   // 2*(w * dr)
            double cEsfera = (-esfera.getX() * -esfera.getX()) +                                // cEsfera = (w*w) - R²
                             (-esfera.getY() * -esfera.getY()) +
                             (-esfera.getZ() * -esfera.getZ()) - esfera.getR() * esfera.getR();
    
            double discriminante = b * b - 4 * a * cEsfera;
    
            if (discriminante >= 0) {
                double raizDiscriminante = Math.sqrt(discriminante);
                a *= 2;
                double t1 = (-b + raizDiscriminante) / a;
                double t2 = (-b - raizDiscriminante) / a;
    
                double t = (t1 > 0 && t2 > 0) ? Math.min(t1, t2) : (t1 > 0 ? t1 : t2);
                
                if (t > 0 && t < menorT) {
                    menorT = t;
                    esferaMaisProxima = esfera;
                }
            }
        }
    
        if (esferaMaisProxima != null) {
            // Posição do ponto de interseção
            double px = menorT * dx, py = menorT * dy, pz = menorT * dz;
    
            // Calcular vetor normal (N)
            double[] N = {(px - esferaMaisProxima.getX()) / esferaMaisProxima.getR(),
                          (py - esferaMaisProxima.getY()) / esferaMaisProxima.getR(),
                          (pz - esferaMaisProxima.getZ()) / esferaMaisProxima.getR()};
            double comprimentoN = Math.sqrt(N[0] * N[0] + N[1] * N[1] + N[2] * N[2]);
            for (int i = 0; i < 3; i++) N[i] /= comprimentoN;
    
            // Calcular vetor em direção à luz (L)
            double[] L = {luzPos[0] - px, luzPos[1] - py, luzPos[2] - pz};
            double comprimentoL = Math.sqrt(L[0] * L[0] + L[1] * L[1] + L[2] * L[2]);
            for (int i = 0; i < 3; i++) L[i] /= comprimentoL;
    
            // Produto escalar N * L e intensidade difusa
            double produtoEscalarNL = Math.max(0, L[0] * N[0] + L[1] * N[1] + L[2] * N[2]);
            double energiaLuzDifusa = this.luzIntensidade * this.kd * produtoEscalarNL;
                
            // Cálculo da iluminação especular
            double[] V = {-dx, -dy, -dz}; // Vetor de visão (direção oposta ao raio)
            double[] R = {2 * produtoEscalarNL * N[0] - L[0],
                        2 * produtoEscalarNL * N[1] - L[1],
                        2 * produtoEscalarNL * N[2] - L[2]};
            double comprimentoV = Math.sqrt(V[0] * V[0] + V[1] * V[1] + V[2] * V[2]);
            for (int i = 0; i < 3; i++) V[i] /= comprimentoV;

            double produtoEscalarRV = Math.max(0, R[0] * V[0] + R[1] * V[1] + R[2] * V[2]);
            double energiaLuzEspecular = this.luzIntensidade * this.ks * Math.pow(produtoEscalarRV, this.n);

            // Adicionar a energia da luz difusa
            int esferaCor = esferaMaisProxima.getCor();
            int r = Math.min(255, (int) ((esferaCor >> 16 & 0xFF) * energiaLuzDifusa));
            int g = Math.min(255, (int) ((esferaCor >> 8 & 0xFF) * energiaLuzDifusa));
            int bCor = Math.min(255, (int) ((esferaCor & 0xFF) * energiaLuzDifusa));
    
            // Adicionar a energia da luz ambiente
            r = Math.min(255, (int) (r + (this.energiaLuzAmbiente * (esferaCor >> 16 & 0xFF))));
            g = Math.min(255, (int) (g + (this.energiaLuzAmbiente * (esferaCor >> 8 & 0xFF))));
            bCor = Math.min(255, (int) (bCor + (this.energiaLuzAmbiente * (esferaCor & 0xFF))));

            // Adicionar a energia especular à cor final
            r = Math.min(255, (int) (r + (energiaLuzEspecular * (esferaCor >> 16 & 0xFF))));
            g = Math.min(255, (int) (g + (energiaLuzEspecular * (esferaCor >> 8 & 0xFF))));
            bCor = Math.min(255, (int) (bCor + (energiaLuzEspecular * (esferaCor & 0xFF))));

            corPintar = (r << 16) | (g << 8) | bCor;


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
                canvas.setRGB(c, l, resultado.getCor());
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