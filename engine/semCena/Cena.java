import java.awt.Color;
import java.util.ArrayList;

public class Cena {
    private ArrayList<Intersectable> objetosCena;
    private ArrayList<Light> luzes;

    public Cena() {
        this.objetosCena = new ArrayList<>();
        this.luzes = new ArrayList<>(); // Exemplo de 2 luzes
        Vector3 intensidade = new Vector3(0.3, 0.3, 0.3);
        new Light(new Vector3(-2, 1, -8) , intensidade, new Color(255, 255, 255));
        Light luz1 = new Light(new Vector3(1, 2, -1) , intensidade, new Color(255, 255, 255));
        Light luz2 = new Light(new Vector3(0, 3, -6) , intensidade, new Color(255, 255, 255));
        Light luz3 = new Light(new Vector3(0, 2, -4), new Vector3(0.3, 0.3, 0.3), new Color(255, 255, 255));
        luzes.add(luz1);
        luzes.add(luz2);
        luzes.add(luz3);
        // Adicionar objetos à cena
        double xmin = -2.0;
        double xmax = 2.0;
        double ymin = -2.0;
        double ymax = 2.0;
        Vector3 vetor1 = new Vector3(0, 0, -10);
        Vector3 vetor2 = new Vector3(0, 0, -15);
        Vector3 vetor3 = new Vector3(2, 0, -15);
        Vector3 pontoPlano = new Vector3(0.0, -1, -16); // Ponto no plano
        Vector3 normalPlano = new Vector3(0.0, 2, 1); // Normal para cima
        Plano plano = new Plano(pontoPlano, normalPlano, new Color(255, 255, 255), xmin, xmax, ymin, ymax);
        Intersectable[] objetos = new Intersectable[4];
        objetos[0] = new Esfera(0.5, vetor1, new Color(255, 255, 255));
        objetos[1] = new Esfera(1.0, vetor2, new Color(255, 0, 0));
        objetos[2] = new Esfera(1.0, vetor3, new Color(255, 105, 180));
        objetos[3] = plano;
        for(int i = 0; i < 4; i++) {
            objetosCena.add(objetos[i]);
        }
    }

    public ArrayList<Intersectable> getObjetos() {
        return objetosCena;
    }

    public ArrayList<Light> getLuzes() {
        return luzes;
    }
}