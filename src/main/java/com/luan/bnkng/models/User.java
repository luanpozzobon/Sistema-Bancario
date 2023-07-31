package com.luan.bnkng.models;

import java.time.LocalDate;
import java.time.Period;
import com.luan.bnkng.dao.UsersDAO;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that represents an user.
 * The class contains the necessary data to open a Bank Account.
 * It also includes methods to validate and authenticate the user
 * @author Luan Pozzobon
 * @since 0.1
 */
public class User {
    private String name;
    private String birthDate;
    private String cpf;
    private String email;
    private String password;
    private String phone;
    private String accountNumber;
    private UsersDAO database;
    
    public User(){
        database = new UsersDAO();
    }

    /**
     * Class Constructor with the mandatory data to open an bank Account
     * @param name the user's full name
     * @param birthDate user's date of birth. Converts a string to a LocalDate type
     * @param cpf user's cpf
     */
    public User(String name, String birthDate, String cpf) {
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
        database = new UsersDAO();
    }

    public User(ResultSet rSet){
        database = new UsersDAO();
        try{
            name = rSet.getString("name");
            birthDate = rSet.getString("birthDate");
            cpf = rSet.getString("cpf");
            email = rSet.getString("email");
            phone = rSet.getString("phone");
            password = rSet.getString("password");
            accountNumber = rSet.getString("accountNumber");
            rSet.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(!email.equals("")){
            this.email = email;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(!password.equals("")){
            this.password = password;
        }
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(!phone.equals("")){
            this.phone = phone;
        }
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public UsersDAO getDatabase() {
        return database;
    }
    
    /**
     * Validate all the necessary conditions to open a Bank Account
     * Verify if the user is over 18 years old
     * Verify if the entered CPF is valid using {@link validateCpf}
     * After validate the user calls the {@link generateAccount} to create an account number
     * @return whether it is possible for this user to open an account
     */
    public boolean validateUser(){
        if(Period.between(LocalDate.parse(birthDate), LocalDate.now()).getYears() < 18){
            System.out.println("Apenas maiores de 18 anos podem abrir contas!");
            return false;
        }
        
        if(!validateCpf()){
            System.out.println("CPF inválido! Certifique-se de digitar o CPF corretamente!");
            return false;
        }
        return true;
    }
    
    /**
     * Verify some conditions to say whether is a valid cpf or not
     * Verify if the CPF isn't registered already {@link UsersDAO#searchCpf}
     * @return whether this cpf is valid or not
     */
    private boolean validateCpf(){        
        if(cpf.length() < 11) return false;
        
        if(cpf.matches("(\\d)\\1{10}")) return false;
        
        if(database.searchCpf(cpf)){
            System.out.println("CPF já cadastrado no sistema!");
            return false;
        }
        
        String tmp = cpf.substring(0,9);
        tmp += calculateDigit(tmp, 10);
        tmp += calculateDigit(tmp, 11);
        
        
        return cpf.equals(tmp);
    }
    
    /**
     * Helper method to validade the CPF number
     * Calculates the last two digits of the cpf
     * @param subCpf initial part of the cpf to calculate the digits
     * @param mult multiplier to calculate both cpf digits
     * @return the verifier digits of cpf
     */
    private int calculateDigit(String subCpf, int mult){
        int sum = 0;
        
        for(int i = 0; i < subCpf.length(); ++i){
            sum += Character.getNumericValue(subCpf.charAt(i)) * mult--;
        }
        
        sum = (sum*10)%11;
        
        return (sum == 10) ? 0 : sum;
    }
    
    /**
     * Saves the user information on the database {@link UsersDAO#createUser}
     * Says whether the operation was succesful or not
     */
    public void saveUser(){
        if(database.createUser(this)){
            System.out.println("Usuário cadastrado com sucesso!");
            System.out.println("Conta: " + accountNumber);
        } else {
            System.out.println("Erro ao cadastrar usuário! Verifique os dados e tente novamente!");
        }
    }
    
    public void modifyUser(){
        if(database.modifyUser(this)){
            System.out.println("Dados alterados com sucesso!");
        } else{
            System.out.println("Erro ao alterar dados do usuário!");
        }
    }
    
    public ResultSet searchUser(String cpf, String password){
        return database.searchUser(cpf, password);
    }
}
