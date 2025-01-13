package src.view;

import java.awt.*;
import javax.swing.*;

import src.controller.MainController;
import src.model.materiais.MateriaisPadrao;
import src.model.materiais.Material;
import src.model.objetos.Intersectable;

public class OpcaoJanela extends JDialog {

    private final MateriaisPadrao materiaisPadrao;
    private MainController mainController;
    Janela parent;

    public OpcaoJanela(Janela parent, Intersectable objeto, MainController mainController) {
        super(parent, "Opções do Objeto", true);
        this.parent = parent;
        this.materiaisPadrao = MateriaisPadrao.getInstance();
        this.mainController = mainController;
        configurarJanela(objeto);
        setLocationRelativeTo(parent); 
        setVisible(true);
    }

    /**
     * Configura a janela principal.
     */
    private void configurarJanela( Intersectable objeto) {
        setLayout(new BorderLayout()); 
        setResizable(false);
        add(criarLabelInformativo(objeto), BorderLayout.NORTH);
        add(criarPainelCentral(objeto), BorderLayout.CENTER);
        add(criarBotaoCancelar(), BorderLayout.SOUTH);
        pack(); 
    }

    /**
     * Cria o rótulo informativo no topo da janela.
     */
    private JLabel criarLabelInformativo(Intersectable objeto) {
        JLabel label = new JLabel("Você clicou no objeto: " + objeto.getClass());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Cria o painel central com as opções de materiais e alteração de cor.
     */
    private JPanel criarPainelCentral( Intersectable objeto) {
        JPanel painelCentral = new JPanel(new BorderLayout());

        //Botão para alterar material
        JButton alterarMaterial = new JButton("Alterar Material");
        alterarMaterial.addActionListener(e -> alterarMaterialObjeto( objeto));

        //Botão para alterar cor
        JButton alterarCor = new JButton("Alterar Cor");
        alterarCor.addActionListener(e -> alterarCorObjeto( objeto));

        //Adiciona os botões de alteração de material e cor no topo
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelSuperior.add(alterarMaterial);
        painelSuperior.add(alterarCor);
        painelSuperior.add(criarSelecionarObjeto(objeto));
        painelCentral.add(painelSuperior, BorderLayout.NORTH);

        return painelCentral;
    }

    /**
     * Abre uma janela com as opções de materiais.
     */
    private void alterarMaterialObjeto( Intersectable objeto) {
        //Criando a janela de materiais
        JDialog materialDialog = new JDialog(this, "Escolher Material", true);
        materialDialog.setSize(400, 300);
        materialDialog.setLocationRelativeTo(this); //Centraliza a janela de materiais
    
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 colunas, espaçamento de 10px
        String[] nomesMateriais = materiaisPadrao.getNomesMateriais();
        Material[] materiais = materiaisPadrao.getTodosMateriais();
    
        //Adiciona os botões de materiais à grade
        for (int i = 0; i < nomesMateriais.length; i++) {
            gridPanel.add(criarBotaoMaterial( objeto, materiais[i], nomesMateriais[i]));
        }
    
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        materialDialog.add(scrollPane, BorderLayout.CENTER);
    
        //Criando o painel para o botão "Cancelar"
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> materialDialog.dispose());  // Fecha a janela ao clicar
        bottomPanel.add(cancelarButton);
    
        materialDialog.add(bottomPanel, BorderLayout.SOUTH);
    
        // xibe a janela de materiais
        materialDialog.setVisible(true);
    }

    /**
     *Cria um botão para um material específico.
     */
    private JButton criarBotaoMaterial( Intersectable objeto, Material material, String nomeMaterial) {
        JButton botaoMaterial = new JButton(nomeMaterial);
        botaoMaterial.addActionListener(e -> alterarMaterial( objeto, material, nomeMaterial));
        return botaoMaterial;
    }

    /**
     *Altera o material do objeto e atualiza a janela principal.
     */
    private void alterarMaterial( Intersectable objeto, Material material, String nomeMaterial) {
        objeto.setMaterial(material); //Aplica o material no objeto
        mainController.atualizarCena();
        JOptionPane.showMessageDialog(this, "Material alterado para: " + nomeMaterial);
    }

    /**
     *Abre o seletor de cores para alterar a cor do objeto.
     */
    private void alterarCorObjeto( Intersectable objeto) {
        Color novaCor = JColorChooser.showDialog(this, "Selecione uma cor", Color.WHITE);
        System.out.println(novaCor);
        if (novaCor != null) {
            objeto.setCor(novaCor); 
            System.out.println(objeto);
            mainController.atualizarCena();
            JOptionPane.showMessageDialog(this, "Cor alterada com sucesso!");
        }
    }

    /**
     *Cria o botão "Cancelar" da primeira tela.
     */
    private JButton criarBotaoCancelar() {
        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> {
            System.out.println("Ação cancelada");
            dispose();
        });
        return cancelar;
    }

    private JButton criarSelecionarObjeto(Intersectable objeto){
        JButton selecionar = new JButton("Selecionar");
        selecionar.addActionListener(e -> {
            //checar a selecao de algum objeto e setar o novo objeto para ser transformado no mainController
            int idx = mainController.getIdxObject(objeto);
            System.out.println("Esse é o objeto: " + objeto.getClass());
            System.out.println("O indice é esse: " + idx);
            mainController.getTecladoListener().setIdxObjetoTransformado(mainController.getIdxObject(objeto));
            dispose();
        });
        return selecionar;
    }
}
