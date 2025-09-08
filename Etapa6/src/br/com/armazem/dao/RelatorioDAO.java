package br.com.armazem.dao;

import br.com.armazem.model.Relatorio;
import br.com.armazem.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {
    public void gerarRelatorio() throws SQLException {
        String sql = "INSERT INTO Relatorio (total_transacoes, total_saidas, total_entradas, data_geracao, item_id) VALUES (?, ?, ?, NOW(), ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 10);
            stmt.setInt(2, 5);
            stmt.setInt(3, 5);
            stmt.setInt(4, 1);
            stmt.executeUpdate();
        }
    }

    public List<Relatorio> listarRelatorios() throws SQLException {
        List<Relatorio> relatorios = new ArrayList<>();
        String sql = "SELECT * FROM Relatorio";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Relatorio relatorio = new Relatorio(
                        rs.getInt("id"),
                        rs.getInt("total_transacoes"),
                        rs.getInt("total_saidas"),
                        rs.getInt("total_entradas"),
                        rs.getString("data_geracao"),
                        rs.getInt("item_id")
                );
                relatorios.add(relatorio);
            }
        }
        return relatorios;
    }
}