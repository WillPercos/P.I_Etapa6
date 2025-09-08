package br.com.armazem.service;

import br.com.armazem.dao.AlertaDAO;
import br.com.armazem.model.Alerta;

import java.sql.SQLException;
import java.util.List;

public class AlertaService {
    private AlertaDAO alertaDAO;

    public AlertaService() {
        this.alertaDAO = new AlertaDAO();
    }

    public List<Alerta> listarTodosAlertas() throws SQLException {
        // Lógica de negócio pode ser adicionada aqui, se necessário (ex: filtros, validações)
        return alertaDAO.listarAlertas();
    }

    public List<Alerta> buscarAlertasPorItem(String nomeItem) throws SQLException {
        // Lógica de negócio
        return alertaDAO.buscarAlertasPorItem(nomeItem);
    }

    public void adicionarAlerta(Alerta alerta) throws SQLException {
        alertaDAO.adicionarAlerta(alerta);
    }

    public void marcarAlertaComoResolvido(int id) throws SQLException {
        alertaDAO.marcarResolvido(id);
    }
}
