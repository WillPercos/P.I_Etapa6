package br.com.armazem.service;

import br.com.armazem.dao.TransacaoDAO;
import br.com.armazem.model.Transacao;

import java.sql.SQLException;
import java.util.List;

public class TransacaoService {
    private TransacaoDAO transacaoDAO;

    public TransacaoService() {
        this.transacaoDAO = new TransacaoDAO();
    }

    public void adicionarTransacao(Transacao transacao) throws SQLException {
        // Lógica de negócio, como validação ou cálculos complexos
        transacaoDAO.adicionarTransacao(transacao);
    }

    public List<Transacao> listarTransacoes() throws SQLException {
        return transacaoDAO.listarTransacoes();
    }

    public List<Transacao> buscarTransacoesPorTipo(String tipo) throws SQLException {
        // Lógica de negócio para a busca
        return transacaoDAO.buscarTransacoesPorTipo(tipo);
    }
}