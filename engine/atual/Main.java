import src.view.*;

public class Main {
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        System.out.println("viadzzzo");
        Cena cena = new Cena();
        Janela janela = new Janela(2.0, 2.0, 5.0, 800, 800, cena.getObjetos(), cena.getLuzes());
    }

}
