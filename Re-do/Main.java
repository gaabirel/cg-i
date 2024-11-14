import java.awt.Color;

public class Main {
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        
        Vector3 vetor1 = new Vector3(0, 0, -10);
        Vector3 vetor2 = new Vector3(0, 0, -15);
        Vector3 vetor3 = new Vector3(2, 0, -15);
        Esfera[] esferas = new Esfera[3];
        //criando esferas
        esferas[0] = new Esfera(0.5, vetor1, new Color(255, 255, 255));
        esferas[1] = new Esfera(1.0, vetor2, new Color(255, 0, 0));
        esferas[2] = new Esfera(1.0, vetor3, new Color(120, 125, 0));
        Janela janela = new Janela(2.0, 2.0, 5.0, 800, 800, esferas);
        Ray raio = new Ray(vetor2, vetor3);
        System.out.println(raio.toString());
    }

}
