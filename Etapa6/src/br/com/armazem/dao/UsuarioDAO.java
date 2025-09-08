package br.com.armazem.dao;

import br.com.armazem.model.Usuario;
import br.com.armazem.database.DatabaseConnection;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    public Usuario autenticarUsuario(String nomeUsuario, String senhaDigitada) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE Nome_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String senhaHash = rs.getString("Senha_hashed");
                    System.out.println("Senha armazenada: " + senhaHash);

                    // Verifica a senha digitada com o hash armazenado (BCrypt)
                    if (BCrypt.verifyer().verify(senhaDigitada.toCharArray(), senhaHash).verified) {
                        System.out.println("Autenticação bem-sucedida!");
                        return new Usuario(
                                rs.getInt("id"),
                                rs.getString("Nome_usuario"),
                                senhaHash,
                                rs.getString("Area_acesso"),
                                rs.getInt("Funcionario_id")
                        );
                    } else {
                        System.out.println("Senha incorreta!");
                    }
                } else {
                    System.out.println("Usuário não encontrado!");
                }
            }
        }
        return null;
    }
}