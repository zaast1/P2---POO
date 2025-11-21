package com.fitlife;

public class Transacao {

    private long transacaoId;
    private double valor;
    private String data; // Para o CSV
    private long alunoId;
    private String tipoPlano;

    public Transacao(long transacaoId, double valor, String data, long alunoId, String tipoPlano) {
        this.transacaoId = transacaoId;
        this.valor = valor;
        this.data = data;
        this.alunoId = alunoId;
        this.tipoPlano = tipoPlano;
    }

    public Transacao(String csvLinha) throws IllegalArgumentException {
        String[] dados = csvLinha.split(";");
        if (dados.length != 5) {
            throw new IllegalArgumentException("Linha CSV inválida para Transacao.");
        }
        try {
            this.transacaoId = Long.parseLong(dados[0].trim());
            this.valor = Double.parseDouble(dados[1].trim());
            this.data = dados[2].trim();
            this.alunoId = Long.parseLong(dados[3].trim());
            this.tipoPlano = dados[4].trim();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erro de formato numérico no CSV de Transacao: " + e.getMessage());
        }
    }

    // 3. Metodo para Persistência CSV (Dados -> Arquivo CSV)
    public String toCSV() {
        return transacaoId + ";" + valor + ";" + data + ";" + alunoId + ";" + tipoPlano;
    }

    // ---------- Getters Setters -------------

    public long getTransacaoId() { return transacaoId; }
    public void setTransacaoId(long transacaoId) { this.transacaoId = transacaoId; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public long getAlunoId() { return alunoId; }
    public void setAlunoId(long alunoId) { this.alunoId = alunoId; }

    public String getTipoPlano() { return tipoPlano; }
    public void setTipoPlano(String tipoPlano) { this.tipoPlano = tipoPlano; }

    // ----------------------------------
}