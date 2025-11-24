package com.fitlife;

import com.fitlife.Aluno.Aluno;
import com.fitlife.Aula.Aula;
import com.fitlife.Modalidade.Modalidade;
import com.fitlife.Plano.Plano;
import com.fitlife.Plano.PlanoBasico;
import com.fitlife.Plano.PlanoVip;
import com.fitlife.Professor.Professor;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    // Onde tudo começa e onde tudo termina (se o usuário quiser).
    private static ServicoDeGestaoFitLife servico;
    private static ServicoFinanceiro servicoFinanceiro;
    private static RelatorioFinanceiro relatorioFinanceiro;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Inicializando os módulos (Injeção de Dependência Manual, vulgo "Na unha")
        servico = new ServicoDeGestaoFitLife();
        servicoFinanceiro = new ServicoFinanceiro(servico);
        relatorioFinanceiro = new RelatorioFinanceiro(servico, servicoFinanceiro);
        scanner = new Scanner(System.in);
        inicializarDadosTeste();

        int opcao = -1;
        // O Loop Infinito da Alegria
        while (opcao != 0) {
            System.out.println("\n=== 🏋️ FITLIFE ACADEMIA - SISTEMA SUPREMO ===");
            System.out.println("1. 📝 Área de Cadastros");
            System.out.println("2. 👁️ Área de Listagem");
            System.out.println("3. ✏️  Área de Edição");
            System.out.println("4. 🗑️  Área de Exclusão");
            System.out.println("5. 📄 Gerar Relatório PDF");
            System.out.println("-----------------------------------");
            System.out.println("6. 💰 MÓDULO FINANCEIRO ");
            System.out.println("-----------------------------------");
            System.out.println("0. ❌ Sair e Salvar");
            System.out.print("Escolha seu destino: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1: menuCadastros(); break;
                    case 2: menuExibicao(); break;
                    case 3: menuEdicao(); break;
                    case 4: menuRemocao(); break;
                    case 5: gerarRelatorioPDF(); break;
                    case 6: menuFinanceiro(); break;
                    case 0:
                        System.out.println("Salvando dados... Não desligue o computador.");
                        servico.salvarTodosDados();
                        break;
                    default: System.out.println("Opção inválida. Tente acertar o dedo na tecla.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Eu pedi um NÚMERO, não uma letra!");
                scanner.nextLine();
            }
        }
    }

    // --- SUB-MENUS (Organização é tudo) ---

    private static void menuFinanceiro() {
        int opFin = -1;
        while (opFin != 0) {
            System.out.println("\n--- 💰 MÓDULO FINANCEIRO ---");
            System.out.println("1. Registrar Pagamento");
            System.out.println("2. Consultar Status Aluno");
            System.out.println("3. Relatório de Receita");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");

            try {
                opFin = scanner.nextInt();
                scanner.nextLine();

                switch (opFin) {
                    case 1: registrarPagamento(); break;
                    case 2: consultarStatusPagamento(); break;
                    case 3: relatorioFinanceiro.gerarRelatorio(); break;
                    case 0: System.out.println("Voltando..."); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Número, por favor.");
                scanner.nextLine();
            }
        }
    }

    private static void registrarPagamento() {
        try {
            System.out.print("ID do Aluno (Quem está pagando?): ");
            long idAluno = scanner.nextLong(); scanner.nextLine();

            // Validação de Integridade: Só recebe se o aluno existir
            Optional<Aluno> alunoOpt = servico.buscarAlunoPorId(idAluno);

            if (alunoOpt.isEmpty()) {
                System.out.println("❌ Aluno fantasma! Cadastre ele primeiro.");
                return;
            }

            Aluno aluno = alunoOpt.get();
            String nomePlano = (aluno.getPlano() != null) ? aluno.getPlano().getNome() : "Avulso";
            System.out.println(">> Recebendo de: " + aluno.getNome() + " (" + nomePlano + ")");

            System.out.print("Valor (R$): ");
            double valor = scanner.nextDouble(); scanner.nextLine();

            System.out.print("Data (dd/MM/aaaa): ");
            String data = scanner.nextLine();

            servicoFinanceiro.registrarPagamento(valor, data, idAluno, nomePlano);
            System.out.println("✅ Pagamento registrado!");

        } catch (Exception e) {
            System.out.println("Erro no pagamento: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void consultarStatusPagamento() {
        System.out.print("ID do Aluno: ");
        long id = scanner.nextLong(); scanner.nextLine();
        boolean pagou = servicoFinanceiro.checarStatusPagamento(id);
        if (pagou) System.out.println("✅ Status: OK (Pagou, tá liberado).");
        else System.out.println("⚠️ Status: Pendente (Barrado na catraca).");
    }

    // --- OUTROS MENUS ---

    private static void menuCadastros() {
        System.out.println("\n--- 📝 CADASTROS ---");
        System.out.println("1. Novo Aluno");
        System.out.println("2. Novo Professor");
        System.out.println("3. Nova Modalidade");
        System.out.println("4. Nova Aula");
        System.out.print("Opção: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        switch (op) {
            case 1: cadastrarAluno(); break;
            case 2: cadastrarProfessor(); break;
            case 3: cadastrarModalidade(); break;
            case 4: agendarAula(); break;
            default: System.out.println("Inválido.");
        }
    }

    private static void menuExibicao() {
        System.out.println("\n--- 👁️ EXIBIÇÃO ---");
        System.out.println("1. Listar Alunos");
        System.out.println("2. Listar Professores");
        System.out.println("3. Listar Modalidades");
        System.out.println("4. Listar Aulas");
        System.out.print("Opção: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        switch (op) {
            case 1:
                System.out.println("\nLISTA DE ALUNOS:");
                for(Aluno a : servico.getTodosAlunos()){
                    String plano = (a.getPlano() != null) ? a.getPlano().getNome() : "Sem Plano";
                    String destaque = plano.toUpperCase().contains("VIP") ? " ⭐ VIP" : "";
                    System.out.printf("ID: %d | Nome: %s | Plano: %s%s\n", a.getId(), a.getNome(), plano, destaque);
                }
                break;
            case 2:
                System.out.println("\nLISTA DE PROFESSORES:");
                servico.getTodosProfessores().forEach(p ->
                        System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome()));
                break;
            case 3:
                System.out.println("\nLISTA DE MODALIDADES:");
                servico.getTodasModalidades().forEach(m ->
                        System.out.println("ID: " + m.getId() + " | " + m.getNome()));
                break;
            case 4:
                System.out.println("\nGRADE DE AULAS:");
                servico.getTodasAulas().forEach(a ->
                        System.out.println(a.getDiaSemana() + " - " + a.getHorarioInicio() + ": " + a.getModalidade().getNome()));
                break;
        }
    }

    private static void menuEdicao() {
        System.out.println("\n--- ✏️ EDIÇÃO ---");
        System.out.println("1. Editar Aluno");
        System.out.println("2. Editar Professor");
        System.out.println("3. Editar Modalidade");
        System.out.print("Opção: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        try {
            if (op == 1) {
                System.out.print("ID do Aluno: ");
                long id = scanner.nextLong(); scanner.nextLine();
                System.out.print("Novo Nome (Enter para manter): ");
                String nome = scanner.nextLine();
                System.out.print("Nova Idade (0 para manter): ");
                int idade = scanner.nextInt();
                servico.editarAluno(id, nome, idade);
            } else if (op == 2) {
                System.out.print("ID do Professor: ");
                int id = scanner.nextInt(); scanner.nextLine();
                System.out.print("Novo Nome (Enter para manter): ");
                String nome = scanner.nextLine();
                System.out.print("Nova Especialidade (Enter para manter): ");
                String esp = scanner.nextLine();
                servico.editarProfessor(id, nome, esp);
            } else if (op == 3) {
                System.out.print("ID da Modalidade: ");
                int id = scanner.nextInt(); scanner.nextLine();
                System.out.print("Novo Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Nova Descrição: ");
                String desc = scanner.nextLine();
                servico.editarModalidade(id, nome, desc);
            }
            System.out.println("✅ Editado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao editar: " + e.getMessage());
        }
    }

    private static void menuRemocao() {
        System.out.println("\n--- 🗑️ REMOÇÃO (ZONA DE PERIGO) ---");
        System.out.println("1. Remover Aluno");
        System.out.println("2. Remover Professor");
        System.out.println("3. Remover Modalidade");
        System.out.print("Opção: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o ID para remover (Sem volta): ");
        int id = scanner.nextInt();

        boolean sucesso = false;
        if (op == 1) sucesso = servico.removerAluno(id);
        else if (op == 2) sucesso = servico.removerProfessor(id);
        else if (op == 3) sucesso = servico.removerModalidade(id);

        if (sucesso) System.out.println("✅ Removido! Foi tarde.");
        else System.out.println("❌ ID não encontrado.");
    }

    // --- CADASTROS SIMPLIFICADOS ---

    private static void cadastrarAluno() {
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Idade: "); int idade = scanner.nextInt(); scanner.nextLine();

        String aut = "SIM";
        if (idade < 18) {
            System.out.print("Autorização (SIM/NAO): "); aut = scanner.nextLine();
        }

        System.out.println("Plano: [1] Mensal | [2] VIP");
        int p = scanner.nextInt();
        Plano plano = (p == 2) ? new PlanoVip(99) : new PlanoBasico(10, "Mensal");

        try {
            servico.cadastrarNovoAluno(nome, idade, aut, plano);
            System.out.println("✅ Aluno Salvo!");
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
    }

    private static void cadastrarProfessor() {
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Registro: "); String reg = scanner.nextLine();
        System.out.print("Especialidade: "); String esp = scanner.nextLine();
        servico.cadastrarProfessor(nome, reg, esp);
    }

    private static void cadastrarModalidade() {
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Descricao: "); String desc = scanner.nextLine();
        servico.cadastrarModalidade(nome, desc);
    }

    private static void agendarAula() {
        try {
            System.out.print("ID Modalidade: "); int mId = scanner.nextInt();
            System.out.print("ID Professor: "); int pId = scanner.nextInt(); scanner.nextLine();
            System.out.print("Horario: "); String hora = scanner.nextLine();
            System.out.print("Dia: "); String dia = scanner.nextLine();
            System.out.print("VIP? (true/false): "); boolean vip = scanner.nextBoolean();
            servico.agendarNovaAula(mId, pId, hora, dia, vip);
            System.out.println("✅ Aula agendada!");
        } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
    }

    private static void gerarRelatorioPDF() {
        System.out.println("Gerando relatório... Cruzando os dedos...");
        new GeradorDeRelatorio().gerarRelatorioCompleto(
                servico.getTodosAlunos(),
                servico.getTodosProfessores(),
                servico.getTodasModalidades(),
                "Relatorio_Geral_FitLife.pdf"
        );
    }

    private static void inicializarDadosTeste() {
        if(servico.getTodasModalidades().isEmpty()) {
            servico.cadastrarModalidade("Musculacao", "Geral");
            servico.cadastrarProfessor("Padrao", "001", "Geral");
        }
    }
}