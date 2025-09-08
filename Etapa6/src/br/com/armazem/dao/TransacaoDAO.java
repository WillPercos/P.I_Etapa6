package br.com.armazem.dao;

import br.com.armazem.model.Transacao;
import br.com.armazem.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {
    public void adicionarTransacao(Transacao transacao) throws SQLException {
        String sql = "INSERT INTO Transacao(data, tipo, funcionario_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, transacao.getDataHora());
            stmt.setString(2, transacao.getTipo());
            stmt.setInt(3, transacao.getFuncionarioId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int transacaoId = rs.getInt(1);
                adicionarItensTransacao(transacaoId, transacao.getItens());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar transação: " + e.getMessage());
            throw e;
        }
    }

    private void adicionarItensTransacao(int transacaoId, String itens) throws SQLException {
        String[] itensArray = itens.split(",");
        String sql = "INSERT INTO Transacao_item(transacao_id, item_id, Quantidade) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (String item : itensArray) {
                String[] partes = item.split(":");
                stmt.setInt(1, transacaoId);
                stmt.setInt(2, Integer.parseInt(partes[0]));
                stmt.setInt(3, Integer.parseInt(partes[1]));
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar itens da transação: " + e.getMessage());
            throw e;
        }
    }

    public List<Transacao> listarTransacoes() throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT t.id, t.data, t.tipo, t.funcionario_id, GROUP_CONCAT(CONCAT(ti.item_id, ':', ti.Quantidade) SEPARATOR ',') AS itens " +
                     "FROM Transacao t " +
                     "JOIN Transacao_item ti ON t.id = ti.transacao_id " +
                     "GROUP BY t.id, t.data, t.tipo, t.funcionario_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Transacao transacao = new Transacao(
                        rs.getInt("id"),
                        rs.getString("data"),
                        rs.getString("tipo"),
                        rs.getInt("funcionario_id"),
                        rs.getString("itens")
                );
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar transações: " + e.getMessage());
            throw e;
        }
        return transacoes;
    }

    public List<Transacao> buscarTransacoesPorTipo(String tipo) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT t.id, t.data, t.tipo, t.funcionario_id, GROUP_CONCAT(CONCAT(ti.item_id, ':', ti.Quantidade) SEPARATOR ',') AS itens " +
                     "FROM Transacao t " +
                     "JOIN Transacao_item ti ON t.id = ti.transacao_id " +
                     "WHERE t.tipo = ? " +
                     "GROUP BY t.id, t.data, t.tipo, t.funcionario_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transacao transacao = new Transacao(
                        rs.getInt("id"),
                        rs.getString("data"),
                        rs.getString("tipo"),
                        rs.getInt("funcionario_id"),
                        rs.getString("itens")
                    );
                    transacoes.add(transacao);
                }
            } catch (SQLException e) {
                System.err.println("Erro ao buscar transações por tipo: " + e.getMessage());
                throw e;
            }
        }
        return transacoes;
    }
}