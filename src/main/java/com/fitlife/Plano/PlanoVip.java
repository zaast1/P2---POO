package com.fitlife.Plano;
public class PlanoVip extends Plano {
    public PlanoVip(int id) {
        super(4, "Plano VIP", 2640.00, 365); // O plano que paga nosso salário
    }
    @Override
    public boolean temAcessoExclusivoAulas() { return true; } // VIP pode tudo
    @Override
    public boolean getPodeReservarArea() { return true; }
}