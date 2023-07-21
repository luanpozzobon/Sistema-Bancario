package com.luan.bnkng.dao;

import com.luan.bnkng.models.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author luanp
 */
public class AccountsDAO extends DatabaseDAO {
    public AccountsDAO(){
        super();
    }
    
    public ResultSet searchAccount(String accountNumber){
        try{
            Statement st = getConnection().createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM accounts WHERE accountNumber = " + accountNumber);
            if(resultSet.next()) return resultSet;
        }
        catch(SQLException e){
            e.printStackTrace();;
        }
        
        return null;
    }
    
    public void saveAccount(Account acc){
        try(PreparedStatement st = getConnection().prepareStatement("INSERT INTO accounts VALUES(DEFAULT, ?, ?)")){
            st.setString(1, acc.getAccountNumber());
            st.setDouble(2, acc.getBalance());
            
            st.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
