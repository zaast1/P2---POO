package com.fitlife.Plano;

import com.fitlife.Aluno.Aluno;

// Não usa a classe Plano base, apenas a interface
public class PlanoVip extends Plano {


    public PlanoVip(int id, double valor, int duracaoDias) {
        super(id, valor, duracaoDias);
    }

    @Override
    public boolean temAcessoExclusivoAulas() {
        return false;
    }

    @Override
    public boolean getPodeReservarArea() {
        return false; //Não é vip então não pode reservar a área
    }
}
