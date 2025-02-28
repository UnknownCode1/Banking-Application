package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Bank{

    public static void main(String []args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        //Variables to store name, account_no and an integer to store the selected option
        String name;
        String userName;
        String password;
        int balance = 0;
        int pss=134;

        System.out.println("Welcome to BobBank");

        while(true){
            System.out.println("1.Create Account");
            System.out.println("2.Login Account");
            System.out.println("3.Exit from Bank");
            //Takes user input for the provided options
            System.out.print("Choose an Option from the above : ");
            int choosenOption = sc.nextInt();
            sc.nextLine();
            while(choosenOption==1){
                    System.out.println("Enter details for account Creation ");
                    System.out.print("Enter userName : ");
                    userName = sc.nextLine();
                    System.out.print("Enter password : ");
                    password = sc.nextLine();
                    System.out.print("Enter your name : ");
                    name = sc.nextLine();
                    System.out.print("Enter balance : ");
                    balance = sc.nextInt();
                    sc.nextLine();
                    if(userName=="" || password ==""){
                        System.out.println("username or password fileds can't be empty");
                        System.out.println("Do you want to exit account creation : Yes or No?");
                        String exitStatus = sc.nextLine().toLowerCase();
                        if(exitStatus.equals("yes")){
                            System.out.println("Exited Bank Account creation");
                            break;
                        }else{
                            continue;
                        }
                    }
                    boolean accountCreationStatus = BankManagement.createAccount(userName, password, name, balance);
                    if(accountCreationStatus){
                        System.out.println("Successfully Created your account");
                        break;
                    }
                    else {
                        System.out.println("Something went wrong try again!");
                        break;
                    }
            }

            while(choosenOption==2){
                System.out.print("Enter your username : ");
                userName = sc.nextLine();
                System.out.print("Enter your password : ");
                password = sc.nextLine();
                List<String> userData = BankManagement.loginAccount(userName, password);
                if(userData==null){
                    System.out.println("Enter correct username or password");
                    continue;
                }else{
                    System.out.println("Login Successful!");
                    while(true){
                        System.out.println("1.View Balance");
                        System.out.println("2.TransferMoney");
                        System.out.println("3.Logout");
                        System.out.print("Chose from the options provided : ");
                        int userActivity = sc.nextInt();
                        sc.nextLine();
                        if(userActivity==1){
                            System.out.println("Your Balance : " + userData.get(1));
                        }
                        if(userActivity==2){
                            System.out.print("Enter the name of the person you want to transfer to : ");
                            String receiverName = sc.nextLine();
                            System.out.print("Enter the amount to be transferred : ");
                            int amountToTransfer = sc.nextInt();
                            sc.nextLine();
                            String transactionStatus = BankManagement.transferMoney(userData.get(0), receiverName, amountToTransfer);
                            System.out.println(transactionStatus);
                            userData = BankManagement.loginAccount(userName, password);
                            continue;
                        }
                        if(userActivity==3){
                            System.out.println("LogOut Successful");
                            break;
                        }
                    }
                }
                System.out.println("Navigating to HomePage");
                break;
            }

            if(choosenOption==3){
                System.out.println("Exiting from Bank Application");
                break;
            }
            if(choosenOption > 3){
                System.out.println("The chosen option isn't available");
            }
        }


    }


}