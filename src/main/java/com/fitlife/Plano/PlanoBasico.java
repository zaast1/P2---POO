package com.fitlife.Plano;

public class PlanoBasico extends Plano {
    // CONSTRUTOR CORRIGIDO: Agora aceita apenas 1 argumento (id)
    public PlanoBasico(int id) {
        // Chama o construtor da classe base (Plano) com os valores fixos:
        super(id, "Plano Básico", 120.00, 30);
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