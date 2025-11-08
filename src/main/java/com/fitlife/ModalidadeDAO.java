package com.fitlife;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModalidadeDAO {
    private final String NOME_ARQUIVO = "modalidades.csv";

    public ModalidadeDAO() {
        // Garante que o arquivo exista ao inicializar o DAO (Tratamento de Erros)
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de modalidades: " + e.getMessage());
            }
        }
    }

    // Método para ler todas as modalidades do arquivo CSV
    public List<Modalidade> buscarTodos() {
        List<Modalidade> modalidades = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    try {
                        // Usa o construtor auxiliar da classe Modalidade
                        Modalidade m = parseModalidadeFromCSV(linha);
                        modalidades.add(m);
                    } catch (IllegalArgumentException e) {
                        // Tratamento de Erros: Ignora linha inválida
                        System.err.println("Erro ao processar linha CSV em ModalidadeDAO: " + e.getMessage());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de Modalidades não encontrado. Retornando lista vazia.");
            return new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro de leitura/escrita no arquivo: " + e.getMessage());
        }
        return modalidades;
    }

    // Método para salvar uma nova modalidade (Create)
    public void salvar(Modalidade modalidade) {
        List<Modalidade> modalidades = buscarTodos();

        // Lógica de Geração de ID
        int novoId = modalidades.stream()
                .mapToInt(Modalidade::getId)
                .max()
                .orElse(0) + 1;
        modalidade.setId(novoId);

        modalidades.add(modalidade);

        // Salva a lista completa de volta no arquivo
        salvarLista(modalidades);
        System.out.println("Modalidade '" + modalidade.getNome() + "' salva com sucesso! ID: " + novoId);
    }

    // Método para buscar Modalidade por ID (necessário para AulaDAO)
    public Optional<Modalidade> buscarPorId(int id) {
        return buscarTodos().stream()
                .filter(m -> m.getId() == id)
                .findFirst();
    }

    // Método auxiliar (Privado) para reescrever a lista completa no arquivo
    private void salvarLista(List<Modalidade> modalidades) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Modalidade m : modalidades) {
                bw.write(m.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo CSV: " + e.getMessage());
        }
    }

    // Método privado para recriar o objeto Modalidade a partir da linha CSV
    private Modalidade parseModalidadeFromCSV(String csvLine) {
        String[] dados = csvLine.split(";");
        // Verifica se há 3 campos: id;nome;descricao (Ajuste se sua Modalidade tiver mais)
        if (dados.length != 3) {
            throw new IllegalArgumentException("Linha CSV inválida (esperado 3 campos): " + csvLine);
        }

        try {
            int id = Integer.parseInt(dados[0].trim());
            String nome = dados[1].trim();
            String descricao = dados[2].trim();

            // Assume que Modalidade tem um construtor que recebe (id, nome, descricao)
            return new Modalidade(id, nome, descricao);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erro de formato numérico no ID: " + csvLine);
        }
    }
}
