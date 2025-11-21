package com.fitlife;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Frequencia {
    private long alunoId;
    private LocalDateTime dataHora;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Frequencia(long alunoId, LocalDateTime dataHora) {
        this.alunoId = alunoId;
        this.dataHora = dataHora;
    }

    public Frequencia(String csvLine) throws Exception {
        String[] dados = csvLine.split(";");
        if (dados.length != 2) {
            throw new IllegalArgumentException("Linha CSV inválida para Frequencia.");
        }

        this.alunoId = Long.parseLong(dados[0].trim());
        this.dataHora = LocalDateTime.parse(dados[1].trim(), FORMATTER);
    }

    public String toCSV() {
        return alunoId + ";" + dataHora.format(FORMATTER);
    }

    public long getAlunoId() {
        return alunoId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}