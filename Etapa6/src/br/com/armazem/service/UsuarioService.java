package br.com.armazem.service;

import br.com.armazem.dao.UsuarioDAO;
import br.com.armazem.model.Usuario;

import java.sql.SQLException;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticarUsuario(String nomeUsuario, String senha) throws SQLException {
        // Validação básica da entrada
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Usuário e senha não podem estar vazios.");
        }
        // Aqui poderia haver outras regras de negócio, como verificação de tentativas de login, etc.
        return usuarioDAO.autenticarUsuario(nomeUsuario, senha);
    }
}