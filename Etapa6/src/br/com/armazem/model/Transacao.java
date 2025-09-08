package br.com.armazem.model;

public class Transacao {
    private int id;
    private String dataHora;
    private String tipo;
    private int funcionarioId;
    private String itens;

    public Transacao(int id, String dataHora, String tipo, int funcionarioId, String itens) {
        this.id = id;
        this.dataHora = dataHora;
        this.tipo = tipo;
        this.funcionarioId = funcionarioId;
        this.itens = itens;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }
    public String getItens() { return itens; }
    public void setItens(String itens) { this.itens = itens; }
}

