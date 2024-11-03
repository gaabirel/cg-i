import java.awt.Color;

public class Main {
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        
        Esfera[] esferas = new Esfera[3];
        //criando esferas
        esferas[0] = new Esfera(0.5, -10.0, 0, 0, new Color(255, 255, 255));
        esferas[1] = new Esfera(1.0, -15.0, 0, 0, new Color(255, 0, 0));
        esferas[2] = new Esfera(0.05, -10, 0, 1.5, new Color(0, 0, 255)); // mesma pos da luz
        Janela janela = new Janela(2.0, 2.0, 5.0, 800, 800, esferas);

    }

}
