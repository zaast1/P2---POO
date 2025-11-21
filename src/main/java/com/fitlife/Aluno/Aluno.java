package com.fitlife.Aluno; // PACOTE CORRETO

// Não precisa importar Plano se estiverem no mesmo pacote

import com.fitlife.Plano.Plano;

public class Aluno {

    private long id; // Usar long (seu Servico usa long)
    private String nome;
    private Integer idade;

    //  COMPOSIÇÃO: Aluno TEM UM Plano
    private Plano plano;

    // Construtor Básico (Não usado, mas bom para a classe)
    public Aluno() {}

    // --- 1. Construtor Principal (USADO NO CÓDIGO) ---
    // Recebe o Plano como argumento para Composição.
    public Aluno(long id, String nome, Integer idade, Plano plano) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.plano = plano; //  Correção: Inicializa o objeto Plano
    }

    // --- 2. Construtor CSV (Parser - USADO PARA CARREGAR) ---
    public Aluno(String csvLine) throws Exception {
        String[] dados = csvLine.split(";");
        if (dados.length != 4) { // ID;NOME;IDADE;ID_PLANO
            throw new IllegalArgumentException("Linha CSV inválida para Aluno (Esperado 4 campos).");
        }

        try {
            this.id = Long.parseLong(dados[0].trim());
            this.nome = dados[1].trim();
            this.idade = Integer.parseInt(dados[2].trim());
            // O objeto Plano será atribuído pelo ServicoDeGestaoFitLife, então mantemos null por enquanto
            this.plano = null;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erro de formato numérico no Aluno CSV.");
        }
    }

    // --- 3. Método toCSV (USADO PARA SALVAR) ---
    @Override
    public String toCSV() { // Implementação correta
        long planoId = (plano != null) ? plano.getId() : 0;
        return id + ";" + nome + ";" + idade + ";" + planoId;
    }

    // --- Getters e Setters (Mantidos por Encapsulamento) ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNome() { return nome; }
    public Integer getIdade() { return idade; }
    public Plano getPlano() { return plano; } // Getter essencial para o Filtro VIP
    public void setPlano(Plano plano) { this.plano = plano; } // Necessário para reconstruir a Composição

    // toString para debug
    @Override
    public String toString() {
        return "Aluno{" + id + ", " + nome + ", " + idade + ", Plano=" + (plano != null ? plano.getNome() : "N/A") + "}";
    }
}