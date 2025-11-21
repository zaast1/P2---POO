package com.fitlife.Plano;

import com.fitlife.Aluno.Aluno;

public abstract class Plano {

    //Atributos gerais que devem existir em qualquer plano
    protected int id;
    protected double valor;
    protected int duracaoDias;

    private Plano plano;

    //construtor para incializar os atributos gerais
    public Plano(int id, double valor, int duracaoDias) {

        this.id = id;
        this.valor = valor;
        this.duracaoDias = duracaoDias;

    }

    public abstract boolean temAcessoExclusivoAulas();
    public abstract boolean getPodeReservarArea();
}


