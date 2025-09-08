package br.com.armazem.view;

import br.com.armazem.model.Relatorio;
import br.com.armazem.model.Usuario;
import br.com.armazem.service.RelatorioService; // Importação da camada Service

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaRelatorios extends JFrame {
    private DefaultTableModel model;
    private Usuario usuario;
    private RelatorioService relatorioService; // Dependência para a camada Service

    public TelaRelatorios(Usuario usuario) {
        this.usuario = usuario;
        this.relatorioService = new RelatorioService(); // Instância do Service

        setTitle("Relatórios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Relatórios");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(34, 139, 34));
        topPanel.add(lblTitulo);

        panel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Total de Transações", "Total de Saídas", "Total de Entradas", "Data de Geração", "Item Relacionado"};
        model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.add(scrollPane);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Visão Geral de Transações por Item",
                "Item",
                "Quantidade",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        centerPanel.add(chartPanel);

        panel.add(centerPanel, BorderLayout.CENTER);

        add(panel);

        carregarRelatorios();
        atualizarGrafico(dataset);
    }

    private void carregarRelatorios() {
        try {
            // Chama o método da camada Service para listar os relatórios
            List<Relatorio> relatorios = relatorioService.listarRelatorios();
            model.setRowCount(0);
            for (Relatorio relatorio : relatorios) {
                model.addRow(new Object[]{
                        relatorio.getId(),
                        relatorio.getTotalTransacoes(),
                        relatorio.getTotalSaidas(),
                        relatorio.getTotalEntradas(),
                        relatorio.getDataGeracao(),
                        relatorio.getItemId()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar relatórios: " + e.getMessage());
        }
    }

    private void atualizarGrafico(DefaultCategoryDataset dataset) {
        try {
            // Chama o método da camada Service para listar os relatórios
            List<Relatorio> relatorios = relatorioService.listarRelatorios();
            dataset.clear();
            for (Relatorio relatorio : relatorios) {
                dataset.addValue(relatorio.getTotalTransacoes(), "Total Transações", "Item " + relatorio.getItemId());
                dataset.addValue(relatorio.getTotalSaidas(), "Total Saídas", "Item " + relatorio.getItemId());
                dataset.addValue(relatorio.getTotalEntradas(), "Total Entradas", "Item " + relatorio.getItemId());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar gráfico: " + e.getMessage());
        }
    }
}