package src.view;

import javax.swing.*;
import java.awt.*;
import src.model.interseccao.Intersectable;
import src.model.materiais.MateriaisPadrao;
import src.model.materiais.Material;

public class OpcaoJanela extends JDialog {

    private final MateriaisPadrao materiaisPadrao;

    public OpcaoJanela(Janela parent, Intersectable objeto) {
        super(parent, "Opções do Objeto", true);
        this.materiaisPadrao = new MateriaisPadrao();

        configurarJanela(parent, objeto);
        setLocationRelativeTo(parent); // Centraliza a janela em relação à janela pai
    }

    /**
     * Configura a janela principal.
     */
    private void configurarJanela(Janela parent, Intersectable objeto) {
        setLayout(new BorderLayout());
          
        add(criarLabelInformativo(objeto), BorderLayout.NORTH);
        add(criarPainelCentral(parent, objeto), BorderLayout.CENTER);
        add(criarBotaoCancelar(), BorderLayout.SOUTH);
        pack(); //Ajusta o tamanho da janela de acordo com o conteúdo
    }

    /**
     * Cria o rótulo informativo no topo da janela.
     */
    private JLabel criarLabelInformativo(Intersectable objeto) {
        JLabel label = new JLabel("Você clicou no objeto: " + objeto);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Cria o painel central com as opções de materiais e alteração de cor.
     */
    private JPanel criarPainelCentral(Janela parent, Intersectable objeto) {
        JPanel painelCentral = new JPanel(new BorderLayout());

        //Botão para alterar material
        JButton alterarMaterial = new JButton("Alterar Material");
        alterarMaterial.addActionListener(e -> alterarMaterialObjeto(parent, objeto));

        //Botão para alterar cor
        JButton alterarCor = new JButton("Alterar Cor");
        alterarCor.addActionListener(e -> alterarCorObjeto(parent, objeto));

        //Adiciona os botões de alteração de material e cor no topo
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelSuperior.add(alterarMaterial);
        painelSuperior.add(alterarCor);

        painelCentral.add(painelSuperior, BorderLayout.NORTH);

        return painelCentral;
    }

    /**
     * Abre uma janela com as opções de materiais.
     */
    private void alterarMaterialObjeto(Janela parent, Intersectable objeto) {
        //Criando a janela de materiais
        JDialog materialDialog = new JDialog(this, "Escolher Material", true);
        materialDialog.setSize(400, 300);
        materialDialog.setLocationRelativeTo(this); //Centraliza a janela de materiais
    
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 colunas, espaçamento de 10px
        String[] nomesMateriais = materiaisPadrao.getNomesMateriais();
        Material[] materiais = materiaisPadrao.getTodosMateriais();
    
        //Adiciona os botões de materiais à grade
        for (int i = 0; i < nomesMateriais.length; i++) {
            gridPanel.add(criarBotaoMaterial(parent, objeto, materiais[i], nomesMateriais[i]));
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
    private JButton criarBotaoMaterial(Janela parent, Intersectable objeto, Material material, String nomeMaterial) {
        JButton botaoMaterial = new JButton(nomeMaterial);
        botaoMaterial.addActionListener(e -> alterarMaterial(parent, objeto, material, nomeMaterial));
        return botaoMaterial;
    }

    /**
     *Altera o material do objeto e atualiza a janela principal.
     */
    private void alterarMaterial(Janela parent, Intersectable objeto, Material material, String nomeMaterial) {
        objeto.setMaterial(material); //Aplica o material no objeto
        parent.pintarCanvas(); //Atualiza a visualização no canvas
        parent.repaint(); //Repaint para refletir as mudanças
        JOptionPane.showMessageDialog(this, "Material alterado para: " + nomeMaterial);
    }

    /**
     *Abre o seletor de cores para alterar a cor do objeto.
     */
    private void alterarCorObjeto(Janela parent, Intersectable objeto) {
        Color novaCor = JColorChooser.showDialog(this, "Selecione uma cor", Color.WHITE);
        System.out.println(novaCor);
        if (novaCor != null) {
            objeto.setCor(novaCor); // Assumindo que Intersectable tem um método setCor
            parent.pintarCanvas();
            parent.repaint();
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
}
