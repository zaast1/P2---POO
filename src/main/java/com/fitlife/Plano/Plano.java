package com.fitlife.Plano;

// Classe Abstrata:
public abstract class Plano {
    protected int id;
    protected String nome;
    protected double valor;
    protected int duracaoDias;

    public Plano(int id, String nome, double valor, int duracaoDias) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.duracaoDias = duracaoDias;
    }

    // Métodos:
    public abstract boolean temAcessoExclusivoAulas();
    public abstract boolean getPodeReservarArea();

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getValor() { return valor; }
    public int getDuracaoDias() { return duracaoDias; }

    //Decide se o cliente é VIP ou Básico com base no ID ou Nome.
    public static Plano criarPlanoPorId(int id, String nomePlano) {
        String nomeUpper = (nomePlano != null) ? nomePlano.toUpperCase() : "";

        // REGRA BLINDADA: Aceita ID 99, ID 4 ou se o nome gritar "VIP"
        if (id == 99 || id == 4 || nomeUpper.contains("VIP")) {
            return new PlanoVip(id);
        }

        if (id == 365 || id == 3 || nomeUpper.contains("ANUAL")) {
            return new PlanoAnual(id);
        }

        // Se não for nada disso, assume que é o básico mensal pra não dar erro.
        return new PlanoMensal(id);
    }
}