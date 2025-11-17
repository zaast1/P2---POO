package com.fitlife.Plano;

import com.fitlife.Aluno.Aluno;
import com.fitlife.Plano.PlanosFit;

// Não usa a classe Plano base, apenas a interface
public class PlanoVip implements PlanosFit {

    // ATRIBUTOS (precisam ser declarados aqui, já que não há herança de Plano)
    private double valor;
    private int duracaoDias;

    // Construtor
    public PlanoVip(double valor, int duracaoDias) {
        this.valor = valor;
        this.duracaoDias = duracaoDias;
    }

    // Implementa todos os métodos da interface PlanosFit
    @Override
    public String NomePlano() { /* ... */ return "Plano VIP"; }

    @Override
    public String detalharPlano(Aluno aluno) {
        return "";
    }

    @Override
    public double getValorDoPlano() {
        return this.valor;
    }

    @Override
    public int getDuracaDeDias() {
        return this.duracaoDias;
    }

    // O método crucial de Lógica VIP (Polimorfismo)
    @Override
    public boolean getPodeReservarArea() {
        return true;
    }
}