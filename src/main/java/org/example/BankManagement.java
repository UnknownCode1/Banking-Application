package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BankManagement {

    public static boolean createAccount(String userName, String password, String name, int balance) throws SQLException {
        Connection connection = JdbcConnection.connection();
        Statement statement = connection.createStatement();

        String query = "CREATE TABLE IF NOT EXISTS customers( username TEXT, password TEXT, name TEXT, balance int )";
        statement.execute(query);

        String addUser = "INSERT INTO customers(username, password, name, balance) values ('" + userName + "','" + password + "', '" + name +"' , '" + balance + "')";
        statement.execute(addUser);
        System.out.println("user added to database");
        statement.close();
        connection.close();
        return true;
    }


    public static List<String> loginAccount(String userName, String password) throws SQLException {
        Connection connection = JdbcConnection.connection();
        Statement statement = connection.createStatement();

        String getUser = "SELECT * FROM customers WHERE userName = '" + userName +"'";
        ResultSet val = statement.executeQuery(getUser);
        System.out.println("Fetched the user details");

        List<String> userData = null;
        boolean found = false;
        while(val.next()) {
            String username = val.getString(1);
            String pass = val.getString(2);
            String name = val.getString(3);
            int balance = val.getInt(4);
            userData = new ArrayList<>();
            userData.add(name);
            userData.add(String.valueOf(balance));
            if (userName.equals(username) && pass.equals(password)) {
                found = true;
            }
        }
        statement.close();
        connection.close();
        return userData;
    }

    public static  String transferMoney(String senderName, String receiverName, int amountToTransfer) throws SQLException {
        Connection connection = JdbcConnection.connection();
        Statement statement = connection.createStatement();

        String senderDataQuery = "SELECT * FROM customers WHERE name = '" + senderName + "' ";
        String receiverDataQuery = "SELECT * FROM customers WHERE name = '" + receiverName + "' ";

        ResultSet senderData = statement.executeQuery(senderDataQuery);
        int senderBalance=0;
        while(senderData.next()){
            senderBalance = senderData.getInt(4);
        }


        ResultSet receiverData = statement.executeQuery(receiverDataQuery);
//        receiverData.beforeFirst();
        int receiverBalance=0;
        if(receiverData.next()){
            do{
                receiverBalance = receiverData.getInt(4);
            }while(receiverData.next());
        }else {
            connection.close();
            statement.close();
            return "The receiverName account doesn't exist, Transaction Failed!";
        }

        if(amountToTransfer > senderBalance){
            return "Transaction Failed! you're trying to send more than the amount available in your account!";
        }
        senderBalance -= amountToTransfer;
        receiverBalance += amountToTransfer;

        String senderBalanceUpdate = "UPDATE customers SET balance = '"+senderBalance+"' WHERE name = '"+senderName+"'";
        String receiverBalanceUpdate = "UPDATE customers SET balance = '"+receiverBalance+"'WHERE name ='"+receiverName+"'";

        if(statement.executeUpdate(senderBalanceUpdate)==1){
            System.out.println("sender Balance Updated");
        }
        if(statement.executeUpdate(receiverBalanceUpdate)==1){
            System.out.println("Receiver Balance Updated");
        }

        connection.close();
        statement.close();
        return "Transaction Successfull";
    }
}
