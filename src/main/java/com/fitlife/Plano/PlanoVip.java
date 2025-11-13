package com.fitlife.Plano;

import com.fitlife.Aluno.Aluno;

public class PlanoVip extends Aluno implements PlanosFit {
    @Override
    public String NomePlano() {
        return "============== PLANO MENSAL =================" +
                "Olá " + getNome() +
                "essas são a as caractetisticas do seu plano na nossa academia " +
                "VALOR = 1200" +
                "HORÁRIOS MUSCULAÇÃO= 24H" +
                "DIREITO DE MODALIDADES DE AULA DISTINTAS  = ";
    }

    @Override
    public double getValorDoPlano() {
        return 2640;
    }

    @Override
    public int getDuracaDeDias() {
        return 365;
    }

    @Override
    public boolean getPodeReservarArea() {   //esperando interface de modalidades
        return true;
    }
}
