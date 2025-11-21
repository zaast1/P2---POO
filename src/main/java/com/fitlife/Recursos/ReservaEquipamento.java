package com.fitlife.Recursos;

import com.fitlife.Aluno.Aluno;
import com.fitlife.Plano.PlanoVip;

import java.time.LocalDateTime;

public class ReservaEquipamento {
    private Aluno aluno;
    private Equipamento equipamento;
    private LocalDateTime dataHora;

    public ReservaEquipamento(Aluno aluno, Equipamento equipamento, LocalDateTime dataHora) {
        if (!(aluno.getPlano() instanceof  PlanoVip)) {
            throw new IllegalArgumentException("BLOQUEADO: O Aluno" + aluno.getNome() + "Não é VIP!");
        }

        if (!equipamento.isDisponivel()) {
            throw new IllegalArgumentException("BLOQUEADO: O equipamento " + equipamento.getNome() + " está ocupado!");
        }

        this.aluno = aluno;
        this.equipamento = equipamento;
        this.dataHora = dataHora;
        this.equipamento.setDisponivel(false);
    }

    @Override
    public String toString() {
        return "Reserva Confirmada: " + equipamento.getNome() + " para " + aluno.getNome();
    }

    public String toCSV() {
        return aluno.getId() + ";" + equipamento.getId() + ";" + dataHora.toString();
    }
}
