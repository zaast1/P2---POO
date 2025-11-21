package com.fitlife;

import java.util.List;

public class MainFinanceiro {

    public static void main(String[] args) {

        // 1. Inicializa o serviço de gestão (Membro 1 - Carrega Professores, Modalidades, Aulas do CSV)
        ServicoDeGestaoFitLife servicoGestao = new ServicoDeGestaoFitLife();

        // 2. Inicializa o serviço financeiro, passando a dependência de gestão
        ServicoFinanceiro servicoFinanceiro = new ServicoFinanceiro(servicoGestao);

        // 3. Inicializa o gerador de relatórios (depende de ambos os serviços)
        RelatorioFinanceiro relatorio = new RelatorioFinanceiro(servicoGestao, servicoFinanceiro);

        System.out.println("\n=============================================");
        System.out.println("  💵 MÓDULO FINANCEIRO - DEMONSTRAÇÃO (Membro 4)");
        System.out.println("=============================================");

        try {
            // --- SIMULAÇÃO DE DADOS FINANCEIROS ---
            // Cria transações de exemplo se o arquivo Transacoes.csv estiver vazio
            if (servicoFinanceiro.getTodasTransacoes().isEmpty()) {
                System.out.println("-> Criando Transações de Exemplo...");

                // Transação 1: Aluno VIP pagando Anual VIP (ID 99)
                servicoFinanceiro.registrarPagamento(2640.00, "2025-11-01", 99L, "VIP");

                // Transação 2: Aluno Básico pagando Mensal (ID 10)
                servicoFinanceiro.registrarPagamento(120.00, "2025-11-05", 10L, "MENSAL");

                // Transação 3: Outro Aluno Básico pagando Mensal (ID 12)
                servicoFinanceiro.registrarPagamento(120.00, "2025-11-05", 12L, "MENSAL");

                // NOTA: Para que as Modalidades apareçam no relatório, elas devem estar cadastradas
                // no arquivo 'modalidades.csv' (feito pelo Membro 1 na simulação anterior).
            }

            // --- 4. GERAÇÃO DO RELATÓRIO FINAL ---
            System.out.println("\nIniciando Geração de Relatórios Gerenciais...");
            relatorio.gerarRelatorio();

            // --- TESTE DE CONSULTA INDIVIDUAL (Exemplo) ---
            System.out.println("\n--- Consulta de Status de Pagamento (Exemplo) ---");
            if (servicoFinanceiro.checarStatusPagamento(99L)) {
                System.out.println("Status: Aluno 99 (VIP) está em dia.");
            }

        } catch (Exception e) {
            System.err.println("Erro Crítico ao iniciar o Módulo Financeiro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}