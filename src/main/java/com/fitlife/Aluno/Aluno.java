package com.fitlife.Aluno;

import com.fitlife.Plano.Plano;

public class Aluno {
    private long id;
    private String nome;
    private Integer idade;
    private Plano plano; // Composição: O Aluno TEM UM Plano

    public Aluno(long id, String nome, Integer idade, Plano plano) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.plano = plano;
    }

    // Construtor que lê do CSV.
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

            // Chama a fábrica de planos. Se o ID for 4 ou 99, vira VIP
            this.plano = Plano.criarPlanoPorId(planoId, "Nome Placeholder");

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erro de formato numérico. Alguém digitou letra onde era número.");
        }
    }

    // Transforma o objeto de volta em texto para salvar no arquivo (Persistência).
    public String toCSV() {
        long planoId = (plano != null) ? plano.getId() : 0;
        return id + ";" + nome + ";" + idade + ";" + planoId;
    }

    // Getters e Setters
    public long getId() { return id; }
    public String getNome() { return nome; }
    public Integer getIdade() { return idade; }
    public Plano getPlano() { return plano; }
    public void setPlano(Plano plano) { this.plano = plano; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}