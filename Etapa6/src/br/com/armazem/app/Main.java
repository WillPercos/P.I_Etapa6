package br.com.armazem.app;

import br.com.armazem.view.TelaLogin;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Usar para testar
            // login: joaos
            // senha: senha123
            // Inicia a tela de login
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setVisible(true);
        });
    }
}

