package src.controller;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import src.model.interseccao.*;


public class Renderizador{

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

    public ProcessadorInterseccoes processador;
    
    public Vector3 origemRaio;
    public Renderizador(Cena cena, double w, double h, double d, int nCol, int nLin){
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
    }

    public void renderizar() {
        // Pegando o Raster pra manipular os pixels diretamente
        WritableRaster raster = canvas.getRaster();
        int[] pixelColor = new int[3]; // Vetor pra pegar as cores R G B
    

        for (int l = 0; l < this.nLin; l++) {
            double y = this.h / 2.0 - this.Dy / 2.0 - l * this.Dy; // Posiçao do meio da altura do retangulo
            for (int c = 0; c < this.nCol; c++) {
                double x = -this.w / 2.0 + this.Dx / 2.0 + c * this.Dx; // posiçao do meio da largura do retangulo
                double dz = -this.d; // distancia da tela projetada pro olho do observador

                Vector3 direcaoRaio = new Vector3(x, y, dz); // direcao do raio na direcao do centro do retangulo
                Ray raio = new Ray(this.origemRaio, direcaoRaio);

                // Convertendo a cor do resultado pra R G B
                int color = processador.interseccionarObjetos(raio);
                pixelColor[0] = (color >> 16) & 0xFF; // Red component
                pixelColor[1] = (color >> 8) & 0xFF;  // Green component
                pixelColor[2] = color & 0xFF;         // Blue component

                //Setta o pixel no raster do canvas
                raster.setPixel(c, l, pixelColor);
            }
        }
    }

    public BufferedImage getCanvas(){
        return this.canvas;
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
    public int getNlin(){
        return this.nLin;
    }
    public int getNcol(){
        return this.nCol;
    }
    public ArrayList<Intersectable> getObjetos(){
        return this.objetos;
    }
}