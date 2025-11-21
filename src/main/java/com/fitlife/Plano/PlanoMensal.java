package com.fitlife.Plano;

public class PlanoMensal extends Plano{
    public PlanoMensal(int id, double valor, int duracaoDias) {
        super(id, valor, duracaoDias);
    }

    @Override
    public boolean temAcessoExclusivoAulas() {
        return false;
    }

    @Override
    public boolean getPodeReservarArea() {
        return false;
    }
}
