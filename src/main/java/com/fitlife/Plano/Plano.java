package com.fitlife.Plano;

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

    public abstract boolean temAcessoExclusivoAulas();
    public abstract boolean getPodeReservarArea();

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getValor() { return valor; }
    public int getDuracaoDias() { return duracaoDias; }

    public static Plano criarPlanoPorId(int id, String nomePlano) {
        if (id == 99 || nomePlano.toUpperCase().contains("VIP")) return new PlanoVip(id);
        if (id == 365 || nomePlano.toUpperCase().contains("ANUAL")) return new PlanoAnual(id);
        return new PlanoMensal(id);
    }
}