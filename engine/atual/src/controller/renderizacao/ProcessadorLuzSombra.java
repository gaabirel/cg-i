package src.controller.renderizacao;

import java.util.ArrayList;

import src.model.interseccao.Intersectable;
import src.model.interseccao.Intersection;
import src.model.interseccao.Light;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;

public class ProcessadorLuzSombra {
    ArrayList<Light> luzes;
    ArrayList<Intersectable> objetos;
    private Vector3 energiaLuzAmbiente;   //Fator de luz ambiente
    private Vector3 intensidadeAmbiente;
    final double EPSILON = 1e-5; // Valor pequeno para evitar auto-interseção

    public ProcessadorLuzSombra(ArrayList<Light> luzes, ArrayList<Intersectable> objetos){
        this.luzes = luzes;
        this.objetos = objetos;
        this.intensidadeAmbiente = new Vector3(0.2, 0.2, 0.2);
    }

    public int[] processar(Intersectable objeto, Vector3 pontoIntersecao, Ray raio) {
    
        int[] corPintar = {0, 0, 0};
    
        // Adicionar luz ambiente
        energiaLuzAmbiente = intensidadeAmbiente.arroba(objeto.getKambiente());
        adicionarEnergia(energiaLuzAmbiente, corPintar);
    
        Vector3 vetorVisao = raio.direction.negate(); // Vetor de visão (V)
        Vector3 vetorNormal = objeto.calcularNormal(pontoIntersecao); // Vetor normal (N)
        Vector3 pontoDeslocado = pontoIntersecao.add(vetorNormal.multiply(EPSILON)); // Evitar auto-interseção
    
        for (Light luz : luzes) {
            processarLuz(luz, objeto, pontoIntersecao, pontoDeslocado, vetorNormal, vetorVisao, corPintar);
        }
    
        return corPintar;
    }
    
    private void processarLuz(Light luz, Intersectable objeto, Vector3 pontoIntersecao, Vector3 pontoDeslocado,
                              Vector3 vetorNormal, Vector3 vetorVisao, int[] corPintar) {
        Vector3 direcaoLuz = luz.calcularDirecaoLuz(pontoIntersecao);
        double comprimentoLuz = direcaoLuz.length();
    
        if (estaNaSombra(objeto, pontoDeslocado, direcaoLuz, comprimentoLuz)) {
            return;
        }
    
        adicionarContribuicoesDeLuz(luz, objeto, vetorNormal, vetorVisao, direcaoLuz, comprimentoLuz, corPintar);
    }
    
    private boolean estaNaSombra(Intersectable objeto, Vector3 pontoDeslocado, Vector3 direcaoLuz, double comprimentoLuz) {
        Ray raioDaSombra = new Ray(pontoDeslocado, direcaoLuz);
    
        for (Intersectable objetoSombra : objetos) {
            if (objetoSombra == objeto) {
                continue; // Ignorar o próprio objeto
            }
    
            Intersection interseccaoSombra = objetoSombra.intersect(raioDaSombra);
    
            if (interseccaoSombra != null 
                && interseccaoSombra.distance > EPSILON
                && interseccaoSombra.distance < comprimentoLuz) {
                return true; // Objeto está na sombra
            }
        }
        return false;
    }
    
    private void adicionarContribuicoesDeLuz(Light luz, Intersectable objeto, Vector3 vetorNormal, Vector3 vetorVisao,
                                             Vector3 direcaoLuz, double comprimentoLuz, int[] corPintar) {
        double produtoEscalarNL = Math.max(0, direcaoLuz.dot(vetorNormal));
        Vector3 vetorRefletido = vetorNormal.multiply(2 * produtoEscalarNL).subtract(direcaoLuz);
        double produtoEscalarRV = Math.max(0, vetorRefletido.dot(vetorVisao));
        
        Vector3 energiaDifusa = luz.getIntensidade()
                                   .arroba(objeto.getKdifuso())
                                   .multiply(produtoEscalarNL);
    
        Vector3 energiaEspecular = luz.getIntensidade()
                                      .arroba(objeto.getKespecular())
                                      .multiply(Math.pow(produtoEscalarRV, objeto.getBrilho()));
    
        double fatorAtenuacao = 1 / (1 + 0.1 * comprimentoLuz + 0.001 * comprimentoLuz * comprimentoLuz);
        Vector3 energiaFinal = (energiaDifusa.add(energiaEspecular)).multiply(fatorAtenuacao);
    
        adicionarEnergia(energiaFinal, corPintar);
    }

    public void adicionarEnergia(Vector3 energia, int[] cor) {
        cor[0] = Math.min(255, (int) (cor[0] + 255 * energia.getX()));  //Red
        cor[1] = Math.min(255, (int) (cor[1] + 255 * energia.getY()));  //Green
        cor[2] = Math.min(255, (int) (cor[2] + 255 * energia.getZ()));  //Blue
    }
}
