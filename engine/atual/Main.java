import src.config.Config;
import src.controller.MainController;
import src.controller.renderizacao.Renderizador;
import src.model.cena.Cena;
import src.view.Janela;

public class Main {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        System.out.println("Programa inicializando...");

        Cena cena = new Cena(); 
        Renderizador renderizador = new Renderizador(
            cena, 
            Config.LARGURA_WINDOW, 
            Config.ALTURA_WINDOW, 
            Config.DISTANCIA_CAMERA, 
            Config.LARGURA_JANELA, 
            Config.ALTURA_JANELA
        );

        Janela janela = new Janela(Config.LARGURA_JANELA, Config.ALTURA_JANELA);
        MainController mainController = new MainController(janela, renderizador);
    }
}