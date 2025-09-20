package br.com.armazem.view;

import br.com.armazem.model.Item;
import br.com.armazem.model.Usuario;
import br.com.armazem.service.ItemService; // Importação da nova camada Service

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaInventario extends JFrame {
    private DefaultTableModel model;
    private Usuario usuario;
    private ItemService itemService; // Dependência para a camada Service

    public TelaInventario(Usuario usuario) {
        this.usuario = usuario;
        this.itemService = new ItemService(); // Instância do Service
        setTitle("Inventário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Inventário");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(34, 139, 34));
        topPanel.add(lblTitulo);
        JButton btnAdicionar = new JButton("Adicionar Novo Item");
        btnAdicionar.setBackground(new Color(34, 139, 34));
        btnAdicionar.setForeground(Color.WHITE);
        topPanel.add(btnAdicionar);

        panel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Nome", "Quantidade", "Data de Entrada", "Data de Validade", "Lote", "Ponto de Ressuprimento"};
        model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de busca e ações
        JPanel searchPanel = new JPanel();
        JTextField txtBuscarItem = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar por Item");
        JButton btnDeletar = new JButton("Deletar Item");
        JButton btnVoltar = new JButton("Voltar"); // Novo botão

        searchPanel.add(new JLabel("Nome do Item:"));
        searchPanel.add(txtBuscarItem);
        searchPanel.add(btnBuscar);
        searchPanel.add(btnDeletar);
        searchPanel.add(btnVoltar); // Adiciona o novo botão

        panel.add(searchPanel, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {
            // Lógica para adicionar um novo item (pode abrir uma nova janela ou um formulário)
            JOptionPane.showMessageDialog(this, "Funcionalidade de Adicionar Novo Item será implementada na próxima fase.");
        });

        btnBuscar.addActionListener(e -> {
            String nomeItem = txtBuscarItem.getText();
            if (!nomeItem.trim().isEmpty()) {
                try {
                    // Chamada para o Service
                    List<Item> itens = itemService.buscarItemPorNome(nomeItem);
                    model.setRowCount(0);
                    for (Item item : itens) {
                        model.addRow(new Object[]{
                                item.getId(),
                                item.getNome(),
                                item.getQuantidade(),
                                item.getDataEntrada(),
                                item.getDataValidade(),
                                item.getLote(),
                                item.getPontoRessuprimento()
                        });
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao buscar item: " + ex.getMessage());
                }
            }
        });

        btnDeletar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int itemId = (int) model.getValueAt(selectedRow, 0);
                try {
                    itemService.deletarItem(itemId);
                    JOptionPane.showMessageDialog(null, "Item deletado com sucesso!");
                    carregarItens(); // Atualiza a tabela
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao deletar item: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um item para deletar.");
            }
        });

        // Ação do novo botão
        btnVoltar.addActionListener(e -> {
            new TelaDashboard(usuario).setVisible(true);
            dispose();
        });

        add(panel);
        carregarItens();
    }

    private void carregarItens() {
        try {
            // Chamada para o Service
            List<Item> itens = itemService.listarTodosItens();
            model.setRowCount(0);
            for (Item item : itens) {
                model.addRow(new Object[]{
                        item.getId(),
                        item.getNome(),
                        item.getQuantidade(),
                        item.getDataEntrada(),
                        item.getDataValidade(),
                        item.getLote(),
                        item.getPontoRessuprimento()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar itens: " + e.getMessage());
        }
    }
}