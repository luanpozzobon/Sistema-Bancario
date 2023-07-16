package com.luan.bnkng.dao;

import com.luan.bnkng.users.User;
import java.sql.*;
/**
 *
 * @author luanp
 */
public class UsersDAO {
    private Connection conn;
    
    public UsersDAO(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bnkng", "root", "");
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
    
    public boolean searchCpf(String cpf){
        try{
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT cpf FROM users WHERE cpf = " + cpf);
            
            if(resultSet.next()) return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean searchAccount(String accountNumber){
        try{
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT accountNumber FROM users WHERE accountNumber = " + accountNumber);
            
            if(resultSet.next()) return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
                
        return false;
    }
    
    public boolean createUser(User user){
        String sql = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement st = conn.prepareStatement(sql)){
            st.setString(1, user.getName());
            st.setString(2, user.getBirthDate().toString());
            st.setString(3, user.getCpf());
            st.setString(4, user.getEmail());
            st.setString(5, user.getPhone());
            st.setString(6, user.getPassword());
            st.setString(7, user.getAccountNumber());
            
            st.executeUpdate();
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public Connection getConn(){
        return conn;
    }
    
    public void closeConeection(){
        try{
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
