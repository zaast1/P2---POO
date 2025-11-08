package com.fitlife;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AulaDAO {
    private final String NOME_ARQUIVO = "aulas.csv";

    // Dependências de outros DAOs para Composição
    private ProfessorDAO professorDAO;
    private ModalidadeDAO modalidadeDAO;

    // O Construtor recebe as dependências (Injeção de Dependência simples)
    public AulaDAO(ProfessorDAO professorDAO, ModalidadeDAO modalidadeDAO) {
        this.professorDAO = professorDAO;
        this.modalidadeDAO = modalidadeDAO;

        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de aulas: " + e.getMessage());
            }
        }
    }

    // Método para ler todas as aulas do arquivo CSV
    public List<Aula> buscarTodos() {
        List<Aula> aulas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    try {
                        Aula aula = parseAulaFromCSV(linha);
                        if (aula != null) {
                            aulas.add(aula);
                        }
                    } catch (Exception e) {
                        System.err.println("Erro ao processar linha CSV em AulaDAO: " + e.getMessage());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de Aulas não encontrado. Retornando lista vazia.");
            return new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro de leitura/escrita no arquivo de Aulas: " + e.getMessage());
        }
        return aulas;
    }

    // Método privado para recriar o objeto Aula a partir da linha CSV
    private Aula parseAulaFromCSV(String csvLine) {
        String[] dados = csvLine.split(";");
        if (dados.length != 6) {
            throw new IllegalArgumentException("Linha CSV inválida (dados incompletos): " + csvLine);
        }

        try {
            int idAula = Integer.parseInt(dados[0].trim());
            int idModalidade = Integer.parseInt(dados[1].trim());
            int idProfessor = Integer.parseInt(dados[2].trim());
            String horario = dados[3].trim();
            String dia = dados[4].trim();
            boolean isVIP = Boolean.parseBoolean(dados[5].trim());

            // *Processo de Reconstrução da Composição:*
            // 1. Busca os objetos completos pelos IDs usando os DAOs dependentes
            Optional<Modalidade> modalidade = modalidadeDAO.buscarPorId(idModalidade);
            Optional<Professor> professor = professorDAO.buscarPorId(idProfessor);

            if (modalidade.isPresent() && professor.isPresent()) {
                // 2. Constrói a Aula com os objetos compostos
                return new Aula(idAula, modalidade.get(), professor.get(), horario, dia, isVIP);
            } else {
                System.err.println("Erro: Não foi possível encontrar Modalidade ou Professor para ID da Aula " + idAula);
                return null;
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erro de formato numérico na linha CSV: " + csvLine);
        }
    }

    // Método para salvar uma nova aula (Create)
    public void salvar(Aula aula) {
        List<Aula> aulas = buscarTodos();

        // Lógica de Geração de ID
        int novoId = aulas.stream()
                .mapToInt(Aula::getId)
                .max()
                .orElse(0) + 1;
        aula.setId(novoId);

        aulas.add(aula);

        // Salva a lista completa de volta no arquivo
        salvarLista(aulas);
        System.out.println("Aula de " + aula.getModalidade().getNome() + " salva com sucesso! ID: " + novoId);
    }

    // Método auxiliar (Privado) para reescrever a lista completa no arquivo
    private void salvarLista(List<Aula> aulas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Aula a : aulas) {
                bw.write(a.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo CSV de Aulas: " + e.getMessage());
        }
    }
}