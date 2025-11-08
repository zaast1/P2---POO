package com.fitlife;

public class Modalidade {
    private int id;
    private String nome;
    private String descricao;

    public Modalidade(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Modalidade(String csvLine) {
        String[] dados = csvLine.split(";");
        if (dados.length != 3){
            throw new IllegalArgumentException("Linha CSV inválida para Modalidade: " + csvLine);
        }

        try {
            this.id = Integer.parseInt(dados[0].trim());
            this.nome = dados[1].trim();
            this.descricao = dados[2].trim();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erro de formato numérico no ID da Modalidade: " + csvLine);
        }
    }

    public String toCSV() {
        return id + ";" + nome + ";" + descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}
