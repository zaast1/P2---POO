package com.fitlife;

import com.fitlife.Aluno.Aluno;
import com.fitlife.Aula.Aula;
import com.fitlife.Modalidade.Modalidade;
import com.fitlife.Plano.Plano;
import com.fitlife.Professor.Professor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// O Cérebro do Sistema. Se essa classe cair, a academia fecha. Aconteça o que acontecer não faça merda aqui (GABRIEL).
public class ServicoDeGestaoFitLife {

    // Listas em memória (Nosso banco de dados é a memória RAM, torça para não faltar luz)
    private List<Professor> professores = new ArrayList<>();
    private List<Modalidade> modalidades = new ArrayList<>();
    private List<Aula> aulas = new ArrayList<>();
    private List<Aluno> alunos = new ArrayList<>();
    private List<?> frequencias = new ArrayList<>();

    // Arquivos CSV onde a mágica persiste
    private static final String PROFESSOR_ARQUIVO = "professores.csv";
    private static final String MODALIDADE_ARQUIVO = "modalidades.csv";
    private static final String AULA_ARQUIVO = "aulas.csv";
    private static final String ALUNO_ARQUIVO = "alunos.csv";
    private static final String FREQUENCIA_ARQUIVO = "frequencias.csv";

    public ServicoDeGestaoFitLife() {
        carregarTodosDados(); //
    }

    // --- MÉTODOS DE BUSCA  ---

    public void adicionarAlunoParaTeste(Aluno aluno) {
        this.alunos.add(aluno);
    }

    public Optional<Professor> buscarProfessorPorId(int id) {
        return professores.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Optional<Modalidade> buscarModalidadePorId(int id) {
        return modalidades.stream().filter(m -> m.getId() == id).findFirst();
    }

    public Optional<Aluno> buscarAlunoPorId(long id) {
        return alunos.stream().filter(a -> a.getId() == id).findFirst();
    }

    // --- PERSISTÊNCIA ---

    private void carregarTodosDados() {
        carregarDadosSimples(MODALIDADE_ARQUIVO, modalidades, Modalidade.class);
        carregarDadosSimples(PROFESSOR_ARQUIVO, professores, Professor.class);
        carregarDadosSimples(ALUNO_ARQUIVO, alunos, Aluno.class);
        carregarAulas(AULA_ARQUIVO);
    }

    // Método genérico para ler qualquer CSV simples.
    private <T> void carregarDadosSimples(String nomeArquivo, List<T> lista, Class<T> classe) {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) { return; }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    try {
                        T objeto = classe.getConstructor(String.class).newInstance(linha);
                        lista.add(objeto);
                    } catch (Exception e) {
                        System.err.println("Linha corrompida no arquivo " + nomeArquivo + ". Ignorando...");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        }
    }

    // Carrega aulas
    private void carregarAulas(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    try {
                        String[] dados = linha.split(";");
                        if (dados.length != 6) throw new IllegalArgumentException("CSV de Aula inválido.");

                        int idAula = Integer.parseInt(dados[0].trim());
                        int idModalidade = Integer.parseInt(dados[1].trim());
                        int idProfessor = Integer.parseInt(dados[2].trim());
                        String horario = dados[3].trim();
                        String dia = dados[4].trim();
                        boolean isVIP = Boolean.parseBoolean(dados[5].trim());

                        // Reconstrói os objetos (Lookups)
                        Optional<Modalidade> mOpt = buscarModalidadePorId(idModalidade);
                        Optional<Professor> pOpt = buscarProfessorPorId(idProfessor);

                        if (mOpt.isPresent() && pOpt.isPresent()) {
                            aulas.add(new Aula(idAula, mOpt.get(), pOpt.get(), horario, dia, isVIP));
                        }
                    } catch (Exception e) {
                        System.err.println("Erro ao ler aula: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro de leitura: " + e.getMessage());
        }
    }

    // Salva tudo de volta no disco
    public void salvarTodosDados() {
        salvarEntidades(MODALIDADE_ARQUIVO, modalidades);
        salvarEntidades(PROFESSOR_ARQUIVO, professores);
        salvarEntidades(AULA_ARQUIVO, aulas);
        salvarEntidades(ALUNO_ARQUIVO, alunos);
        salvarEntidades(FREQUENCIA_ARQUIVO, frequencias);
    }

