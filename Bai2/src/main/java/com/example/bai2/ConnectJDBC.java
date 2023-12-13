package com.example.bai2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectJDBC {
    private String hostName = "localhost:3306";
    private String dbName = "mydb";
    private String userName ="root";
    private String password = "password";
    private String connectUrl = "jdbc:mysql://"+hostName+"/"+dbName;

    public Connection connection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectUrl,userName,password);
            System.out.println("Complete connect");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
