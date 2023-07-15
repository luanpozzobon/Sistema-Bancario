package com.luan.bnkng.users;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import com.luan.bnkng.dao.UsersDAO;

/**
 *
 * @author luanp
 */
public class User {
    private int userId;
    private final String name;
    private LocalDate birthDate;
    private String cpf;
    private String email;
    private String password;
    private String phone;
    private String accountNumber;
    private UsersDAO banco = new UsersDAO();
    
    public User(String name, String birthDate, String cpf) {
        this.name = name;
        this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ISO_DATE);
        this.cpf = cpf;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }    
    
    public boolean validateUser(){
        if(Period.between(birthDate, LocalDate.now()).getYears() < 18){
            System.out.println("Apenas maiores de 18 anos podem abrir contas!");
            return false;
        }
        
        if(!validateCpf()){
            System.out.println("CPF inválido! Certifique-se de digitar o CPF corretamente!");
            return false;
        }
        
        return true;
    }
    
    private boolean validateCpf(){
        cpf = cpf.replaceAll("[^0-9]", "");
        
        if(cpf.length() < 11) return false;
        
        if(cpf.matches("(\\d)\\1{10}")) return false;
        
        if(banco.searchCpf(cpf)){
            System.out.println("CPF já cadastrado no sistema!");
            return false;
        }
        
        String tmp = cpf.substring(0,9);
        tmp += calcDigit(tmp, 10);
        tmp += calcDigit(tmp, 11);
        
        
        return cpf.equals(tmp);
    }
    
    private int calcDigit(String subCpf, int mult){
        int sum = 0;
        
        for(int i = 0; i < subCpf.length(); ++i){
            sum += Character.getNumericValue(subCpf.charAt(i)) * mult--;
        }
        
        sum = (sum*10)%11;
        
        return (sum == 10) ? 0 : sum;
    }
    
    public void saveUser(){
        if(banco.createUser(this)){
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar usuário! Verifique os dados e tente novamente!");
        }
    }
}
