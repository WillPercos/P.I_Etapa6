package br.com.armazem.dao;

import br.com.armazem.model.Funcionario;
import br.com.armazem.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    public void adicionarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO Funcionario (Nome, Sobrenome, cargo, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getSobrenome());
            stmt.setString(3, funcionario.getCargo());
            stmt.setString(4, funcionario.getEmail());
            stmt.executeUpdate();
            System.out.println("Funcionario adicionado com sucesso: " + funcionario);
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar funcionario: " + e.getMessage());
            throw e;
        }
    }

    public List<Funcionario> listarFuncionarios() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("Nome"),
                        rs.getString("Sobrenome"),
                        rs.getString("cargo"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
            throw e;
        }
        return funcionarios;
    }
    
    public Funcionario buscarFuncionarioPorId(int id) throws SQLException {
        String sql = "SELECT f.id, f.Nome, f.Sobrenome, f.cargo, f.email, GROUP_CONCAT(tf.telefone_celular) AS telefone FROM Funcionario f JOIN Telefone_Funcionario tf ON f.id = tf.funcionario_id WHERE f.id = ? GROUP BY f.id, f.Nome, f.Sobrenome, f.cargo, f.email";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                            rs.getInt("id"),
                            rs.getString("Nome"),
                            rs.getString("Sobrenome"),
                            rs.getString("cargo"),
                            rs.getString("email"),
                            rs.getString("telefone")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public void atualizarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE Funcionario SET Nome = ?, Sobrenome = ?, cargo = ?, email = ? WHERE id = ?";
        String sqlTelefone = "UPDATE Telefone_Funcionario SET telefone_celular = ? WHERE funcionario_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement stmtTelefone = conn.prepareStatement(sqlTelefone)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getSobrenome());
            stmt.setString(3, funcionario.getCargo());
            stmt.setString(4, funcionario.getEmail());
            stmt.setInt(5, funcionario.getId());
            stmt.executeUpdate();

            stmtTelefone.setString(1, funcionario.getTelefone());
            stmtTelefone.setInt(2, funcionario.getId());
            stmtTelefone.executeUpdate();

            System.out.println("Funcionario atualizado com sucesso: " + funcionario);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionario: " + e.getMessage());
            throw e;
        }
    }

    public void deletarFuncionario(int id) throws SQLException {
        String sql = "DELETE FROM Funcionario WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Funcionario deletado com sucesso: ID " + id);
            } else {
                System.out.println("Nenhum funcionario encontrado com ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar funcionario: " + e.getMessage());
            throw e;
        }
    }
}