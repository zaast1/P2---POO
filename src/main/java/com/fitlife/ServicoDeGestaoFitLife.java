package com.fitlife;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// --- CLASSE PRINCIPAL DO MEMBRO 1 (SERVIÇO DE GESTÃO) ---

public class ServicoDeGestaoFitLife {

    // Listas em memória
    private List<Professor> professores = new ArrayList<>();
    private List<Modalidade> modalidades = new ArrayList<>();
    private List<Aula> aulas = new ArrayList<>();
    private List<Aluno> alunos = new ArrayList<>();

    // Nomes dos arquivos CSV
    private static final String PROFESSOR_ARQUIVO = "professores.csv";
    private static final String MODALIDADE_ARQUIVO = "modalidades.csv";
    private static final String AULA_ARQUIVO = "aulas.csv";
    private static final String ALUNO_ARQUIVO = "alunos.csv";


    public ServicoDeGestaoFitLife() {
        carregarTodosDados();
    }

    // --- MÉTODOS DE BUSCA AUXILIARES (LOOKUP) ---

    public Optional<Professor> buscarProfessorPorId(int id) {
        return professores.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Optional<Modalidade> buscarModalidadePorId(int id) {
        return modalidades.stream().filter(m -> m.getId() == id).findFirst();
    }

    public Optional<Aluno> buscarAlunoPorId(long id) {
        return alunos.stream().filter(a -> a.getId() == id).findFirst();
    }

    // --- MÉTODOS DE PERSISTÊNCIA (I/O CSV) ---

    private void carregarTodosDados() {
        carregarDadosSimples(MODALIDADE_ARQUIVO, modalidades, Modalidade.class);
        carregarDadosSimples(PROFESSOR_ARQUIVO, professores, Professor.class);
        carregarDadosSimples(ALUNO_ARQUIVO, alunos, Aluno.class); // Carrega Alunos
        carregarAulas(AULA_ARQUIVO); // Depende dos carregamentos acima
    }

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
                        System.err.println("Erro ao processar linha em " + nomeArquivo + ": " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro de leitura no arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    private void carregarAulas(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    try {
                        String[] dados = linha.split(";");
                        if (dados.length != 6) throw new IllegalArgumentException("Campos incorretos na linha CSV de Aula.");

                        int idAula = Integer.parseInt(dados[0].trim());
                        int idModalidade = Integer.parseInt(dados[1].trim());
                        int idProfessor = Integer.parseInt(dados[2].trim());
                        String horario = dados[3].trim();
                        String dia = dados[4].trim();
                        boolean isVIP = Boolean.parseBoolean(dados[5].trim());

                        Optional<Modalidade> mOpt = buscarModalidadePorId(idModalidade);
                        Optional<Professor> pOpt = buscarProfessorPorId(idProfessor);

                        if (mOpt.isPresent() && pOpt.isPresent()) {
                            aulas.add(new Aula(idAula, mOpt.get(), pOpt.get(), horario, dia, isVIP));
                        } else {
                            System.err.println("Erro: Dependência não encontrada para Aula ID: " + idAula);
                        }
                    } catch (Exception e) {
                        System.err.println("Erro ao processar linha de Aulas: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro de leitura no arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    public void salvarTodosDados() {
        salvarEntidades(MODALIDADE_ARQUIVO, modalidades);
        salvarEntidades(PROFESSOR_ARQUIVO, professores);
        salvarEntidades(AULA_ARQUIVO, aulas);
        salvarEntidades(ALUNO_ARQUIVO, alunos);
    }

    private void salvarEntidades(String nomeArquivo, List<?> lista) {
        if (lista.isEmpty()) { new File(nomeArquivo).delete(); return; }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Object item : lista) {
                String csvLine = "";

                if (item instanceof Professor) csvLine = ((Professor) item).toCSV();
                else if (item instanceof Modalidade) csvLine = ((Modalidade) item).toCSV();
                else if (item instanceof Aula) csvLine = ((Aula) item).toCSV();
                else if (item instanceof Aluno) csvLine = ((Aluno) item).toCSV();
                else continue;

                bw.write(csvLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    // --- MÉTODOS DE LÓGICA DE NEGÓCIO (CADASTRO E AGENDAMENTO) ---

    public Professor cadastrarProfessor(String nome, String registro, String especializacao) throws IllegalArgumentException {
        if (nome == null || nome.trim().isEmpty() || registro == null || registro.trim().isEmpty()) {
            // Tratamento de Erros: Validação de entrada
            throw new IllegalArgumentException("Nome e registro do professor são obrigatórios.");
        }

        int novoId = professores.stream().mapToInt(Professor::getId).max().orElse(0) + 1;
        Professor novoProfessor = new Professor(novoId, nome, registro, especializacao);
        professores.add(novoProfessor);
        salvarTodosDados();
        return novoProfessor;
    }

    public Modalidade cadastrarModalidade(String nome, String descricao) throws IllegalArgumentException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da modalidade é obrigatório.");
        }

        int novoId = modalidades.stream().mapToInt(Modalidade::getId).max().orElse(0) + 1;
        Modalidade novaModalidade = new Modalidade(novoId, nome, descricao);
        modalidades.add(novaModalidade);
        salvarTodosDados();
        return novaModalidade;
    }

    public Aula agendarNovaAula(int modalidadeId, int professorId, String horario, String dia, boolean isVIP) throws Exception {
        // Tratamento de Erros: Verifica se as dependências existem
        Modalidade modalidade = buscarModalidadePorId(modalidadeId)
                .orElseThrow(() -> new Exception("Modalidade não encontrada."));

        Professor professor = buscarProfessorPorId(professorId)
                .orElseThrow(() -> new Exception("Professor não encontrado."));

        if (horario == null || dia == null) {
            throw new IllegalArgumentException("Horário e dia são obrigatórios.");
        }

        int novoId = aulas.stream().mapToInt(Aula::getId).max().orElse(0) + 1;
        Aula novaAula = new Aula(novoId, modalidade, professor, horario, dia, isVIP);
        aulas.add(novaAula);
        salvarTodosDados();
        return novaAula;
    }

    // --- LÓGICA DE ACESSO VIP (POLIMORFISMO) ---

    public List<Aula> listarAulasDisponiveis(long alunoId) {
        Aluno aluno = buscarAlunoPorId(alunoId).orElse(null);

        if (aluno == null) {
            System.err.println("Aluno não encontrado. Apenas aulas básicas serão exibidas.");
            return aulas.stream().filter(aula -> !aula.isExclusivaVIP()).collect(Collectors.toList());
        }

        // Filtro com Lógica VIP (Polimorfismo e Open/Closed Principle)
        return aulas.stream()
                .filter(aula -> {
                    if (!aula.isExclusivaVIP()) {
                        return true;
                    }

                    // Usa o método polimórfico do Plano
                    if (aluno.getPlano() != null && aluno.getPlano().temAcessoExclusivoAulas()) {
                        return true;
                    }

                    return false;
                })
                .collect(Collectors.toList());
    }

    // --- MÉTODOS PÚBLICOS DE ACESSO (Para Interface e Outros Membros) ---

    public List<Modalidade> getTodasModalidades() {
        return new ArrayList<>(modalidades);
    }

    public List<Professor> getTodosProfessores() {
        return new ArrayList<>(professores);
    }

    public List<Aula> getTodasAulas() {
        return new ArrayList<>(aulas);
    }
}