package com.fitlife.Plano;
public class PlanoMensal extends Plano {
    public PlanoMensal(int id) {
        super(2, "Plano Mensal", 120.00, 30);
    }
    @Override
    public boolean temAcessoExclusivoAulas() { return false; } // Não pagou o suficiente
    @Override
    public boolean getPodeReservarArea() { return false; }
}