    private void salvarEntidades(String nomeArquivo, List<?> lista) {
        if (lista.isEmpty()) { new File(nomeArquivo).delete(); return; }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Object item : lista) {
                String csvLine = "";
                // Polimorfismo manual feio, mas funciona
                if (item instanceof Professor) csvLine = ((Professor) item).toCSV();
                else if (item instanceof Modalidade) csvLine = ((Modalidade) item).toCSV();
                else if (item instanceof Aula) csvLine = ((Aula) item).toCSV();
                else if (item instanceof Aluno) csvLine = ((Aluno) item).toCSV();
                else continue;

                bw.write(csvLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    // --- REGRAS DE NEGÓCIO (CRUD) ---

    public Professor cadastrarProfessor(String nome, String registro, String especializacao) throws IllegalArgumentException {
        int novoId = professores.stream().mapToInt(Professor::getId).max().orElse(0) + 1;
        Professor novoProfessor = new Professor(novoId, nome, registro, especializacao);
        professores.add(novoProfessor);
        salvarTodosDados();
        return novoProfessor;
    }

    public Modalidade cadastrarModalidade(String nome, String descricao) throws IllegalArgumentException {
        int novoId = modalidades.stream().mapToInt(Modalidade::getId).max().orElse(0) + 1;
        Modalidade novaModalidade = new Modalidade(novoId, nome, descricao);
        modalidades.add(novaModalidade);
        salvarTodosDados();
        return novaModalidade;
    }

    public Aula agendarNovaAula(int modalidadeId, int professorId, String horario, String dia, boolean isVIP) throws Exception {
        Modalidade modalidade = buscarModalidadePorId(modalidadeId).orElseThrow(() -> new Exception("Modalidade sumiu!"));
        Professor professor = buscarProfessorPorId(professorId).orElseThrow(() -> new Exception("Professor sumiu!"));

        int novoId = aulas.stream().mapToInt(Aula::getId).max().orElse(0) + 1;
        Aula novaAula = new Aula(novoId, modalidade, professor, horario, dia, isVIP);
        aulas.add(novaAula);
        salvarTodosDados();
        return novaAula;
    }

    // Cadastro de Aluno com a Regra de Negócio de Menor de Idade
    public Aluno cadastrarNovoAluno(String nome, int idade, String autorizacaoStatus, Plano planoInicial) throws IllegalArgumentException {
        if (idade < 18 && !"SIM".equalsIgnoreCase(autorizacaoStatus)) {
            throw new IllegalArgumentException("Sem autorização dos pais, sem treino. Regras são regras.");
        }

        long novoAlunoId = alunos.stream().mapToLong(Aluno::getId).max().orElse(0L) + 1;
        Aluno novoAluno = new Aluno(novoAlunoId, nome, idade, planoInicial);
        this.alunos.add(novoAluno);
        salvarTodosDados();
        return novoAluno;
    }

    // --- FILTRO VIP ---
    public List<Aula> listarAulasDisponiveis(long alunoId) {
        Aluno aluno = buscarAlunoPorId(alunoId).orElse(null);
        if (aluno == null) return aulas.stream().filter(aula -> !aula.isExclusivaVIP()).collect(Collectors.toList());

        return aulas.stream()
                .filter(aula -> {
                    if (!aula.isExclusivaVIP()) return true; // Aula comum, entra todo mundo
                    // Se for VIP, pergunta pro Plano se pode entrar (Polimorfismo!)
                    return aluno.getPlano() != null && aluno.getPlano().temAcessoExclusivoAulas();
                })
                .collect(Collectors.toList());
    }

    // --- EDIÇÃO E REMOÇÃO ---

    public boolean removerAluno(long id) {
        boolean removeu = alunos.removeIf(a -> a.getId() == id);
        if (removeu) salvarTodosDados();
        return removeu;
    }

    public void editarAluno(long id, String novoNome, int novaIdade) throws Exception {
        Aluno aluno = buscarAlunoPorId(id).orElseThrow(() -> new Exception("Aluno fantasma? Não achei."));
        if (novoNome != null && !novoNome.trim().isEmpty()) aluno.setNome(novoNome);
        if (novaIdade > 0) aluno.setIdade(novaIdade);
        salvarTodosDados();
    }

    public boolean removerProfessor(int id) {
        boolean removeu = professores.removeIf(p -> p.getId() == id);
        if (removeu) salvarTodosDados();
        return removeu;
    }

    public void editarProfessor(int id, String novoNome, String novaEsp) throws Exception {
        Professor p = buscarProfessorPorId(id).orElseThrow(() -> new Exception("Professor não encontrado"));
        if (!novoNome.trim().isEmpty()) p.setNome(novoNome);
        if (!novaEsp.trim().isEmpty()) p.setEspecialidade(novaEsp);
        salvarTodosDados();
    }

    public boolean removerModalidade(int id) {
        boolean removeu = modalidades.removeIf(m -> m.getId() == id);
        if (removeu) salvarTodosDados();
        return removeu;
    }

    public void editarModalidade(int id, String novoNome, String novaDesc) throws Exception {
        Modalidade m = buscarModalidadePorId(id).orElseThrow(() -> new Exception("Modalidade não encontrada"));
        if (!novoNome.trim().isEmpty()) m.setNome(novoNome);
        if (!novaDesc.trim().isEmpty()) m.setDescricao(novaDesc);
        salvarTodosDados();
    }

    // Getters para a galera (Cópia defensiva para ninguém estragar a lista original)
    public List<Modalidade> getTodasModalidades() { return new ArrayList<>(modalidades); }
    public List<Professor> getTodosProfessores() { return new ArrayList<>(professores); }
    public List<Aula> getTodasAulas() { return new ArrayList<>(aulas); }
    public List<Aluno> getTodosAlunos() { return new ArrayList<>(alunos); }
}