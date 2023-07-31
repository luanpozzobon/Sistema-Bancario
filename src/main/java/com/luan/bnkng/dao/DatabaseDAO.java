package com.luan.bnkng.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author luanp
 */
public abstract class DatabaseDAO {
    private Connection conn;
    
    protected DatabaseDAO(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");
            
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
    
    protected Connection getConnection(){
        return conn;
    }
    
    protected void closeConnection(){
        try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
