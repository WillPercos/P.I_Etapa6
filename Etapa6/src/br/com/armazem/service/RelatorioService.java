package br.com.armazem.service;

import br.com.armazem.dao.RelatorioDAO;
import br.com.armazem.model.Relatorio;

import java.sql.SQLException;
import java.util.List;

public class RelatorioService {
    private RelatorioDAO relatorioDAO;

    public RelatorioService() {
        this.relatorioDAO = new RelatorioDAO();
    }

    public List<Relatorio> listarRelatorios() throws SQLException {
        // Lógica de negócio pode ser adicionada aqui, como filtros complexos
        return relatorioDAO.listarRelatorios();
    }
}