package br.com.armazem.view;

import br.com.armazem.model.Usuario;
import br.com.armazem.service.UsuarioService; // Importação da nova camada Service

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TelaLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private UsuarioService usuarioService; // Dependência para a camada Service

    public TelaLogin() {
        this.usuarioService = new UsuarioService(); // Instância do Service
        setTitle("Tela de Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(60, 63, 65));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitulo = new JLabel("Armazém Agrícola");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblUsuario, gbc);

        txtUsuario = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtUsuario, gbc);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblSenha, gbc);

        txtSenha = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtSenha, gbc);

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 18));
        btnLogin.setBackground(new Color(75, 110, 175));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String senha = new String(txtSenha.getPassword());

                try {
                    // Agora a chamada é para o Service, não mais para o DAO
                    Usuario user = usuarioService.autenticarUsuario(usuario, senha);
                    if (user != null) {
                        JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                        new TelaDashboard(user).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao autenticar: " + ex.getMessage());
                }
            }
        });

        add(panel);
    }
}