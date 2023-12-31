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

            conn = DriverManager.getConnection("DB_URL", "DB_USERNAME", "DB_PASSWORD");
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
