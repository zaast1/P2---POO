package com.fitlife;

import com.fitlife.Aluno.Aluno;
import com.fitlife.Professor.Professor;

import java.sql.*;


public class FitLifeApplication {
    public static void main(String[] args) {
        // CONEXÃO COM O MYSQL (EM LOCALHOST)
        try{
            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitlife","root","1234");

            Statement statement = conexao.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

            while(resultSet.next()){
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("password"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}