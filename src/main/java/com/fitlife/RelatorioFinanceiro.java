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

    // Calcula a receita total de todas as transações.
    double calcularReceitaTotal(){
        return servicoFinanceiro.getTodasTransacoes().stream().mapToDouble(Transacao::getValor).sum();}

    // Calcula a receita agregada por tipo de plano (VIP, Mensal, Anual).
    public Map<String, Double> calcularReceitaPorPlano() {
        return servicoFinanceiro.getTodasTransacoes().stream().collect(Collectors.groupingBy(
                        Transacao::getTipoPlano, // Agrupa pelo tipo de plano
                        Collectors.summingDouble(Transacao::getValor) // Soma o valor
                ));
    }

    // Calcula a receita agregada por modalidade
    public Map<String, Double> calcularReceitaPorModalidade() {
        List<Transacao> transacoes = servicoFinanceiro.getTodasTransacoes();
        List<Aula> aulas = servicoGestao.getTodasAulas();
        Map<String, Long> contagemAulasPorModalidade = aulas.stream().collect(Collectors.groupingBy(
                        a -> a.getModalidade().getNome(),
                        Collectors.counting()
                ));
        double receitaTotal = calcularReceitaTotal();
        long totalAulasOfertadas = contagemAulasPorModalidade.values().stream().mapToLong(l -> l).sum();

        if (totalAulasOfertadas == 0) {return new HashMap<>();} // Não há dados pra calcular

        // Calcula a receita por modalidade
        return contagemAulasPorModalidade.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (receitaTotal * entry.getValue()) / totalAulasOfertadas
                ));
    }


    // --- MÉTODOS DE FORMATAÇÃO E APRESENTAÇÃO ---

    // Gera e imprime o relatório financeiro final no console.

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