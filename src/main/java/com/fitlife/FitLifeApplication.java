package com.fitlife;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FitLifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitLifeApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AlunoRepository repo) {
        return args -> {
            // Salva alguns alunos de teste
            repo.save(new Aluno(null, "João", 25));
            repo.save(new Aluno(null, "Maria", 30));

            // Lista todos os alunos
            System.out.println("Lista de alunos:");
            repo.findAll().forEach(System.out::println);
        };
    }
}
