package com.fitlife.Plano;

public class PlanoVip extends Plano {

    // CORRIGIDO: Aceita apenas ID e chama o super() com valores fixos
    public PlanoVip(int id) {
        super(4, "Plano VIP", 2640.00, 365);
    }

    @Override
    public boolean temAcessoExclusivoAulas() {
        return true; // CORRIGIDO: Plano VIP deve liberar acesso
    }

    @Override
    public boolean getPodeReservarArea() {
        return true; // CORRIGIDO: Plano VIP deve liberar reserva
    }
}