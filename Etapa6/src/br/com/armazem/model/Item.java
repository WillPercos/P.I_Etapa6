package br.com.armazem.model;

public class Item {
    private int id;
    private String nome;
    private int quantidade;
    private String dataEntrada;
    private String dataValidade;
    private String lote;
    private int pontoRessuprimento;

    public Item(int id, String nome, int quantidade, String dataEntrada, String dataValidade, String lote, int pontoRessuprimento) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.dataEntrada = dataEntrada;
        this.dataValidade = dataValidade;
        this.lote = lote;
        this.pontoRessuprimento = pontoRessuprimento;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public String getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(String dataEntrada) { this.dataEntrada = dataEntrada; }
    public String getDataValidade() { return dataValidade; }
    public void setDataValidade(String dataValidade) { this.dataValidade = dataValidade; }
    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }
    public int getPontoRessuprimento() { return pontoRessuprimento; }
    public void setPontoRessuprimento(int pontoRessuprimento) { this.pontoRessuprimento = pontoRessuprimento; }
}

