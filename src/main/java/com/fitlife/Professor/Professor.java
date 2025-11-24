package com.fitlife.Professor;

public class Professor {
    private int id; private String nome; private String registro; private String especializacao;

    public Professor(int id, String nome, String registro, String especializacao) {
        this.id = id;
        this.nome = nome;
        this.registro = registro;
        this.especializacao = especializacao;
    }

    // Leitura do CSV
    public Professor(String csvLine) {
        String[] dados = csvLine.split(";");
        if (dados.length == 4) {
            this.id = Integer.parseInt(dados[0].trim());
            this.nome = dados[1].trim();
            this.registro = dados[2].trim();
            this.especializacao = dados[3].trim();
        } else {
            throw new IllegalArgumentException("CSV do Professor tá quebrado: " + csvLine);
        }
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getRegistro() { return registro; }
    public void setRegistro(String registro) { this.registro = registro; }
    public String getEspecializacao() { return especializacao; }

    // Método(corrige o nome do atributo)
    public void setEspecializacao(String especializacao) { this.especializacao = especializacao; }
    public void setEspecialidade(String novaEsp) { this.especializacao = novaEsp; }

    public String toCSV() {
        return id + ";" + nome + ";" + registro + ";" + especializacao;
    }
}