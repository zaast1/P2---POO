package com.fitlife.Recursos;

public class Equipamento {
    private int id;
    private String nome;
    private boolean disponivel;

    public Equipamento(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.disponivel = true;
    }

    //construtor que cria a partir do csv
    public Equipamento(String csvLine) {
        String[] dados = csvLine.split(";");
        this.id = Integer.parseInt(dados[0]);
        this.nome = dados[1];
        this.disponivel = Boolean.parseBoolean(dados[2]);
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public String getNome() {
        return nome;
    }

    public int getId(){
        return id;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String toCSV() {
        return id + ";" + nome + ";" + disponivel;
    }

}
