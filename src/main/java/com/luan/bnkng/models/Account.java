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
    private int accountId;
    private String accountNumber;
    private double balance;
    private final AccountsDAO database;
    
    public Account(){
        database = new AccountsDAO();
    }
    
    public Account(User user){
        database = new AccountsDAO();
        if(user.getAccountNumber() != null){
            ResultSet rSet = database.searchAccount(user.getAccountNumber());
            try{
                accountId = rSet.getInt("accountId");
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
}
