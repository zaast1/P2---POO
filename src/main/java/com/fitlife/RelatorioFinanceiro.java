package com.fitlife;

import com.fitlife.Aula.Aula;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

public class RelatorioFinanceiro {

    // Dependências (Gestão e Finanças)
    private ServicoDeGestaoFitLife servicoGestao;
    private ServicoFinanceiro servicoFinanceiro;

    public RelatorioFinanceiro(ServicoDeGestaoFitLife servicoGestao, ServicoFinanceiro servicoFinanceiro) {
        this.servicoGestao = servicoGestao;
        this.servicoFinanceiro = servicoFinanceiro;
    }

    // BI (Business Intelligence) usando Java Streams
    // Soma tudo que entrou no caixa
    double calcularReceitaTotal(){
        return servicoFinanceiro.getTodasTransacoes().stream().mapToDouble(Transacao::getValor).sum();}

    // Agrupa receita por plano (VIP, Mensal...)
    public Map<String, Double> calcularReceitaPorPlano() {
        return servicoFinanceiro.getTodasTransacoes().stream().collect(Collectors.groupingBy(
                Transacao::getTipoPlano,
                Collectors.summingDouble(Transacao::getValor)
        ));
    }

    // Calcula de onde vem o dinheiro por modalidade
    public Map<String, Double> calcularReceitaPorModalidade() {
        List<Transacao> transacoes = servicoFinanceiro.getTodasTransacoes();
        List<Aula> aulas = servicoGestao.getTodasAulas();

        // Conta quantas aulas de cada tipo existem
        Map<String, Long> contagemAulas = aulas.stream().collect(Collectors.groupingBy(
                a -> a.getModalidade().getNome(),
                Collectors.counting()
        ));
        double receitaTotal = calcularReceitaTotal();
        long totalAulas = contagemAulas.values().stream().mapToLong(l -> l).sum();

        if (totalAulas == 0) {return new HashMap<>();}

        // Distribui a receita proporcionalmente
        return contagemAulas.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (receitaTotal * entry.getValue()) / totalAulas
                ));
    }

    public void gerarRelatorio() {
        Map<String, Double> receitaPorPlano = calcularReceitaPorPlano();
        Map<String, Double> receitaPorModalidade = calcularReceitaPorModalidade();
        double receitaTotal = calcularReceitaTotal();

        System.out.println("\n=============================================");
        System.out.println("       📊 RELATÓRIO FINANCEIRO ($$$)         ");
        System.out.println("=============================================");
        System.out.printf(">>> RECEITA TOTAL: R$ %.2f (Dá pra pagar o churrasco?)\n\n", receitaTotal);

        System.out.println("--- Por Plano ---");
        if (receitaPorPlano.isEmpty()) System.out.println("Ninguém pagou nada ainda.");
        else receitaPorPlano.forEach((plano, valor) -> System.out.printf(" - %-10s: R$ %.2f\n", plano, valor));

        System.out.println("\n--- Por Modalidade (Estimativa) ---");
        if (receitaPorModalidade.isEmpty()) System.out.println("Sem aulas cadastradas.");
        else receitaPorModalidade.forEach((mod, valor) -> System.out.printf(" - %-10s: R$ %.2f\n", mod, valor));

        System.out.println("=============================================");
    }
}