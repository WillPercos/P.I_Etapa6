package br.com.armazem.view;

import br.com.armazem.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class TelaConfiguracoes extends JFrame {
    private JComboBox<String> temaComboBox;
    private JCheckBox notificacoesCheckBox;
    private JTextField permissoesTextField;
    private JButton salvarButton;
    private JButton cancelarButton;
    private JButton voltarButton;
    private Usuario usuario;

    public TelaConfiguracoes(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Configurações");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tema:"), gbc);
        temaComboBox = new JComboBox<>(new String[]{"Claro", "Escuro"});
        gbc.gridx = 1;
        panel.add(temaComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Notificações:"), gbc);
        notificacoesCheckBox = new JCheckBox();
        gbc.gridx = 1;
        panel.add(notificacoesCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Permissões de Acesso:"), gbc);
        permissoesTextField = new JTextField(20);
        permissoesTextField.setText(usuario.getAreaAcesso());
        permissoesTextField.setEditable(false); // Permissões são exibidas, não alteradas aqui
        gbc.gridx = 1;
        panel.add(permissoesTextField, gbc);

        salvarButton = new JButton("Salvar");
        cancelarButton = new JButton("Cancelar");
        voltarButton = new JButton("Voltar");

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(salvarButton, gbc);
        gbc.gridx = 1;
        panel.add(cancelarButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(voltarButton, gbc);

        add(panel);

        salvarButton.addActionListener(e -> {
            String temaSelecionado = (String) temaComboBox.getSelectedItem();
            boolean notificacoesAtivadas = notificacoesCheckBox.isSelected();
            String permissoesDeAcesso = permissoesTextField.getText();

            JOptionPane.showMessageDialog(this, "Configurações salvas:\nTema: " + temaSelecionado + "\nNotificações: " + (notificacoesAtivadas ? "Ativadas" : "Desativadas") + "\nPermissões de Acesso: " + permissoesDeAcesso);
        });

        cancelarButton.addActionListener(e -> {
            temaComboBox.setSelectedIndex(0);
            notificacoesCheckBox.setSelected(false);
        });

        voltarButton.addActionListener(e -> {
            new TelaDashboard(usuario).setVisible(true);
            dispose();
        });
    }
}