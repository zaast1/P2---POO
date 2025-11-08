package com.fitlife;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorDAO {
    private final String NOME_ARQUIVO = "professores.csv";

    public ProfessorDAO() {
        // Garantir que o arquivo exista (Tratamento de Erros)
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                // O sistema não deve quebrar
                System.err.println("Erro ao criar arquivo de persistência: " + e.getMessage());
            }
        }
    }

    // Método para ler todos os professores do arquivo
    public List<Professor> buscarTodos() {
        List<Professor> professores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) { // Ignorar linhas vazias
                    try {
                        // Usa o construtor da classe Professor para recriar o objeto
                        Professor p = new Professor(linha);
                        professores.add(p);
                    } catch (IllegalArgumentException e) {
                        // Tratamento de Erros: Ignora linha inválida, mas não quebra o sistema
                        System.err.println("Erro ao processar linha CSV: " + e.getMessage());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Tratamento de Erros: Se arquivo ausente, retorna lista vazia
            System.err.println("Arquivo de Professores não encontrado. Retornando lista vazia.");
            return new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro de leitura/escrita no arquivo: " + e.getMessage());
        }
        return professores;
    }

    // Método para salvar um novo professor (Create)
    public void salvar(Professor professor) {
        List<Professor> professores = buscarTodos();

        // Lógica de Geração de ID (simplesmente busca o maior ID e adiciona 1)
        int novoId = professores.stream()
                .mapToInt(Professor::getId)
                .max()
                .orElse(0) + 1;
        professor.setId(novoId);

        professores.add(professor); // Adiciona o novo

        // Salva a lista completa de volta no arquivo
        salvarLista(professores);
    }

    // Método auxiliar (Privado) para reescrever a lista completa no arquivo
    private void salvarLista(List<Professor> professores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Professor p : professores) {
                bw.write(p.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo CSV: " + e.getMessage());
        }
    }

    // Método para buscar professor por ID (necessário para a classe Aula)
    public Optional<Professor> buscarPorId(int id) {
        return buscarTodos().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }
}
