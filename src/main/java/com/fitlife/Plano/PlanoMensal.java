package com.fitlife.Plano;

public abstract class PlanoMensal implements PlanosFit {

    protected double valor;
    protected int duracaoDias;

    public PlanoMensal(double valor, int duracaoDias) {
        this.valor = valor;
        this.duracaoDias = duracaoDias;
    }

    // Implementação comum dos getters (herdada por PlanoVip, PlanoMensal, etc.)
    @Override
    public double getValorDoPlano() {
        return valor;
    }

    @Override
    public int getDuracaDeDias() {
        return duracaoDias;
    }

    // Deixa o método de acesso exclusivo e o nome do plano para a implementação específica
    public abstract boolean getPodeReservarArea();;
    public abstract String NomePlano();
}

