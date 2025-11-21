package com.fitlife.Plano;

public class PlanoAnual extends Plano {

    public PlanoAnual(int id) {
        super(3, "Plano Anual", 1200.00, 365);
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