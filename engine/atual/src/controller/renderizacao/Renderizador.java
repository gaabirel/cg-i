package src.controller.renderizacao;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import src.model.Camera;
import src.model.cena.Cena;
import src.model.interseccao.*;
import src.model.objetos.Intersectable;


public class Renderizador{

     private double w;              // largura da janela
    private double h;               // altura da janela
    private double d;               // distância do olho do pintor até a janela

    private int nCol;               // número de colunas do canvas
    private int nLin;               // número de linhas do canvas

    private BufferedImage canvas;   // tela a ser pintada
    private double Dx;              // largura do retângulo
    private double Dy;              // altura do retângulo

    private ArrayList<Intersectable> objetos;
    private ArrayList<Light> luzes;

    public ProcessadorInterseccoes processador;
    
    public Camera camera;

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

        //Objetos e cor de fundo
        this.objetos = cena.getObjetos();
        this.luzes = cena.getLuzes();

        //Camera
        // Posição da câmera (Eye position)
        Vector3 posEye = new Vector3(0, 0, 0);
        
        // Ponto que a câmera está olhando (LookAt)
        Vector3 lookAt = new Vector3(0.5, 0, -5);
        
        // Vetor "para cima" da câmera (ViewUp)
        Vector3 viewUp = new Vector3(0, 1, 0);
        
        // Criando a instância da câmera
        this.camera = new Camera(posEye, lookAt, viewUp);

        //classe que vai processar as interseccoes dos objetos
        this.processador = new ProcessadorInterseccoes(objetos, luzes, camera);
        
        //origem do sistema de coordenadas
        this.origemRaio = new Vector3(0, 0, 0); // origem do raio saindo do olho do observador
    }

    public void renderizar() {
        // Pegando o Raster pra manipular os pixels diretamente
        WritableRaster raster = canvas.getRaster();
        int[] pixelColor = new int[3]; // Vetor pra pegar as cores R G B
    
        //pre calculo de variaveis
        double half_H = h / 2;
        double half_W = w / 2;
        double half_Dy = Dy / 2;
        double half_Dx = Dx / 2;
        double sub_HalfH_HalfDy = half_H - half_Dy;
        double sub_HalfW_HalfDx = half_Dx - half_W;
        double dz = -this.d; // distancia da tela projetada pro olho do observador

        
        for (int l = 0; l < this.nLin; l++) {
            double y = sub_HalfH_HalfDy - l * this.Dy; 
            for (int c = 0; c < this.nCol; c++) {
                double x = sub_HalfW_HalfDx + c * this.Dx; 

                Vector3 direcaoRaio = new Vector3(x, y, dz);

                // Convertendo a cor do resultado pra R G B
                int color = processador.interseccionarObjetos(new Ray(this.origemRaio, direcaoRaio));
                pixelColor[0] = (color >> 16) & 0xFF; // Red 
                pixelColor[1] = (color >> 8) & 0xFF;  // Green
                pixelColor[2] = color & 0xFF;         // Blue 

                //Setta o pixel no raster do canvas
                raster.setPixel(c, l, pixelColor);
            }
        }
    }

    public void transladarCamera(double dx, double dy, double dz) {
        Vector3 translacao = new Vector3(dx, dy, dz);
        Vector3 novaPos = this.camera.getPosEye().add(translacao);
        Vector3 novoLookAt = this.camera.getLookAt().add(translacao);
        this.camera.setPosEye(novaPos);
        this.camera.setLookAt(novoLookAt);
        }

        public void mudarLookAt(double anguloX, double anguloY, double anguloZ) {
        // Calcula o novo ponto LookAt usando os ângulos fornecidos
        double radX = Math.toRadians(anguloX);
        double radY = Math.toRadians(anguloY);
        double radZ = Math.toRadians(anguloZ);

        double cosY = Math.cos(radY);
        double sinY = Math.sin(radY);
        double cosP = Math.cos(radX);
        double sinP = Math.sin(radX);
        double cosR = Math.cos(radZ);
        double sinR = Math.sin(radZ);

        Vector3 lookAtOriginal = this.camera.getLookAt();

        Vector3 novoLookAt = new Vector3(
            lookAtOriginal.getX() * cosY * cosR - lookAtOriginal.getY() * sinR + lookAtOriginal.getZ() * sinY * cosR,
            lookAtOriginal.getX() * sinP * sinY * cosR + lookAtOriginal.getY() * cosP * cosR + lookAtOriginal.getZ() * sinP * cosY * cosR,
            lookAtOriginal.getX() * cosP * sinY * cosR - lookAtOriginal.getY() * sinP * sinR + lookAtOriginal.getZ() * cosP * cosY * cosR
        ).add(this.camera.getPosEye());

        this.camera.setLookAt(novoLookAt);
    }

    public ProcessadorInterseccoes getProcessador() {
        return this.processador;
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