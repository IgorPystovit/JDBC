package com.epam.igorpystovit.connectionmanager;

import java.sql.*;

public class ConnectionManager {
    private static Connection connection;
    private static final String user = "wage";
    private static final String pass = "root";
    private static final String url = "jdbc:mysql://localhost:3306/Airport?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

    public static Connection getConnection(){
        if (connection == null) {
            synchronized (ConnectionManager.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection
                                (url, user, pass);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    public static void main(String[] args) {
        try{
            System.out.println(getConnection().getCatalog());
        } catch (SQLException e){
            System.out.println(e);
        }
    }
}
