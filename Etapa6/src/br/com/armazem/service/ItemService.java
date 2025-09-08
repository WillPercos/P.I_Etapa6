package br.com.armazem.service;

import br.com.armazem.dao.ItemDAO;
import br.com.armazem.model.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemService {
    private ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    public List<Item> listarTodosItens() throws SQLException {
        return itemDAO.listarItens();
    }

    public List<Item> buscarItemPorNome(String nomeItem) throws SQLException {
        return itemDAO.buscarItensPorNome(nomeItem);
    }
    
    public void adicionarItem(Item item) throws SQLException {
        itemDAO.adicionarItem(item);
    }
    
    public void atualizarItem(Item item) throws SQLException {
        itemDAO.atualizarItem(item);
    }
    
    public void deletarItem(int id) throws SQLException {
        itemDAO.deletarItem(id);
    }
}