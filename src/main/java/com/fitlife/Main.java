package com.fitlife;

import com.fitlife.Aluno.Aluno;
import com.fitlife.Aula.Aula;
import com.fitlife.Plano.PlanoBasico;
import com.fitlife.Plano.PlanoVip;
import com.fitlife.Recursos.Equipamento;
import com.fitlife.Recursos.ReservaEquipamento;
import com.fitlife.Historico.HistoricoTreino;
import com.fitlife.ServicoDeGestaoFitLife;
import com.fitlife.Aluno.Aluno;

import java.time.LocalDateTime;
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
                Aluno testeBasico = new Aluno(10L, "Carlos(Básico)", 25, new PlanoBasico(1));
                Aluno testeVIP = new Aluno(99L, "Julia(VIP)", 25, new PlanoVip(4));
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
        System.out.println("\n=============================================");
        System.out.println("  MÓDULO 3: RECURSOS E RESERVAS (SEU CÓDIGO)");
        System.out.println("=============================================");

        try {
            // 1. CRIAR EQUIPAMENTOS
            Equipamento esteira = new Equipamento(1, "Esteira Pro 2000");
            Equipamento supino = new Equipamento(2, "Banco Supino");
            System.out.println("-> Equipamentos criados: " + esteira.getNome() + " e " + supino.getNome());

            // 2. ALUNOS PARA TESTE DE RESERVA
            // Carlos (Plano Básico ID 1)
            Aluno alunoBasicoTeste = new Aluno(55L, "Carlos Teste", 20, new PlanoBasico(1));
            // Julia (Plano VIP ID 2)
            Aluno alunoVipTeste = new Aluno(99L, "Julia Teste", 25, new PlanoVip(2));

            // 3. TESTE: Aluno Comum tentando reservar (Deve ser BLOQUEADO)
            System.out.println("\n--- 🔒 Cenário A: Aluno Básico tenta reservar Esteira ---");
            try {
                new ReservaEquipamento(alunoBasicoTeste, esteira, LocalDateTime.now());
                System.out.println("ERRO: O sistema falhou e deixou reservar!");
            } catch (IllegalArgumentException e) {
                System.out.println("SUCESSO: Sistema bloqueou corretamente -> " + e.getMessage());
            }

            // 4. TESTE: Aluno VIP reservando (Deve dar CERTO)
            System.out.println("\n--- 🔓 Cenário B: Aluno VIP tenta reservar Esteira ---");
            try {
                ReservaEquipamento reserva = new ReservaEquipamento(alunoVipTeste, esteira, LocalDateTime.now());
                System.out.println("SUCESSO: " + reserva.toString()); // Se toString não existir, vai imprimir o hash do objeto, tudo bem
                System.out.println("Status da Esteira agora: " + (esteira.isDisponivel() ? "Livre" : "OCUPADA"));
            } catch (Exception e) {
                System.out.println("ERRO INESPERADO: " + e.getMessage());
            }

            // 5. TESTE: Equipamento Ocupado (Deve ser BLOQUEADO)
            System.out.println("\n--- ⚠️ Cenário C: Tentando reservar equipamento já ocupado ---");
            try {
                new ReservaEquipamento(alunoVipTeste, esteira, LocalDateTime.now()); // Tenta reservar a MESMA esteira
                System.out.println("ERRO: Sistema permitiu dupla reserva!");
            } catch (IllegalArgumentException e) {
                System.out.println("SUCESSO: Sistema avisou ocupado -> " + e.getMessage());
            }

            // 6. TESTE: Histórico
            System.out.println("\n--- 📝 Cenário D: Relatório de Histórico ---");
            HistoricoTreino historico = new HistoricoTreino(alunoVipTeste, "21/11/2025", "Musculação", "Aumentou carga no Supino.");
            historico.ImprimirRelatorio(); // Certifique-se que este método existe na classe HistoricoTreino

        } catch (Exception e) {
            System.out.println("Erro Geral no Módulo 3: " + e.getMessage());
        }

        System.out.println("\n--- Fim da Execução ---");
    }
}