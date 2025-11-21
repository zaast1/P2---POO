package com.fitlife.Plano;

import com.fitlife.Aluno.Aluno;

public class PlanoAnual implements PlanosFit {
    @Override
    public String NomePlano() {
        return "============== PLANO MENSAL =================" +
                "Olá " +
                "essas são a as caractetisticas do seu plano na nossa academia " +
                "VALOR = 1200" +
                "HORÁRIOS MUSCULAÇÃO= 24H" +
                "DIREITO DE MODALIDADES DE AULA DISTINTAS  = "; //ESPARANDO MODALIDADES DECIDIDAS
    }

    @Override
    public double getValorDoPlano() {
        return 1200;
    }

    @Override
    public int getDuracaDeDias() {
        return 365;
    }

    @Override
    public boolean getPodeReservarArea() {
        return false;
    }
}
