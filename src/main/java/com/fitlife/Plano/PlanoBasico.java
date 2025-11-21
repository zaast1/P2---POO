package com.fitlife.Plano;

public class PlanoBasico extends Plano {
    public PlanoBasico(int id, double valor, int duracaoDias) {
        super(id, valor, duracaoDias);
    }

    // Implementação Polimórfica: Não tem acesso exclusivo a aulas VIP
    @Override
    public boolean temAcessoExclusivoAulas() {
        return false; // Nega o acesso exclusivo
    }

    // Implementação para o Membro 3 (Reserva de Área)
    @Override
    public boolean getPodeReservarArea() {
        return false; // Nega reserva de área
    }
}
