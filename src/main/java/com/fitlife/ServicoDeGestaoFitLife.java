package com.fitlife;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        System.out.println("Iniciando serviço: Tentando carregar dados dos arquivos CSV...");
        carregarTodosDados();
    }

    // --- MÉTODOS DE BUSCA AUXILIARES (LOOKUP) ---

    // Busca Professor
    public Optional<Professor> buscarProfessorPorId(int id) {
        return professores.stream().filter(p -> p.getId() == id).findFirst();
    }

    // Busca Modalidade
    public Optional<Modalidade> buscarModalidadePorId(int id) {
        return modalidades.stream().filter(m -> m.getId() == id).findFirst();
    }
    // Busca Aluno
    public Optional<Aluno> buscarAlunoPorId(long id) {
        return alunos.stream().filter(a -> a.getId() == id).findFirst();
    }

    // --- MÉTODOS AUXILIARES
    private void carregarTodosDados() { /* Implementação no Commit 2 */ }
    private void salvarTodosDados() { /* Implementação no Commit 2 */ }

}