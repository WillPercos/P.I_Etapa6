package br.com.armazem.dao;

import br.com.armazem.model.Item;
import br.com.armazem.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public void adicionarItem(Item item) throws SQLException {
        String sql = "INSERT INTO Item (Nome, Quantidade, Data_entrada, Data_validade, Lote, Ponto_ressuprimento) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getNome());
            stmt.setInt(2, item.getQuantidade());
            stmt.setString(3, item.getDataEntrada());
            stmt.setString(4, item.getDataValidade());
            stmt.setString(5, item.getLote());
            stmt.setInt(6, item.getPontoRessuprimento());
            stmt.executeUpdate();
        }
    }

    public List<Item> listarItens() throws SQLException {
        List<Item> itens = new ArrayList<>();
        String sql = "SELECT * FROM Item";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("Nome"),
                        rs.getInt("Quantidade"),
                        rs.getString("Data_entrada"),
                        rs.getString("Data_validade"),
                        rs.getString("Lote"),
                        rs.getInt("Ponto_ressuprimento")
                );
                itens.add(item);
            }
        }
        return itens;
    }

    public List<Item> buscarItensPorNome(String nomeItem) throws SQLException {
        List<Item> itens = new ArrayList<>();
        String sql = "SELECT * FROM Item WHERE Nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeItem);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(
                            rs.getInt("id"),
                            rs.getString("Nome"),
                            rs.getInt("Quantidade"),
                            rs.getString("Data_entrada"),
                            rs.getString("Data_validade"),
                            rs.getString("Lote"),
                            rs.getInt("Ponto_ressuprimento")
                    );
                    itens.add(item);
                }
            }
        }
        return itens;
    }

    public void atualizarItem(Item item) throws SQLException {
        String sql = "UPDATE Item SET Nome = ?, Quantidade = ?, Data_entrada = ?, Data_validade = ?, Lote = ?, Ponto_ressuprimento = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getNome());
            stmt.setInt(2, item.getQuantidade());
            stmt.setString(3, item.getDataEntrada());
            stmt.setString(4, item.getDataValidade());
            stmt.setString(5, item.getLote());
            stmt.setInt(6, item.getPontoRessuprimento());
            stmt.setInt(7, item.getId());
            stmt.executeUpdate();
        }
    }

    public void deletarItem(int id) throws SQLException {
        String sql = "DELETE FROM Item WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}