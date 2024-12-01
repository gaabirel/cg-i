
package src.model.materiais;

import src.model.interseccao.Vector3;

import java.util.Random;

public class MateriaisPadrao {

    Random random = new Random();
    
    public Material getMaterialAleatorio() {
        Material[] materiais = getTodosMateriais();
        int indexAleatorio = random.nextInt(materiais.length); // Gera um índice aleatório
        return materiais[indexAleatorio];
    }

    // Função para retornar uma string com os nomes de todos os materiais
    public String[] getNomesMateriais() {
        Material[] materiais = getTodosMateriais();
        String[] nomes = new String[materiais.length];
        
        for (int i = 0; i < materiais.length; i++) {
            nomes[i] = materiais[i].getNome(); // Supondo que Material possui um método getNome()
        }
        
        return nomes;
    }

    // Função para retornar um array com todos os materiais
    public Material[] getTodosMateriais() {
        return new Material[] {
            METALICO,
            MADEIRA,
            VIDRO,
            PLASTICO,
            OURO,
            COBRE,
            PLASTICO_BRILHANTE,
            MADEIRA_ENVELHECIDA,
            BORRACHA_PRETA,
            PLASTICO_PRETO,
            TURQUESA,
            RUBI,
            PEROLA,
            OBSIDIANA
        };
    }

    // Definições dos materiais (exemplo)
    public final Material METALICO = new Material("Metálico", new Vector3(0.8, 0.8, 0.8), new Vector3(0.9, 0.9, 0.9), new Vector3(0.1, 0.1, 0.1));
    public final Material MADEIRA = new Material("Madeira", new Vector3(0.6, 0.3, 0.1), new Vector3(0.5, 0.5, 0.5), new Vector3(0.2, 0.1, 0.05));
    public final Material VIDRO = new Material("Vidro", new Vector3(0.9, 0.9, 0.9), new Vector3(1.0, 1.0, 1.0), new Vector3(0.1, 0.1, 0.1));
    public final Material PLASTICO = new Material("Plástico", new Vector3(0.8, 0.8, 0.8), new Vector3(0.9, 0.9, 0.9), new Vector3(0.2, 0.2, 0.2));
    public final Material OURO = new Material("Ouro", new Vector3(1.0, 0.84, 0.0), new Vector3(1.0, 1.0, 1.0), new Vector3(0.2, 0.2, 0.1));
    public final Material COBRE = new Material("Cobre", new Vector3(0.72, 0.45, 0.20), new Vector3(1.0, 0.9, 0.6), new Vector3(0.2, 0.1, 0.05));
    public final Material PLASTICO_BRILHANTE = new Material("Plástico Brilhante", new Vector3(0.6, 0.6, 0.6), new Vector3(1.0, 1.0, 1.0), new Vector3(0.3, 0.3, 0.3));
    public final Material MADEIRA_ENVELHECIDA = new Material("Madeira Envelhecida", new Vector3(0.4, 0.2, 0.1), new Vector3(0.3, 0.3, 0.3), new Vector3(0.1, 0.05, 0.02));
    public final Material BORRACHA_PRETA = new Material("Borracha Preta", new Vector3(0.02, 0.02, 0.02), new Vector3(0.01, 0.01, 0.01), new Vector3(0.50, 0.50, 0.50));
    public final Material PLASTICO_PRETO = new Material("Plástico Preto", new Vector3(0.0, 0.0, 0.0), new Vector3(0.01, 0.01, 0.01), new Vector3(0.50, 0.50, 0.50));
    public final Material TURQUESA = new Material("Turquesa", new Vector3(0.1, 0.18725, 0.1745), new Vector3(0.396, 0.74151, 0.69102), new Vector3(0.297254, 0.30829, 0.306678));
    public final Material RUBI = new Material("Rubi", new Vector3(0.1745, 0.01175, 0.01175), new Vector3(0.61424, 0.04136, 0.04136), new Vector3(0.727811, 0.626959, 0.626959));
    public final Material PEROLA = new Material("Pérola", new Vector3(0.25, 0.20725, 0.20725), new Vector3(1.0, 0.829, 0.829), new Vector3(0.296648, 0.296648, 0.296648));
    public final Material OBSIDIANA = new Material("Obsidiana", new Vector3(0.05375, 0.05, 0.06625), new Vector3(0.18275, 0.17, 0.22525), new Vector3(0.332741, 0.328634, 0.346435));
    
}