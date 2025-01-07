package src.view;

import javax.swing.*;

import src.model.objetos.Intersectable;

import java.awt.*;
import java.util.ArrayList;
import src.controller.MainController;


public class MenuBarraSuperior extends JMenuBar {
    private ArrayList<Intersectable> objects; // Lista de objetos
    private JComboBox<String> objectSelector;
    private MainController mainController;

    public MenuBarraSuperior(MainController mainController) {
        this.mainController = mainController;
        this.objects = mainController.getRenderizador().getObjetos();
        initializeMenus();
    }

    private void initializeMenus() {

        //Opcao de Menu
        JMenu menu = new JMenu("Menu");
        JMenuItem item = new JMenuItem("Sair");
        item.addActionListener(e -> System.exit(0));
        menu.add(item);
        add(menu);

        //Opcao para os objetos
        JMenu barraObjetos = new JMenu("Objetos");
        JMenuItem selecionar = new JMenuItem("Selecionar");
        selecionar.addActionListener(e -> showObjectDialog());
        barraObjetos.add(selecionar);
        add(barraObjetos);

    }

    /*
     * Atualiza o seletor de objetos
     */
    private void updateObjectSelector() {
        objectSelector.removeAllItems();
        if (objects != null) {
            for (int i = 0; i < objects.size(); i++) {
                objectSelector.addItem(i + ": " + objects.get(i).getClass().getSimpleName());
            }
        }
    }

    /*
     * Mostra uma janelinha para selecionar o objeto a ser transformado
     */
    private void showObjectDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Selecionar Objeto");
        dialog.setLayout(new FlowLayout());
        objectSelector = new JComboBox<>();
        objectSelector.addActionListener(e -> {
            //checar a selecao de algum objeto e setar o novo objeto para ser transformado no mainController
            mainController.getTecladoListener().setIdxObjetoTransformado(this.objectSelector.getSelectedIndex());
            dialog.dispose();
        });
        updateObjectSelector();
        dialog.add(objectSelector);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}