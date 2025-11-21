package com.fitlife.recursos;

public class Equipamento {
    private int id;
    private String nome;
    private boolean disponivel;

    public Equipamento(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.disponivel = true;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public String getNome() {
        return nome;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }



}
