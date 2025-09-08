package br.com.armazem.util;

import br.com.armazem.database.DatabaseConnection;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AtualizarSenhas {
    public static void atualizarSenhas() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String selectSql = "SELECT id, Nome_usuario, Senha_hashed FROM Usuario";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("id");
                String nomeUsuario = rs.getString("Nome_usuario");
                String senha = "senha123"; // Use a senha original aqui

                // Verifica se a senha já está hashada
                if (!BCrypt.verifyer().verify(senha.toCharArray(), rs.getString("Senha_hashed")).verified) {
                    String senhaBcrypt = BCrypt.withDefaults().hashToString(12, senha.toCharArray());

                    String updateSql = "UPDATE Usuario SET Senha_hashed = ? WHERE id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setString(1, senhaBcrypt);
                    updateStmt.setInt(2, userId);
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}