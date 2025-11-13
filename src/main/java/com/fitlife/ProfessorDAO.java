package com.fitlife;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorDAO {
    private final String NOME_ARQUIVO = "professores.csv";

    public ProfessorDAO() {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de persistência: " + e.getMessage());
            }
        }
    }

    public List<Professor> buscarTodos() {
        List<Professor> professores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    try {
                        Professor p = new Professor(linha);
                        professores.add(p);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erro ao processar linha CSV: " + e.getMessage());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de Professores não encontrado. Retornando lista vazia.");
            return new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro de leitura/escrita no arquivo: " + e.getMessage());
        }
        return professores;
    }

    public void salvar(Professor professor) {
        List<Professor> professores = buscarTodos();

        long novoId = professores.stream()
                .mapToLong(Professor::getId)
                .max()
                .orElse(0L) + 1L;

        professor.setId(novoId);
        professores.add(professor);
        salvarLista(professores);
    }

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

    public Optional<Professor> buscarPorId(long id) {
        return buscarTodos().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }
}
