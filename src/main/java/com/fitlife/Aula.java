package com.fitlife;

public class Aula{
    private int id;

    private Modalidade modalidade;
    private Professor professor;

    private String horarioInicio;
    private String diaSemana;
    private boolean isExclusivaVIP;


    public Aula(int id, Modalidade modalidade, Professor professor, String horarioInicio, String diaSemana, boolean isExclusivaVIP) {
        this.id = id;
        this.modalidade = modalidade;
        this.professor = professor;
        this.horarioInicio = horarioInicio;
        this.diaSemana = diaSemana;
        this.isExclusivaVIP = isExclusivaVIP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public boolean isExclusivaVIP() {
        return isExclusivaVIP;
    }

    public void setExclusivaVIP(boolean exclusivaVIP) {
        isExclusivaVIP = exclusivaVIP;
    }
}
