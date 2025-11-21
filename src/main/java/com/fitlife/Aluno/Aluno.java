package com.fitlife.Aluno;

import com.fitlife.Plano.Plano;

public class Aluno {
    private long id;
    private String nome;
    private Integer idade;
    private Plano plano;

    public Aluno(long id, String nome, Integer idade, Plano plano) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.plano = plano;
    }

    public Aluno(String csvLine) throws Exception {
        String[] dados = csvLine.split(";");
        if (dados.length != 4) {
            throw new IllegalArgumentException("Linha CSV inválida para Aluno (Esperado 4 campos).");
        }

        try {
            this.id = Long.parseLong(dados[0].trim());
            this.nome = dados[1].trim();
            this.idade = Integer.parseInt(dados[2].trim());
            int planoId = Integer.parseInt(dados[3].trim());

            this.plano = Plano.criarPlanoPorId(planoId, "Nome Placeholder");

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erro de formato numérico no Aluno CSV.");
        }
    }

    public String toCSV() {
        long planoId = (plano != null) ? plano.getId() : 0;
        return id + ";" + nome + ";" + idade + ";" + planoId;
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public Integer getIdade() { return idade; }
    public Plano getPlano() { return plano; }
    public void setPlano(Plano plano) { this.plano = plano; }
}