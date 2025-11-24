package com.fitlife;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoFinanceiro {

    private List<Transacao> transacoes = new ArrayList<>();
    // Dependência do gestor principal para saber quem é quem
    private ServicoDeGestaoFitLife servicoGestao;
    private static final String TRANSACOES_ARQUIVO = "transacoes.csv";

    public ServicoFinanceiro(ServicoDeGestaoFitLife servicoGestao) {
        this.servicoGestao = servicoGestao;
        carregarTransacoes();
    }

    // Lê os pagamentos do disco (se existirem)
    private void carregarTransacoes() {
        File arquivo = new File(TRANSACOES_ARQUIVO);
        if (!arquivo.exists()){return;}
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))){
            String linha;
            while ((linha = br.readLine()) != null){
                if (!linha.trim().isEmpty()){
                    try{
                        Transacao t = new Transacao(linha);
                        transacoes.add(t);
                    } catch (Exception e) {
                        System.err.println("Erro ao ler transação: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro de leitura: " + e.getMessage());
        }
    }

    // Salva a grana no disco
    private void salvarTransacoes() {
        if (transacoes.isEmpty()) { new File(TRANSACOES_ARQUIVO).delete(); return; }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACOES_ARQUIVO))) {
            for (Transacao t : transacoes) {
                bw.write(t.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro crítico: Não consegui salvar a transação!" + e.getMessage());
        }
    }

    // --- REGISTRO DE PAGAMENTO ---
    public Transacao registrarPagamento(double valor, String data, long alunoId, String tipoPlano) throws IllegalArgumentException {
        if (valor <= 0) {
            throw new IllegalArgumentException("Pagamento negativo? O aluno quer dinheiro emprestado?");
        }
        long novoId = transacoes.stream().mapToLong(Transacao::getTransacaoId).max().orElse(0) + 1;
        Transacao novaTransacao = new Transacao(novoId, valor, data, alunoId, tipoPlano);
        transacoes.add(novaTransacao);
        salvarTransacoes(); // Salva imediatamente pra não perder dinheiro
        return novaTransacao;
    }

    // Verifica se o aluno pagou
    public boolean checarStatusPagamento(long alunoId) {
        return transacoes.stream().anyMatch(t -> t.getAlunoId() == alunoId);
    }

    public List<Transacao> getTodasTransacoes() {
        return new ArrayList<>(transacoes);
    }
}