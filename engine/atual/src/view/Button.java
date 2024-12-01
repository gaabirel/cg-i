package src.view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import src.model.interseccao.Light;
import src.model.interseccao.Vector3;

public class Button extends JButton{

    public Button(Janela janela, String texto, JLabel labelParaAtualizar, JPanel renderPanel, ArrayList<Light> luzes) {
        super(texto); // Define o texto do botão

        // Configurações do botão
        this.setFocusPainted(false);
        this.setContentAreaFilled(true);
        setHorizontalAlignment(SwingConstants.CENTER); 
        // Adiciona uma ação ao botão
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                
                labelParaAtualizar.setText("Voce adicionou uma luz: " + (luzes.size()+1)); // Atualiza o texto do JLabel
                int x = random.nextInt(-2, 2);
                int y = random.nextInt(0, 4);
                int z = random.nextInt(-15, 0);
                luzes.add(new Light(new Vector3(x, y, z), new Vector3(0.2, 0.2, 0.2)));

                janela.pintarCanvas();
                janela.repaint();
                renderPanel.requestFocusInWindow();
            }
        });
    }
}
