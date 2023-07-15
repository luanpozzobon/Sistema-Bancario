package com.luan.bnkng.dao;

import com.luan.bnkng.users.User;
import java.sql.*;
/**
 *
 * @author luanp
 */
public class UsersDAO {
    private Connection connection;
    
    public UsersDAO(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bnkng", "root", "");
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
    
    public boolean searchCpf(String cpf){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT cpf FROM users WHERE cpf = " + cpf);
            
            if(resultSet.next()) return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean createUser(User user){
        String sql = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, user.getName());
            statement.setString(2, user.getBirthDate().toString());
            statement.setString(3, user.getCpf());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getPassword());
            statement.setString(7, "1");
            
            statement.executeUpdate();
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public Connection getConnection(){
        return connection;
    }
    
    public void closeConeection(){
        try{
            connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
