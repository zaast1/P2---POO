package com.fitlife.Aluno;

import com.fitlife.Plano.Plano;

public class Aluno {
    private Long id;
    private String nome;
    private Integer idade;
    private Plano plano;

    public Aluno(Long id, String nome, Integer idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.plano = plano;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Integer getIdade() {
        return idade;
    }
    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Plano getPlano(){
        return plano;
    }

    public void setPlano(Plano plano){
        this.plano = plano;
    }

    @Override
    public String toString() {
        return "Aluno{" + id + ", " + nome + ", " + idade + "}";
    }
}
