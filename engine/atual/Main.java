import src.controller.Cena;
import src.view.*;

public class Main {
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        System.out.println("viadzzzo");
        Cena cena = new Cena(); //inicializa os objetos da cena
        Janela janela = new Janela(2.0, 2.0, 5.0, 800, 800, cena);
    }

}
