package br.com.armazem.model;

public class Alerta {
    private int id;
    private String tipoAlerta;
    private String dataHora;
    private int itemId;

    public Alerta(int id, String tipoAlerta, String dataHora, int itemId) {
        this.id = id;
        this.tipoAlerta = tipoAlerta;
        this.dataHora = dataHora;
        this.itemId = itemId;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipoAlerta() { return tipoAlerta; }
    public void setTipoAlerta(String tipoAlerta) { this.tipoAlerta = tipoAlerta; }
    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
}

