package br.com.armazem.service;

import br.com.armazem.dao.FuncionarioDAO;
import br.com.armazem.model.Funcionario;

import java.sql.SQLException;

public class FuncionarioService {
    private FuncionarioDAO funcionarioDAO;

    public FuncionarioService() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    public Funcionario buscarFuncionarioPorId(int id) throws SQLException {
        // Lógica de negócio, como validação ou formatação de dados, pode ser adicionada aqui
        return funcionarioDAO.buscarFuncionarioPorId(id);
    }
    
    public void atualizarFuncionario(Funcionario funcionario) throws SQLException {
        // Lógica de negócio, como validação dos campos antes de salvar
        if (funcionario.getNome() == null || funcionario.getNome().isEmpty() ||
            funcionario.getEmail() == null || funcionario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Nome e e-mail do funcionário são obrigatórios.");
        }
        funcionarioDAO.atualizarFuncionario(funcionario);
    }
}
