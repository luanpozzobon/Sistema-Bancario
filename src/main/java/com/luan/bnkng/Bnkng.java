package com.luan.bnkng;

import com.luan.bnkng.models.*;
import com.luan.bnkng.utils.*;
import java.sql.ResultSet;

public class Bnkng{
    private static final InputReader sc = new InputReader();
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
    
    private static void openAccount(){
        System.out.println("Para abrir sua conta precisamos das seguintes informações:");
        System.out.print("Nome completo: ");
        String name;
        name = sc.getNextLine();
        System.out.print("Data de nascimento (aaaa-mm-dd): ");
        String birthDate;
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
    
    private static void login(){
        System.out.println("Autentifique-se para poder acessar os serviços!");
        System.out.print("CPF: ");
        cpf = Formatter.formatCpf(sc.getNextLine());
        System.out.print("Password: ");
        password = sc.getNextLine();
        ResultSet rSet = user.searchUser(cpf, password);
        if(rSet != null){
            user = new User(rSet);
            acc = new Account(user);
            bank();
        } else{
            System.out.println("Credenciais Inválidas!");
        }
    }
    
    private static void bank(){
        while(true){
            System.out.printf("Bem-Vindo %s!\n", user.getName());
            System.out.println("Selecione a função desejada!");
            System.out.println("1-Editar Cadastro");
            System.out.println("2-Consultar Saldo");
            System.out.println("3-Transferências");
            System.out.println("4-Depósito");
            System.out.println("5-Retirada");
            System.out.println("0-Sair");
            
            switch(sc.getNextInt()){
                case 0:
                    return;
                case 1:
                    System.out.println("Altere os dados (deixe em branco para manter os dados atuais)");
                    System.out.print("E-Mail: ");
                    user.setEmail(sc.getNextLine());
                    System.out.print("Telefone: ");
                    user.setPhone(sc.getNextLine());
                    System.out.print("Senha: ");
                    password = sc.getNextLine();
                    if(!password.equals("")){
                        System.out.print("Confirme a senha: ");
                        while(!password.equals(sc.getNextLine())){
                            System.out.println("Senhas não correspondem! Tente novamente!");
                            System.out.print("Senha: ");
                            password = sc.getNextLine();
                            System.out.print("Confirme a senha: ");
                        }
                    }
                    user.setPassword(password);
                    user.modifyUser();
                    break;
                case 2:
                    System.out.println("Seu saldo é de R$" + acc.getBalance());
                    
                    break;
                case 3:
                    System.out.print("Digite a conta de destino: ");
                    Account destAccount = new Account(sc.getNextLine());
                    
                    System.out.print("Digite o valor desejado: ");
                    double value = sc.getNextDouble();
                    if(acc.transfer(destAccount, value)){
                        System.out.println("Transferência concluída com sucesso!");
                    } else{
                        System.out.println("Houve um erro ao fazer a transferência!");
                    }
                case 4:
                    System.out.print("Digite o valor a depositar: R$");
                    if(acc.deposit(sc.getNextDouble())){
                        System.out.println("Depósito realizado com sucesso!");
                    }
                    break;
                case 5:
                    System.out.print("Digite o valor a sacar: R$");
                    acc.withdraw(sc.getNextDouble());
                    break;
            }
        }
    }
}