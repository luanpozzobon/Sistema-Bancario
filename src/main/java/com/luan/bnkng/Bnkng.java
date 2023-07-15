package com.luan.bnkng;

import com.luan.bnkng.users.User;
import com.luan.bnkng.utils.InputReader;

public class Bnkng{
    private static final InputReader sc = new InputReader();
    private static String name;
    private static String birthDate;
    private static String cpf;
    private static String password;

    private static User user;
    
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
            }
        }
    }
    
    private static void login(){
        
    }
    
    private static void openAccount(){
        System.out.println("Para abrir sua conta precisamos das seguintes informações:");
        System.out.print("Nome completo: ");
        name = sc.getNextLine();
        System.out.print("Data de nascimento (aaaa-mm-dd): ");
        birthDate = sc.getNextLine();
        System.out.print("CPF: ");
        cpf = sc.getNextLine();
        
        user = new User(name, birthDate, cpf);
        if(!user.validateUser()){
            System.out.println("Infelizmente, vocÊ não está apto a abrir uma conta!");
            return;
        }
        
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
    }
}