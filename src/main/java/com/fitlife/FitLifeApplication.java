package com.fitlife;

import com.fitlife.Aluno.Aluno;
import com.fitlife.Professor.Professor;

import java.sql.*;
import java.util.*;

/*
DISCLAIMER:
Se tiver aparecendo erro de tabela como "não existe a tabela aluno", certifique-se de
ter a tabela criada em Database. Se não der certo alguma coisa, quer dizer que você não criou
alguma coisa dentro do seu banco de dados LOCAL (sim, é local, logo você tem que fazer).
                                                 ^--- Claro, eu posso ver uma forma de carregar
                                                      as coisas daqui para ficar global na hora
                                                      de executar. Já fiz isso para o insert de
                                                      alunos.

A cada função nova que alguém for implementar usando SQL, por favor, faça um boxe como feito
dentro do try na classe FitLifeApplication, isso vai ajudar a todo mundo que passar por aqui
entender o que você fez. sério.

Keywords para pesquisas rápidas:
REGISTRAR UM ALUNO
MOSTRAR LISTA




*/


public class FitLifeApplication {
    public static void main(String[] args) {
        List<Aluno> alunos = new ArrayList<>();
        // CONEXÃO COM O MYSQL (EM LOCALHOST)
        try{
            Connection conexao = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/fitlife",
                    "root",
                    "Um4.S3nh4");
            // Conexão Criada, tudo dentro do try ocorre DENTRO do banco de dados.
            Statement statement = conexao.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM aluno");
            // ^---- Selecionando tudo da tabela aluno
            while (resultSet.next()) { // Adicionando alunos do banco de dados como objetos na lista.
                Aluno a = new Aluno(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getInt("idade")
                );
                alunos.add(a);
            }
            PreparedStatement ps = conexao.prepareStatement(
                    "INSERT INTO aluno (nome, idade) VALUES (?, ?)"
            );

            // ========================
            // REGISTRAR UM ALUNO
            // ========================

            ps = conexao.prepareStatement(
                    "INSERT INTO aluno (nome, idade) VALUES (?, ?)"
            );
                                                        // No index 0, fica id (o DB já gera o id).
            ps.setString(1, "Carlos"); // No index 1, fica "nome"
            ps.setInt(2, 28); // No index 2, fica "idade"

            ps.executeUpdate(); // Comando para atualizar o banco de dados, essencial para finalizar
            //                     a adição de novas informações na tabela.

            // ========================
            // MOSTRAR LISTA
            // ========================
            for (Aluno a : alunos) {
                System.out.println(a);
            }



            conexao.close(); // Fecha conexão.

        } catch(SQLException e){ // Tratamento de erro
            e.printStackTrace();
        }


    }
}