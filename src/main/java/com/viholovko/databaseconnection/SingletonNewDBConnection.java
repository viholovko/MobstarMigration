package com.viholovko.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonNewDBConnection {

    private static SingletonNewDBConnection instance;
    private Connection connection;
    private String url = "jdbc:postgresql://localhost:5432/mobstar_db_development";
    private String username = "postgres";
    private String password = "postgres";

    private SingletonNewDBConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static SingletonNewDBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new SingletonNewDBConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new SingletonNewDBConnection();
        }

        return instance;
    }
}
