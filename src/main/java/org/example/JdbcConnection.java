package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
    public static Connection connection(){
        String url = "jdbc:postgresql://localhost:5432/Practice";
        String username = "postgres";
        String password = "0000";
        Connection connect;
        try{
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to postgresql Practice db");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return connect;
    }
}
