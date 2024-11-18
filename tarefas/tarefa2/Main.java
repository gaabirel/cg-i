import java.awt.Color;

public class Main {
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        
        int distanciaDoPintor = 5;
        double rEsfera = 0.5;
        Esfera[] esferas = new Esfera[1];
        //criando esferas
        esferas[0] = new Esfera(rEsfera, -(distanciaDoPintor+rEsfera) , 0, 0, new Color(255, 0, 0));
        Janela janela = new Janela(2.0, 2.0, distanciaDoPintor , 800, 800, esferas);

    }

}
