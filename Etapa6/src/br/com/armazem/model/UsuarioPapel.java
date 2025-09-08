package br.com.armazem.model;

public class UsuarioPapel {
    private int id;
    private int usuarioId;
    private int papelId;

    public UsuarioPapel(int id, int usuarioId, int papelId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.papelId = papelId;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public int getPapelId() { return papelId; }
    public void setPapelId(int papelId) { this.papelId = papelId; }
}


