package br.com.armazem.view;

import br.com.armazem.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TelaDashboard extends JFrame {
    private final Map<String, JFrame> telas = new HashMap<>();
    private Usuario usuario;

    public TelaDashboard(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));
        menuPanel.setBackground(new Color(60, 63, 65));

        JLabel lblMenu = new JLabel("Menu de Navegação");
        lblMenu.setFont(new Font("Arial", Font.BOLD, 16));
        lblMenu.setForeground(Color.WHITE);
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(lblMenu);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        String[] menuItems = {"Dashboard", "Inventário", "Transações", "Relatórios", "Alertas", "Perfil", "Configurações", "Sair"};

        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(180, 40));
            button.setBackground(new Color(75, 110, 175));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.addActionListener(e -> {
                switch (item) {
                    case "Inventário":
                        abrirTela(new TelaInventario(usuario));
                        break;
                    case "Transações":
                        abrirTela(new TelaTransacoes(usuario));
                        break;
                    case "Relatórios":
                        abrirTela(new TelaRelatorios(usuario));
                        break;
                    case "Alertas":
                        abrirTela(new TelaAlertas(usuario));
                        break;
                    case "Perfil":
                        abrirTela(new TelaPerfil(usuario));
                        break;
                    case "Configurações":
                        abrirTela(new TelaConfiguracoes(usuario));
                        break;
                    case "Dashboard":
                        // Já está na tela do dashboard, apenas garante que está visível
                        break;
                    case "Sair":
                        dispose();
                        break;
                }
            });
            menuPanel.add(button);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        mainPanel.add(menuPanel, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Título principal
        JLabel lblDashboard = new JLabel("Dashboard Principal");
        lblDashboard.setFont(new Font("Arial", Font.BOLD, 28));
        lblDashboard.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(lblDashboard, gbc);

        // Painel de Visão Geral
        JPanel overviewPanel = new JPanel(new GridBagLayout());
        overviewPanel.setBorder(BorderFactory.createTitledBorder("Visão Geral do Estoque"));
        overviewPanel.setBackground(new Color(240, 255, 240));
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(overviewPanel, gbc);

        // Adicionando ícones e gráficos
        ImageIcon estoqueIcon = new ImageIcon("path/to/estoque_icon.png");
        JLabel lblItensEstoque = new JLabel("Itens em Estoque: 8", estoqueIcon, JLabel.LEFT);
        lblItensEstoque.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        overviewPanel.add(lblItensEstoque, gbc);

        ImageIcon alertasIcon = new ImageIcon("path/to/alertas_icon.png");
        JLabel lblAlertasRecentes = new JLabel("Alertas Recentes: 2", alertasIcon, JLabel.LEFT);
        lblAlertasRecentes.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        overviewPanel.add(lblAlertasRecentes, gbc);

        ImageIcon transacoesIcon = new ImageIcon("path/to/transacoes_icon.png");
        JLabel lblTransacoesRecentes = new JLabel("Transações Recentes: 7", transacoesIcon, JLabel.LEFT);
        lblTransacoesRecentes.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        overviewPanel.add(lblTransacoesRecentes, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void abrirTela(JFrame tela) {
        if (!telas.containsKey(tela.getTitle())) {
            telas.put(tela.getTitle(), tela);
        }
        telas.get(tela.getTitle()).setVisible(true);
        dispose();
    }
}