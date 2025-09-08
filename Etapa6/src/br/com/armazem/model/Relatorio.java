package br.com.armazem.model;

public class Relatorio {
    private int id;
    private int totalTransacoes;
    private int totalSaidas;
    private int totalEntradas;
    private String dataGeracao;
    private int itemId;

    public Relatorio(int id, int totalTransacoes, int totalSaidas, int totalEntradas, String dataGeracao, int itemId) {
        this.id = id;
        this.totalTransacoes = totalTransacoes;
        this.totalSaidas = totalSaidas;
        this.totalEntradas = totalEntradas;
        this.dataGeracao = dataGeracao;
        this.itemId = itemId;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTotalTransacoes() { return totalTransacoes; }
    public void setTotalTransacoes(int totalTransacoes) { this.totalTransacoes = totalTransacoes; }
    public int getTotalSaidas() { return totalSaidas; }
    public void setTotalSaidas(int totalSaidas) { this.totalSaidas = totalSaidas; }
    public int getTotalEntradas() { return totalEntradas; }
    public void setTotalEntradas(int totalEntradas) { this.totalEntradas = totalEntradas; }
    public String getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(String dataGeracao) { this.dataGeracao = dataGeracao; }
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
}



