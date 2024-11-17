package src.view;
import javax.swing.*;
import java.awt.*;

public class TextoNaTela extends JLabel {

    int size;

    public TextoNaTela(String texto, int size) {
        super(texto);
        this.size = size;
        estilizar();
    }

    private void estilizar() {
        setFont(new Font("Arial", Font.BOLD, this.size)); // Define a fonte como Arial, negrito, tamanho 24
        setForeground(new Color(155, 155, 50));      // Define a cor do texto
        setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o texto horizontalmente
        setPreferredSize(new Dimension(300, 50));  // Define o tamanho preferido
    }
}