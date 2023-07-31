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
            ResultSet rSet = st.executeQuery("SELECT * FROM accounts WHERE accountNumber = " + accountNumber);
            if(rSet.next()) return rSet;
        }
        catch(SQLException e){
            e.printStackTrace();;
        }
        
        return null;
    }
    
    public void saveAccount(Account acc){
        try(PreparedStatement st = getConnection().prepareStatement("INSERT INTO accounts VALUES(?, ?)")){
            st.setString(1, acc.getAccountNumber());
            st.setDouble(2, acc.getBalance());
            
            st.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public boolean transfer(Account origAccount, Account destAccount, double value){
        try(PreparedStatement stOrigin = getConnection().prepareStatement("UPDATE accounts SET balance = ? WHERE accountNumber = ?");
            PreparedStatement stDestiny = getConnection().prepareStatement("UPDATE accounts SET balance = ? WHERE accountNumber = ?")){
            
            getConnection().setAutoCommit(false);
            
            stOrigin.setDouble(1, origAccount.getBalance() - value);
            stOrigin.setString(2, origAccount.getAccountNumber());
            stOrigin.executeUpdate();
            
            stDestiny.setDouble(1, destAccount.getBalance() + value);
            stDestiny.setString(2, destAccount.getAccountNumber());
            stDestiny.executeUpdate();
            
            getConnection().commit();
            getConnection().setAutoCommit(true);
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            try{
                getConnection().rollback();
                getConnection().setAutoCommit(true);
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
            return false;
        }
    }
    
    public boolean changeBalance(Account acc, double value){
        try(PreparedStatement st = getConnection().prepareStatement("UPDATE accounts SET balance = ? WHERE accountNumber = ?")){          
            st.setDouble(1, acc.getBalance() + value);
            st.setString(2, acc.getAccountNumber());
            
            st.executeUpdate();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
