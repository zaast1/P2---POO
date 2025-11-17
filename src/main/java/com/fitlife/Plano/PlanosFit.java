package com.fitlife.Plano;


import com.fitlife.Aluno.Aluno;

//criando ionterface para os planos da academia
public interface PlanosFit {
    String NomePlano();          //nome do plano escolhido na classe

    String detalharPlano(Aluno aluno);

    //deve retoranar os valores e informações do plano, como
                                 //plano mensal ==> 120 por mês acesso à musculação em tais horarios...
    double getValorDoPlano();
    int getDuracaDeDias();
    boolean getPodeReservarArea();




}
