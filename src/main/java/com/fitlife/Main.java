package com.fitlife;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializa o serviço (carrega dados CSV e inicia as listas)
        ServicoDeGestaoFitLife servico = new ServicoDeGestaoFitLife();

        System.out.println("\n=============================================");
        System.out.println("  SIMULAÇÃO FITLIFE-ACADEMIA (MÓDULO MEMBRO 1)");
        System.out.println("=============================================");

        try {
            // 2. Criação de Dados Mínimos de Teste (Se os arquivos CSV estiverem vazios)
            if (servico.getTodasModalidades().isEmpty()) {
                System.out.println("-> Criando dados iniciais para teste...");

                // CADASTRO: Modalidades e Professor (Requisito: Cadastro de Modalidades e Horários)
                servico.cadastrarModalidade("Musculação", "Treino de força");
                servico.cadastrarModalidade("Pilates Exclusivo", "Aula VIP");
                servico.cadastrarProfessor("Ana Souza", "R1", "Musculação");

                // AGENDAMENTO: Aulas (Atribuição de Tarefas)
                servico.agendarNovaAula(2, 1, "08:00", "Segunda", true);  // Aula VIP
                servico.agendarNovaAula(1, 1, "10:00", "Segunda", false); // Aula Normal

                // SIMULAÇÃO DE ALUNOS (Encapsulamento corrigido)
                servico.adicionarAlunoParaTeste(new Aluno(10L, "Carlos (Básico)", new PlanoBasico(10, "Mensal")));
                servico.adicionarAlunoParaTeste(new Aluno(99L, "Julia (VIP)", new PlanoVip(99)));
                servico.salvarTodosDados(); // Salva todos os dados, incluindo os alunos de simulação
                System.out.println("-> Dados iniciais criados e salvos em CSV.");
            }

            // 3. DEMONSTRAÇÃO DO FILTRO VIP (Lógica de Acesso VIP a Aulas)

            // Simulação 1: Aluno Básico (ID 10)
            System.out.println("\n--- 🔎 TESTE 1: Aluno Básico (Sem Acesso VIP) ---");
            List<Aula> aulasBasicas = servico.listarAulasDisponiveis(10L);
            System.out.println("Total de Aulas Liberadas: " + aulasBasicas.size());
            aulasBasicas.forEach(a -> System.out.println(" [OK] " + a.getModalidade().getNome() +
                    (a.isExclusivaVIP() ? " (VIP)" : " (Normal)")));

            // Simulação 2: Aluno VIP (ID 99)
            System.out.println("\n--- 👑 TESTE 2: Aluno VIP (Com Acesso Exclusivo) ---");
            List<Aula> aulasVIP = servico.listarAulasDisponiveis(99L);
            System.out.println("Total de Aulas Liberadas: " + aulasVIP.size());
            aulasVIP.forEach(a -> System.out.println(" [OK] " + a.getModalidade().getNome() +
                    (a.isExclusivaVIP() ? " (VIP)" : " (Normal)")));

            // NOTA: Para o Membro 4 (Relatórios), ele usaria: servico.getTodasAulas()

        } catch (Exception e) {
            System.err.println("Erro Crítico durante a Demonstração: " + e.getMessage());
        }
        System.out.println("\n--- Demonstração Funcional Concluída ---");
    }
}