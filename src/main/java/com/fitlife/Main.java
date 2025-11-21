package com.fitlife;

import com.fitlife.Aluno.Aluno;
import com.fitlife.Aula.Aula;
import com.fitlife.Plano.Plano;
import com.fitlife.Plano.PlanoBasico;
import com.fitlife.Plano.PlanoVip;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static ServicoDeGestaoFitLife servico;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Inicializa o serviço e o scanner
        servico = new ServicoDeGestaoFitLife();
        scanner = new Scanner(System.in);

        System.out.println("\n=============================================");
        System.out.println("  FITLIFE-ACADEMIA - INTERFACE DE ADMINISTRAÇÃO");
        System.out.println("=============================================");

        // Garante que haja dados mínimos para o teste VIP (se o CSV estiver vazio).
        inicializarDadosDeExemplo();

        exibirMenuPrincipal();
    }

    private static void inicializarDadosDeExemplo() {
        try {
            // Se as modalidades não foram carregadas do CSV, cria dados de teste
            if (servico.getTodasModalidades().isEmpty()) {
                System.out.println("-> Criando dados iniciais de TESTE (Necessário para a Lógica VIP)...");

                // Cadastro de Dados Mestres
                servico.cadastrarModalidade("Musculação", "Treino de força");
                servico.cadastrarModalidade("Pilates Exclusivo", "Aula VIP");
                servico.cadastrarProfessor("Ana Souza", "R1", "Musculação");

                // Agendamento de Aulas
                servico.agendarNovaAula(2, 1, "08:00", "Segunda", true); // Aula VIP (Modalidade ID 2)
                servico.agendarNovaAula(1, 1, "10:00", "Segunda", false); // Aula Normal (Modalidade ID 1)

                // Simulação de Alunos para Teste VIP (IDs 10=Básico, 99=VIP)
                servico.adicionarAlunoParaTeste(new Aluno(10L, "Carlos (Básico)", 25, new PlanoBasico(10, "Mensal")));
                servico.adicionarAlunoParaTeste(new Aluno(99L, "Julia (VIP)", 30, new PlanoVip(99)));
                servico.salvarTodosDados(); // Salva dados de teste
                System.out.println("-> Dados de teste salvos. Pronto para o Teste VIP.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados de teste: " + e.getMessage());
        }
    }

    private static void exibirMenuPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. ➕ Cadastrar Novo Professor");
            System.out.println("2. 🧘‍♀️ Cadastrar Nova Modalidade");
            System.out.println("3. 📅 Agendar Nova Aula (Teste de Cadastro)");
            System.out.println("4. 🔎 Testar Lógica de Acesso VIP (Demo POO)");
            System.out.println("5. 🧑‍🎓 Cadastrar Novo Aluno");
            System.out.println("0. ❌ Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consome a linha pendente

                switch (opcao) {
                    case 1: cadastrarProfessorInterativo(); break;
                    case 2: cadastrarModalidadeInterativo(); break;
                    case 3: agendarAulaInterativo(); break;
                    case 4: testarFiltroVIP(); break;
                    case 5: cadastrarAlunoInterativo(); break;
                    case 0:
                        System.out.println("Sistema encerrado. Dados salvos.");
                        servico.salvarTodosDados();
                        break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
            }
        }
    }

    // --- MÉTODOS DE CADASTRO INTERATIVO (Requisito: Tratamento de Erros e Regra de Negócio) ---

    private static void cadastrarProfessorInterativo() {
        try {
            System.out.print("Nome do Professor: ");
            String nome = scanner.nextLine();
            System.out.print("Registro (Ex: R200): ");
            String registro = scanner.nextLine();
            System.out.print("Especialização: ");
            String esp = scanner.nextLine();

            servico.cadastrarProfessor(nome, registro, esp);
            System.out.println("✅ Professor cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("ERRO DE VALIDAÇÃO: " + e.getMessage());
        }
    }

    private static void cadastrarModalidadeInterativo() {
        try {
            System.out.print("Nome da Modalidade: ");
            String nome = scanner.nextLine();
            System.out.print("Descrição: ");
            String desc = scanner.nextLine();

            servico.cadastrarModalidade(nome, desc);
            System.out.println("✅ Modalidade cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("ERRO DE VALIDAÇÃO: " + e.getMessage());
        }
    }

    private static void agendarAulaInterativo() {
        try {
            System.out.println("\n--- AGENDAR AULA ---");
            System.out.println("Modalidades disponíveis:");
            servico.getTodasModalidades().forEach(m -> System.out.println(" ID " + m.getId() + ": " + m.getNome()));

            System.out.print("ID da Modalidade: ");
            int modId = scanner.nextInt();

            System.out.println("Professores disponíveis:");
            servico.getTodosProfessores().forEach(p -> System.out.println(" ID " + p.getId() + ": " + p.getNome()));

            System.out.print("ID do Professor: ");
            int profId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Horário (Ex: 18:00): ");
            String horario = scanner.nextLine();

            System.out.print("É aula VIP (true/false)? ");
            boolean isVIP = scanner.nextBoolean();

            servico.agendarNovaAula(modId, profId, horario, "Terça", isVIP);
            System.out.println("✅ Aula agendada com sucesso!");

        } catch (InputMismatchException e) {
            System.err.println("Entrada numérica inválida.");
            scanner.nextLine();
        } catch (Exception e) {
            System.err.println("ERRO: Falha ao agendar: " + e.getMessage());
        }
    }

    private static void cadastrarAlunoInterativo() {
        try {
            System.out.println("\n--- CADASTRO DE ALUNO ---");
            System.out.print("Nome do Aluno: ");
            String nome = scanner.nextLine();
            System.out.print("Idade: ");
            int idade = scanner.nextInt();
            scanner.nextLine();

            String autorizacao = "SIM"; // Padrão

            // --- Lógica Interativa de Validação de Idade ---
            if (idade < 18) {
                System.out.println("⚠️ Aluno inferior a 18 anos deve conter autorização do responsável.");
                System.out.print("O aluno trouxe a autorização? (SIM/NAO): ");
                autorizacao = scanner.nextLine().toUpperCase();

                // --- VALIDAÇÃO IMEDIATA E CANCELAMENTO ---
                if (!"SIM".equals(autorizacao)) {
                    // Lança exceção e o bloco catch final impede a continuidade
                    throw new IllegalArgumentException("Cadastro CANCELADO. Menor de idade sem autorização do responsável.");
                }
                // Se trouxe SIM, a execução continua normalmente.
            }
            // ----------------------------------------

            System.out.println("Escolha o Plano:");
            System.out.println(" [1] Plano Básico (Mensal)");
            System.out.println(" [2] Plano VIP (Exclusivo)");
            System.out.print("Opção: ");
            int planoOpcao = scanner.nextInt();
            scanner.nextLine();

            Plano planoEscolhido;
            if (planoOpcao == 2) {
                planoEscolhido = new PlanoVip(99);
            } else {
                planoEscolhido = new PlanoBasico(10, "Mensal");
            }

            // Chama o método no serviço, que contém a lógica de validação e persistência
            servico.cadastrarNovoAluno(nome, idade, autorizacao, planoEscolhido);
            System.out.println("✅ Aluno cadastrado com sucesso! ID: " + servico.getTodosAlunos().stream().mapToLong(Aluno::getId).max().orElse(0L));

        } catch (InputMismatchException e) {
            System.err.println("ERRO: Entrada numérica inválida para Idade ou opção de plano.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.err.println("ERRO DE VALIDAÇÃO: " + e.getMessage());
            System.out.println("❌ Cadastro CANCELADO. O aluno não foi salvo no CSV.");
        }
    }


    // --- DEMONSTRAÇÃO DO FILTRO VIP (Requisito 2 / Prova de POO) ---

    private static void testarFiltroVIP() {
        System.out.println("\n====================================================");
        System.out.println(" 🔎 TESTE DE ACESSO VIP (Prova de Polimorfismo)");
        System.out.println("====================================================");

        // 1. Busca TODOS os alunos (Incluindo os cadastrados manualmente)
        List<Aluno> todosAlunos = servico.getTodosAlunos();

        if (todosAlunos.isEmpty()) {
            System.out.println("Nenhum aluno encontrado para teste. Cadastre um aluno primeiro (Opção 5).");
            return;
        }

        // 2. Percorre cada aluno e testa o acesso
        for (Aluno aluno : todosAlunos) {
            System.out.println("\n----------------------------------------------------");

            String nomePlano = (aluno.getPlano() != null) ? aluno.getPlano().getNome() : "Sem Plano";

            System.out.println("👤 Aluno: " + aluno.getNome() + " (ID: " + aluno.getId() + ")");
            System.out.println("🎫 Plano Atual: " + nomePlano);

            // Chama a lógica VIP do seu serviço
            List<Aula> aulasLiberadas = servico.listarAulasDisponiveis(aluno.getId());

            System.out.println("📚 Aulas Disponíveis para este aluno: " + aulasLiberadas.size());

            if (aulasLiberadas.isEmpty()) {
                System.out.println("   (Nenhuma aula disponível)");
            } else {
                for (Aula a : aulasLiberadas) {
                    String statusVIP = a.isExclusivaVIP() ? "[👑 AULA VIP]" : "[✅ AULA NORMAL]";
                    // Se o aluno viu a aula VIP, é porque o polimorfismo funcionou
                    System.out.println("   " + statusVIP + " " + a.getModalidade().getNome() +
                            " (" + a.getHorarioInicio() + ")");
                }
            }
        }
        System.out.println("\n====================================================");
        System.out.println("✅ Teste concluído para " + todosAlunos.size() + " alunos.");
    }
}