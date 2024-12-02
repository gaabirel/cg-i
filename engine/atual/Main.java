import src.controller.Cena;
import src.view.*;

public class Main {
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        System.out.println("Programa inicializando...");
        Cena cena = new Cena(); //inicializa os objetos da cena
        Janela janela = new Janela(2.0, 2.0, 5.0, 1600, 1600, cena);
    }

}
