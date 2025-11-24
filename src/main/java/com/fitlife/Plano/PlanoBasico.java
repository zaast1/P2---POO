package com.fitlife.Plano;
public class PlanoBasico extends Plano {
    public PlanoBasico(int id, String nome) {
        super(id, nome, 120.00, 30);
    }
    @Override
    public boolean temAcessoExclusivoAulas() { return false; }
    @Override
    public boolean getPodeReservarArea() { return false; }
}