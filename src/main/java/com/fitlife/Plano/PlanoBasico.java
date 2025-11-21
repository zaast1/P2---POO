package com.fitlife.Plano;

public class PlanoBasico extends Plano {

    // CORREÇÃO: Construtor ajustado para receber ID e Nome, como a Main espera.
    public PlanoBasico(int id, String nome) {
        // Chama o construtor da classe base (Plano) com os valores fixos:
        super(id, nome, 120.00, 30);
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