package src.model.cena;

import java.util.ArrayList;
import src.model.interseccao.*;
import src.model.objetos.Intersectable;

public class Cena {
    private ArrayList<Intersectable> objetosCena;
    private ArrayList<Light> luzes;
    private CenaBuilder cenaBuilder;

    public Cena() {

        this.cenaBuilder = new CenaBuilder();
        this.objetosCena = new ArrayList<>();
        luzes = cenaBuilder.criarLuzesPadrao();
        
        objetosCena.addAll(cenaBuilder.criarTriangulosPadrao());
        //objetosCena.addAll(cenaBuilder.criarPlanosPadrao());
        //objetosCena.addAll(cenaBuilder.criarEsferasPadrao());
        objetosCena.addAll(cenaBuilder.criarConesPadrao()); 
        objetosCena.addAll(cenaBuilder.criarCilindrosPadrao());
        //objetosCena.addAll(cenaBuilder.criarEsferasAleatorias(1));
    }

    public ArrayList<Light> getLuzes(){
        return this.luzes;
    }
    
    public ArrayList<Intersectable> getObjetos(){
        return this.objetosCena;
    }
   
}