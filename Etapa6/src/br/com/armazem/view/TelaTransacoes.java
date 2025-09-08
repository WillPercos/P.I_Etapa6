package br.com.armazem.view;

import br.com.armazem.model.Transacao;
import br.com.armazem.model.Usuario;
import br.com.armazem.service.TransacaoService; // Importação da camada Service

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaTransacoes extends JFrame {
    private DefaultTableModel model;
    private Usuario usuario;
    private TransacaoService transacaoService; // Dependência para a camada Service

    public TelaTransacoes(Usuario usuario) {
        this.usuario = usuario;
        this.transacaoService = new TransacaoService(); // Instância do Service
        setTitle("Transações");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Transações", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(34, 139, 34));
        panel.add(label, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Data e Hora", "Tipo", "ID Funcionário", "Itens"};
        model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel();
        JTextField txtBuscarTipo = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar por Tipo");

        searchPanel.add(new JLabel("Tipo de Transação:"));
        searchPanel.add(txtBuscarTipo);
        searchPanel.add(btnBuscar);
        panel.add(searchPanel, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> {
            String tipoTransacao = txtBuscarTipo.getText();
            if (!tipoTransacao.trim().isEmpty()) {
                try {
                    // Chama o método da camada Service para buscar transações por tipo
                    List<Transacao> transacoes = transacaoService.buscarTransacoesPorTipo(tipoTransacao);
                    model.setRowCount(0);
                    for (Transacao transacao : transacoes) {
                        model.addRow(new Object[]{
                                transacao.getId(),
                                transacao.getDataHora(),
                                transacao.getTipo(),
                                transacao.getFuncionarioId(),
                                transacao.getItens()
                        });
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao buscar transações: " + ex.getMessage());
                }
            }
        });

        add(panel);
        carregarTransacoes();
    }

    private void carregarTransacoes() {
        try {
            // Chama o método da camada Service para listar todas as transações
            List<Transacao> transacoes = transacaoService.listarTransacoes();
            model.setRowCount(0);
            for (Transacao transacao : transacoes) {
                model.addRow(new Object[]{
                        transacao.getId(),
                        transacao.getDataHora(),
                        transacao.getTipo(),
                        transacao.getFuncionarioId(),
                        transacao.getItens()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar transações: " + e.getMessage());
        }
    }
}