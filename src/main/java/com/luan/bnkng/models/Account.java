package com.luan.bnkng.models;

import com.luan.bnkng.dao.AccountsDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 *
 * @author luanp
 */
public class Account {
    private String accountNumber;
    private double balance;
    private final AccountsDAO database;
    
    public Account(){
        database = new AccountsDAO();
    }
    
    public Account(String accountNumber){
        database = new AccountsDAO();
        ResultSet rSet = database.searchAccount(accountNumber);
        try{
            this.accountNumber = rSet.getString("accountNumber");
            this.balance = rSet.getDouble("balance");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public Account(User user){
        database = new AccountsDAO();
        if(user.getAccountNumber() != null){
            ResultSet rSet = database.searchAccount(user.getAccountNumber());
            try{
                accountNumber = rSet.getString("accountNumber");
                balance = rSet.getDouble("balance");
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        } else {
            generateAccountNumber(user);
            balance = 0;
        }
        
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance){
        this.balance = balance;
    }
    
    public void generateAccountNumber(User user){
        Random rd = new Random();
        do{
            accountNumber = user.getBirthDate().substring(2, 4);
            accountNumber += user.getCpf().substring(9, 11);
            accountNumber += String.valueOf(rd.nextInt(1000000));
        }while(database.searchAccount(accountNumber) != null);
        user.setAccountNumber(accountNumber);
    }
    
    public void saveAccount(){
        database.saveAccount(this);
    }
    
    public boolean transfer(Account destAccount, double value){
        if(this.getBalance() >= value){
            if(database.transfer(this, destAccount, value)){
                this.setBalance(this.getBalance() - value);
                destAccount.setBalance(destAccount.getBalance() + value);
                return true;
            }
        }
        
        return false;
    }
    
    public boolean deposit(double value){
        if(database.changeBalance(this, value)){
            setBalance(getBalance() + value);
            return true;
        }
        return false;
    }
    
    public boolean withdraw(double value){
        if(database.changeBalance(this, -value)){
            setBalance(getBalance() - value);
            return true;
        }
        return false;
    }
}
