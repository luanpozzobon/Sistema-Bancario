package com.luan.bnkng.dao;

import com.luan.bnkng.models.User;
import java.sql.*;
/**
 *
 * @author luanp
 */
public class UsersDAO extends DatabaseDAO{
    
    public UsersDAO(){
        super();
    }
    
    public boolean searchCpf(String cpf){
        try{
            Statement st = getConnection().createStatement();
            ResultSet rSet = st.executeQuery("SELECT cpf FROM users WHERE cpf = " + cpf);
            
            if(rSet.next()) return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public ResultSet searchUser(String cpf, String password){
        try{
            PreparedStatement st = getConnection().prepareStatement("SELECT * FROM users WHERE cpf = ? AND password = ?");
            
            st.setString(1, cpf);
            st.setString(2, password);
            
            ResultSet rSet = st.executeQuery();
            if(rSet.next()) return rSet;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean createUser(User user){
        try(PreparedStatement st = getConnection().prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?)")){
            st.setString(1, user.getName());
            st.setString(2, user.getBirthDate());
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
    
    public boolean modifyUser(User user){
        try(PreparedStatement st = getConnection().prepareStatement("UPDATE users SET email = ?, phone = ?, password = ? WHERE cpf = ?")){
            st.setString(1, user.getEmail());
            st.setString(2, user.getPhone());
            st.setString(3, user.getPassword());
            st.setString(4, user.getCpf());
            
            st.executeUpdate();
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
