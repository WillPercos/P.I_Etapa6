package br.com.armazem.dao;

import br.com.armazem.model.Alerta;
import br.com.armazem.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {
    public void adicionarAlerta(Alerta alerta) throws SQLException {
        String sql = "INSERT INTO Alerta (Tipo_alerta, Data_hora, item_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alerta.getTipoAlerta());
            stmt.setString(2, alerta.getDataHora());
            stmt.setInt(3, alerta.getItemId());
            stmt.executeUpdate();
        }
    }

    public List<Alerta> listarAlertas() throws SQLException {
        List<Alerta> alertas = new ArrayList<>();
        String sql = "SELECT * FROM Alerta";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Alerta alerta = new Alerta(
                        rs.getInt("id"),
                        rs.getString("Tipo_alerta"),
                        rs.getString("Data_hora"),
                        rs.getInt("item_id")
                );
                alertas.add(alerta);
            }
        }
        return alertas;
    }

    public List<Alerta> buscarAlertasPorItem(String nomeItem) throws SQLException {
        List<Alerta> alertas = new ArrayList<>();
        String sql = "SELECT a.* FROM Alerta a JOIN Item i ON a.item_id = i.id WHERE i.Nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeItem);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Alerta alerta = new Alerta(
                            rs.getInt("id"),
                            rs.getString("Tipo_alerta"),
                            rs.getString("Data_hora"),
                            rs.getInt("item_id")
                    );
                    alertas.add(alerta);
                }
            }
        }
        return alertas;
    }

    public void marcarResolvido(int id) throws SQLException {
        String sql = "DELETE FROM Alerta WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}