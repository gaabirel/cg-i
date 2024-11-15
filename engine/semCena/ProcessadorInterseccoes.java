import java.awt.Color;

import src.Intersectable;
import src.Intersection;
import src.Light;
import src.Ray;
import src.ResultadoIntersecao;
import src.Vector3;

public class ProcessadorInterseccoes {

    //parametros da luz
    private Vector3 energiaLuzAmbiente;   // Fator de luz ambiente
    private double n;                    //expoente de brilho, ou coeficiente de especularidade
    private Color bgColor; 

    ProcessadorInterseccoes(){
        this.bgColor = new Color(100, 100, 100); // cor de fundo (cinza)
        this.energiaLuzAmbiente = new Vector3(0.3, 0.3, 0.3); 
        this.n = 5;
    }
    
    public ResultadoIntersecao interseccionaObjetos(Intersectable[] objetos, Ray raio, Light[] luzes) {
        double menorDistanciaT = Double.MAX_VALUE; // Inicializando o T (distancia) como o maior possível
        int corPintar = bgColor.getRGB(); // Cor de fundo
        Intersectable objetoMaisProximo = null;
        Intersection intersecao = null; //ponto de intersecao

        //calcular a intersecao de cada objeto na cena
        for (Intersectable objeto : objetos) {
            intersecao = objeto.intersect(raio);
            if ((intersecao != null) && (intersecao.distance < menorDistanciaT)) {
                menorDistanciaT = intersecao.distance;
                objetoMaisProximo = objeto;
            }   
        }
        // Se o objeto nao for nulo, teve intersecao
        if (objetoMaisProximo != null) {
            // Posição do ponto de interseção
            double  px = menorDistanciaT * raio.direction.x, 
                    py = menorDistanciaT * raio.direction.y, 
                    pz = menorDistanciaT * raio.direction.z;
            Vector3 pontoIntersecao = new Vector3(px, py, pz);

            //pegar a cor do objeto que teve intersecao mais proxima
            int objetoCor = objetoMaisProximo.getColor();

            int r = 0, g = 0, bCor = 0;
            // Adicionar a energia da luz ambiente
            r += Math.min(255, (int) (this.energiaLuzAmbiente.x * (objetoCor >> 16 & 0xFF)));
            g += Math.min(255, (int) (this.energiaLuzAmbiente.y * (objetoCor >> 8 & 0xFF)));
            bCor += Math.min(255, (int) (this.energiaLuzAmbiente.z * (objetoCor & 0xFF)));
            

            for(Light luz : luzes){
                //criando o vetor da luz
                Vector3 vetorLuz = luz.calcularDirecaoLuz(pontoIntersecao);

                // -------  Calcular vetor em direção à luz (L) ----------
                double comprimentoL = Math.sqrt(vetorLuz.dot(vetorLuz));
                vetorLuz = vetorLuz.multiply(1/comprimentoL);

                //Verificar se o ponto está em sombra
                boolean sombra = false; //inicialmente consideramos que nao tem sombra
                Ray raio2 = new Ray(pontoIntersecao, vetorLuz);

                for (Intersectable sombraObjeto : objetos) {
                    if (sombraObjeto == objetoMaisProximo) continue; //Ignorar o proprio objeto
                
                    Intersection intersecao2 = sombraObjeto.intersect(raio2);
                    if((intersecao2 != null) && (intersecao2.distance > 0) && intersecao2.distance < comprimentoL) {
                        sombra = true;
                        break;
                    }            
                }
            
                if (!sombra) {//se nao tiver sombra, adicionar luz difusa e especular

                    // Calcular vetor normal (N)
                    Vector3 N = objetoMaisProximo.calcularNormal(pontoIntersecao);        
                    
                    // Produto escalar N * L e intensidade difusa
                    double produtoEscalarNL = Math.max(0, vetorLuz.dot(N));
                    Vector3 energiaLuzDifusa = luz.getIntensidade().arroba(objetoMaisProximo.getKdifuso()).multiply(produtoEscalarNL);

                    // Cálculo da iluminação especular

                    // Vetor de visão (V) na direção oposta ao raio
                    Vector3 V = new Vector3(-raio.direction.x, -raio.direction.y, -raio.direction.z).normalize();

                    // Cálculo do vetor refletido (R)
                    Vector3 R = N.multiply(2 * produtoEscalarNL).subtract(vetorLuz);

                    // Produto escalar entre R e V (com proteção para não ser negativo)
                    double produtoEscalarRV = Math.max(0, R.dot(V));

                    // Cálculo da energia especular
                    Vector3 energiaLuzEspecular = luz.getIntensidade().arroba(objetoMaisProximo.getKespecular()).multiply(Math.pow(produtoEscalarRV, this.n));

                    // Adicionar a energia da luz difusa
                    r = Math.min(255, (int) (r + (objetoCor >> 16 & 0xFF) * energiaLuzDifusa.x));
                    g = Math.min(255, (int) (g + (objetoCor >> 8 & 0xFF) * energiaLuzDifusa.y));
                    bCor = Math.min(255, (int) (bCor + (objetoCor & 0xFF) * energiaLuzDifusa.z));

                    // Adicionar a energia especular à cor final
                    r = Math.min(255, (int) (r + 255 * energiaLuzEspecular.x));
                    g = Math.min(255, (int) (g + 255 * energiaLuzEspecular.y));
                    bCor = Math.min(255, (int) (bCor + 255 * energiaLuzEspecular.z));
                }
            }
            corPintar = new Color(r, g, bCor).getRGB();
            return new ResultadoIntersecao(intersecao, corPintar); //teve intersecao
        }
        return new ResultadoIntersecao(intersecao, corPintar);     //nao teve intersecao, vai retornar false e bgcolor
    }
}
