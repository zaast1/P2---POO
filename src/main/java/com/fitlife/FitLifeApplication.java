package com.fitlife;

import com.fitlife.Aluno.Aluno;
import com.fitlife.Professor.Professor;
import com.fitlife.Professor.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/*
Cada classe anotada com @Entity vira uma tabela no banco.

save()      - cria ou atualiza um registro
findAll()   - busca todos os registros
findById()  - busca um registro pelo ID
deleteById()- deleta um registro
*/

@SpringBootApplication
public class FitLifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitLifeApplication.class, args);
    }

    // Teste de Aluno
    @Bean
    CommandLineRunner runAluno(AlunoRepository alunoRepo) {
        return args -> {
            alunoRepo.save(new Aluno(null, "João", 25));
            alunoRepo.save(new Aluno(null, "Maria", 30));

            System.out.println("Lista de alunos:");
            alunoRepo.findAll().forEach(System.out::println);
        };
    }

    // Teste de Professor
    @Bean
    CommandLineRunner runProfessor(ProfessorRepository profRepo) {
        return args -> {
            profRepo.save(new Professor(null, "Carla Souza", "REG123", "Musculação"));
            profRepo.save(new Professor(null, "João Silva", "REG456", "Crossfit"));

            System.out.println("Lista de professores:");
            profRepo.findAll().forEach(System.out::println);
        };
    }
}
