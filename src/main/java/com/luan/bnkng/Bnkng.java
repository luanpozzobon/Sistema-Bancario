package com.luan.bnkng;

import com.luan.bnkng.models.*;
import com.luan.bnkng.utils.Formatter;
import com.luan.bnkng.utils.InputReader;
import java.sql.ResultSet;

public class Bnkng{
    private static final InputReader sc = new InputReader();
    private static String name;
    private static String birthDate;
    private static String cpf;
    private static String password;

    private static User user = new User();
    private static Account acc = new Account();
    
    public static void main(String[] args) {
        while(true){
            System.out.println("Bem-Vindo ao Banco Bnkng!");
            System.out.println("Faça seu login, ou abra já sua conta!");
            System.out.println("1-Abrir conta");
            System.out.println("2-Login");
            System.out.println("0-Sair");

            switch(sc.getNextInt()){
                case 0:
                    System.out.println("Obrigado por utilizar nosso banco!");
                    System.exit(0);
                case 1:
                    openAccount();
                    break;
                case 2:
                    login();
                    break;
            }
        }
    }
    
    private static void login(){
        System.out.println("Autentifique-se para poder acessar os serviços!");
        System.out.print("CPF: ");
        cpf = Formatter.formatCpf(sc.getNextLine());
        System.out.print("Password: ");
        password = sc.getNextLine();
        ResultSet rSet;
        if((rSet = user.searchUser(cpf, password)) != null){
            System.out.println("Bem-Vindo!");
            user = new User(rSet);
            acc = new Account(user);
        } else{
            System.out.println("Credenciais Inválidas!");
        }
    }
    
    private static void openAccount(){
        System.out.println("Para abrir sua conta precisamos das seguintes informações:");
        System.out.print("Nome completo: ");
        name = sc.getNextLine();
        System.out.print("Data de nascimento (aaaa-mm-dd): ");
        birthDate = sc.getNextLine();
        System.out.print("CPF: ");
        cpf = Formatter.formatCpf(sc.getNextLine());
        
        user = new User(name, birthDate, cpf);
        if(!user.validateUser()){
            System.out.println("Infelizmente, você não está apto a abrir uma conta!");
            return;
        }
        acc = new Account(user);
        
        System.out.println("Quase lá! Precisamos apenas mais algumas informações!");
        System.out.print("E-Mail: ");
        user.setEmail(sc.getNextLine());
        System.out.print("Telefone: ");
        user.setPhone(sc.getNextLine());
        System.out.println("O último passo é criar uma senha de acesso à sua conta!");
        System.out.print("Senha: ");
        password = sc.getNextLine();
        System.out.print("Confirme a senha: ");
        while(!sc.getNextLine().equals(password)){
            System.out.println("Senhas não correspondem! Tente novamente!");
            System.out.print("Senha: ");
            password = sc.getNextLine();
            System.out.print("Confirme a senha: ");
        }
        user.setPassword(password);
        
        user.saveUser();
        acc.saveAccount();
    }
}