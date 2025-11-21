package com.fitlife.Plano;

public abstract class Plano {

    //Atributos gerais que devem existir em qualquer plano
    protected int id;
    protected double valor;
    protected int duracaoDias;

    //construtor para incializar os atributos gerais
    public Plano(int id,double valor, int duracaoDias){

        this.id = id;
        this.valor = valor;
        this.duracaoDias = duracaoDias;

    }

    //Método para validar acesso a aulas exclusivas
    public abstract boolean temAcessoExclusivoAulas();

    //Método para validar se tem direito a reserva de áreas
    public abstract boolean getPodeReservarArea();

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public int getDuracaoDias() {
        return duracaoDias;
    }
}
