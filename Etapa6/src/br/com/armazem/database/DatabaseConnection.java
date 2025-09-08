package br.com.armazem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/BD_Armazem_Agricola";
    private static final String USER = "root";
    private static final String PASSWORD = "B4nc0//D4d0$";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}