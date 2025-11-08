package com.fitlife;

public class Professor {
    private String id;
    private String nome;
    private String registro;
    private String especializacao;

    public Professor(String id, String nome, String registro, String especializacao) {
        this.id = id;
        this.nome = nome;
        this.registro = registro;
        this.especializacao = especializacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }
}
