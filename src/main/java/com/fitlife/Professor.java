package com.fitlife;
import jakarta.persistence.*;

@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String registro;


    private String especializacao;

    public Professor(Long id, String nome, String registro, String especializacao) {
        this.id = id;
        this.nome = nome;
        this.registro = registro;
        this.especializacao = especializacao;
    }


    public Professor(String csvLine) {
        String[] dados = csvLine.split(";");
        if (dados.length == 4) {
            this.id = Long.parseLong(dados[0].trim());
            this.nome = dados[1].trim();
            this.registro = dados[2].trim();
            this.especializacao = dados[3].trim();
        } else {
            throw new IllegalArgumentException("Linha CSV inválida para Professor: " + csvLine);
        }
    }

    public Professor() {}


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

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }

    public String toCSV() {
        return id + ";" + nome + ";" + registro + ";" + especializacao;
    }
}
