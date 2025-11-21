package com.fitlife.Historico;

import com.fitlife.Aluno.Aluno;

public class HistoricoTreino {
    private Aluno aluno;
    private String data;
    private String atividade;
    private String feedback;

    public HistoricoTreino(Aluno aluno, String data, String atividade, String feedback) {
        this.aluno = aluno;
        this.atividade = atividade;
        this.data = data;
        this.feedback = feedback;
    }

    public void ImprimirRelatorio() {
        System.out.println("--- RELATÓRIO DE PERFORMANCE ---");
        System.out.println("Aluno: " + aluno.getNome());
        System.out.println("Data: " + data);
        System.out.println("Atividade: " + atividade);
        System.out.println("Feedback do Instrutor: " + feedback);
        System.out.println("--------------------------------");
    }
}
