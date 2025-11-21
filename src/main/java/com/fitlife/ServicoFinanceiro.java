package com.fitlife;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServicoFinanceiro {

    // Armazenamento de dados do módulo
    private List<Transacao> transacoes = new ArrayList<>();
    // Dependência do serviço de gestão para obter dados mestres (Aulas, Modalidades)
    private ServicoDeGestaoFitLife servicoGestao;
    private static final String TRANSACOES_ARQUIVO = "transacoes.csv";


    public ServicoFinanceiro(ServicoDeGestaoFitLife servicoGestao) {
        this.servicoGestao = servicoGestao;
        carregarTransacoes();
    }

    // --- MÉTODOS DE PERSISTÊNCIA (CSV) ---

    private void carregarTransacoes() {
        File arquivo = new File(TRANSACOES_ARQUIVO);
        if (!arquivo.exists()){return;}
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))){
            String linha;
            while ((linha = br.readLine()) != null){
                if (!linha.trim().isEmpty()){
                    try{
                        // Usa o construtor parser da Transacao
                        Transacao t = new Transacao(linha);
                        transacoes.add(t);
                    } catch (Exception e) {
                        System.err.println("Erro ao processar linha em Transacoes.csv: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro de leitura no arquivo Transacoes.csv: " + e.getMessage());
        }
    }

    private void salvarTransacoes() {
        if (transacoes.isEmpty()) { new File(TRANSACOES_ARQUIVO).delete(); return; }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACOES_ARQUIVO))) {
            for (Transacao t : transacoes) {
                bw.write(t.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo Transacoes.csv: " + e.getMessage());
        }
    }

    // --- LÓGICA DE NEGÓCIO (REGISTRO E GESTÃO) ---
    // Registra um novo pagamento (Transacao) no sistema.

    public Transacao registrarPagamento(double valor, String data, long alunoId, String tipoPlano) throws IllegalArgumentException {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo.");
        }
        // 1. Gera o novo ID (ID atual + 1)
        long novoId = transacoes.stream().mapToLong(Transacao::getTransacaoId).max().orElse(0) + 1;
        // 2. Cria o objeto e adiciona à lista em memória
        Transacao novaTransacao = new Transacao(novoId, valor, data, alunoId, tipoPlano);
        transacoes.add(novaTransacao);
        // 3. Salva a lista completa no arquivo CSV
        salvarTransacoes();
        return novaTransacao;
    }
     //Implementa a lógica de status de pagamento (simples).

    public boolean checarStatusPagamento(long alunoId) {
        // Verifica se há alguma transação recente para o aluno.
        return transacoes.stream()
                .anyMatch(t -> t.getAlunoId() == alunoId);
    }

    // --- MÉTODOS DE RELATÓRIOS E CONSULTA (Serão implementados na RelatorioFinanceiro) ---
    public List<Transacao> getTodasTransacoes() {
        return new ArrayList<>(transacoes);
    }
}