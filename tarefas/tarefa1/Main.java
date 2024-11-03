import java.awt.Color;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
        
        ArrayList<Esfera> esferas = new ArrayList<>();

        esferas.add(new Esfera(0.5, -10.0, 0, 0, new Color(255, 255, 255)));
        esferas.add(new Esfera(1.0, -15.0, 0, 0, new Color(255, 0, 0)));
        Janela janela = new Janela(2.0, 2.0, 5.0, 800, 800, esferas);

    }

}
