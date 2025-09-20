package br.com.armazem.view;

import br.com.armazem.model.Alerta;
import br.com.armazem.model.Usuario;
import br.com.armazem.service.AlertaService; // Importação da nova camada Service

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaAlertas extends JFrame {
    private DefaultTableModel model;
    private Usuario usuario;
    private AlertaService alertaService; // Dependência para a camada Service

    public TelaAlertas(Usuario usuario) {
        this.usuario = usuario;
        this.alertaService = new AlertaService(); // Instância do Service
        setTitle("Alertas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Alertas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(34, 139, 34));
        topPanel.add(lblTitulo);

        panel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Tipo de Alerta", "Data e Hora", "Item Relacionado"};
        model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de busca e ações
        JPanel searchPanel = new JPanel();
        JTextField txtBuscarItem = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar por Item");
        JButton btnMarcarResolvido = new JButton("Marcar como Resolvido");
        JButton btnVoltar = new JButton("Voltar"); // Novo botão

        searchPanel.add(new JLabel("Nome do Item:"));
        searchPanel.add(txtBuscarItem);
        searchPanel.add(btnBuscar);
        searchPanel.add(btnMarcarResolvido);
        searchPanel.add(btnVoltar); // Adiciona o novo botão

        panel.add(searchPanel, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> {
            String nomeItem = txtBuscarItem.getText();
            if (!nomeItem.trim().isEmpty()) {
                try {
                    // Agora a chamada é para o Service, não mais para o DAO
                    List<Alerta> alertas = alertaService.buscarAlertasPorItem(nomeItem);
                    model.setRowCount(0);
                    for (Alerta alerta : alertas) {
                        model.addRow(new Object[]{
                                alerta.getId(),
                                alerta.getTipoAlerta(),
                                alerta.getDataHora(),
                                alerta.getItemId()
                        });
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao buscar alertas: " + ex.getMessage());
                }
            }
        });
        
        btnMarcarResolvido.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int alertaId = (int) model.getValueAt(selectedRow, 0);
                try {
                    alertaService.marcarAlertaComoResolvido(alertaId);
                    JOptionPane.showMessageDialog(null, "Alerta marcado como resolvido!");
                    carregarAlertas();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao marcar alerta: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um alerta para marcar como resolvido.");
            }
        });

        // Ação do novo botão
        btnVoltar.addActionListener(e -> {
            new TelaDashboard(usuario).setVisible(true);
            dispose();
        });

        add(panel);
        carregarAlertas();
    }

    private void carregarAlertas() {
        try {
            // Agora a chamada é para o Service
            List<Alerta> alertas = alertaService.listarTodosAlertas();
            model.setRowCount(0);
            for (Alerta alerta : alertas) {
                model.addRow(new Object[]{
                        alerta.getId(),
                        alerta.getTipoAlerta(),
                        alerta.getDataHora(),
                        alerta.getItemId()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alertas: " + e.getMessage());
        }
    }
}