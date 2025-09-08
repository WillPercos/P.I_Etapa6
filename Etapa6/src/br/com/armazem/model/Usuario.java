package br.com.armazem.model;

public class Usuario {
    private int id;
    private String nomeUsuario;
    private String senha;
    private String areaAcesso;
    private int funcionarioId;

    public Usuario(int id, String nomeUsuario, String senha, String areaAcesso, int funcionarioId) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.areaAcesso = areaAcesso;
        this.funcionarioId = funcionarioId;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getAreaAcesso() { return areaAcesso; }
    public void setAreaAcesso(String areaAcesso) { this.areaAcesso = areaAcesso; }
    public int getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }
}
