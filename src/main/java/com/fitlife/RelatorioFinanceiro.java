package com.fitlife;

import com.fitlife.Aula.Aula;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

public class RelatorioFinanceiro {

    // Dependências para obter os dados
    private ServicoDeGestaoFitLife servicoGestao;
    private ServicoFinanceiro servicoFinanceiro;

    public RelatorioFinanceiro(ServicoDeGestaoFitLife servicoGestao, ServicoFinanceiro servicoFinanceiro) {
        this.servicoGestao = servicoGestao;
        this.servicoFinanceiro = servicoFinanceiro;
    }

    // --- MÉTODOS DE CÁLCULO E AGREGAÇÃO (CONSULTAS DE BI) ---

    /**
     * Calcula a receita total de todas as transações.
     * @return A soma total dos valores das transações.
     */
    public double calcularReceitaTotal() {
        return servicoFinanceiro.getTodasTransacoes().stream()
                .mapToDouble(Transacao::getValor)
                .sum();
    }

    /**
     * Calcula a receita agregada por tipo de plano (VIP, Mensal, Anual).
     * Usa Java Streams para agrupar e somar os valores.
     * @return Um Map onde a chave é o tipo de plano e o valor é a receita total.
     */
    public Map<String, Double> calcularReceitaPorPlano() {
        return servicoFinanceiro.getTodasTransacoes().stream()
                .collect(Collectors.groupingBy(
                        Transacao::getTipoPlano, // Agrupa pelo tipo de plano
                        Collectors.summingDouble(Transacao::getValor) // Soma o valor
                ));
    }

    /**
     * Calcula a receita agregada por modalidade. (Consulta Complexa)
     * NOTA: Assume-se que o valor da transação de um plano cobre todas as modalidades
     * que o aluno frequentou no período. Aqui, faremos uma agregação simplificada.
     * @return Um Map onde a chave é o nome da modalidade e o valor é a receita total associada.
     */
    public Map<String, Double> calcularReceitaPorModalidade() {
        // 1. Obter todas as transações e todas as aulas (dados mestres)
        List<Transacao> transacoes = servicoFinanceiro.getTodasTransacoes();
        List<Aula> aulas = servicoGestao.getTodasAulas();

        // 2. Simplificação da Lógica: Assume-se que a receita do plano se aplica a todas as modalidades
        // Se a lógica fosse detalhada, seria necessário rastrear o check-in do aluno por aula (Membro 2).

        // Vamos agrupar as Aulas por Modalidade para saber quais foram ofertadas
        Map<String, Long> contagemAulasPorModalidade = aulas.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getModalidade().getNome(),
                        Collectors.counting()
                ));

        // Se houver transações e aulas, vamos distribuir o valor total das transações
        // pelo número total de aulas ofertadas, agrupando pela modalidade.

        double receitaTotal = calcularReceitaTotal();
        long totalAulasOfertadas = contagemAulasPorModalidade.values().stream().mapToLong(l -> l).sum();

        if (totalAulasOfertadas == 0) {
            return new HashMap<>(); // Não há dados para calcular
        }

        // Calcula a receita por modalidade baseada na frequência de oferta da aula
        return contagemAulasPorModalidade.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (receitaTotal * entry.getValue()) / totalAulasOfertadas
                ));
    }


    // --- MÉTODOS DE FORMATAÇÃO E APRESENTAÇÃO ---

    /**
     * Gera e imprime o relatório financeiro final no console.
     */
    public void gerarRelatorio() {
        Map<String, Double> receitaPorPlano = calcularReceitaPorPlano();
        Map<String, Double> receitaPorModalidade = calcularReceitaPorModalidade();
        double receitaTotal = calcularReceitaTotal();

        System.out.println("\n=============================================");
        System.out.println("       📊 RELATÓRIO FINANCEIRO FITLIFE       ");
        System.out.println("=============================================");

        System.out.printf(">>> RECEITA TOTAL: R$ %.2f\n\n", receitaTotal);

        System.out.println("--- Receita Detalhada por Tipo de Plano ---");
        if (receitaPorPlano.isEmpty()) {
            System.out.println("Nenhuma transação registrada.");
        } else {
            receitaPorPlano.forEach((plano, valor) ->
                    System.out.printf(" - %-10s: R$ %.2f\n", plano, valor)
            );
        }

        System.out.println("\n--- Receita Estimada por Modalidade ---");
        if (receitaPorModalidade.isEmpty() && !receitaPorPlano.isEmpty()) {
            System.out.println("Aulas não cadastradas para análise de distribuição.");
        } else {
            receitaPorModalidade.forEach((modalidade, valor) ->
                    System.out.printf(" - %-10s: R$ %.2f\n", modalidade, valor)
            );
        }
        System.out.println("=============================================");
    }
}