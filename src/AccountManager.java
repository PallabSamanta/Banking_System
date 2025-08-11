import java.sql.*;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;
    public AccountManager(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void credit_money(Long account_number) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter the amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter security number: ");
        String security_pin = scanner.nextLine();
        try{
            connection.setAutoCommit(false);
            if(account_number!=0){
                PreparedStatement preparedStatement = connection.prepareStatement("select * from accounts where account_number = ? and security_pin = ?");
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2,security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    String query = "update accounts set balance = balance + ? where account_number = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(query);
                    preparedStatement1.setDouble(1, amount);
                    preparedStatement1.setLong(2, account_number);
                    int rowsAffected = preparedStatement1.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Rs. " + amount + " is credited successfully");
                        connection.commit();
                        connection.setAutoCommit(true);
                    } else {
                        System.out.println("Transaction failed");
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                } else{
                        System.out.println("Invalid security pin");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void debit_money(Long account_number) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter the amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter security number: ");
        String security_pin = scanner.nextLine();
        try{
            connection.setAutoCommit(false);
            if(account_number!=0){
                PreparedStatement preparedStatement = connection.prepareStatement("select * from accounts where account_number = ? and security_pin = ?");
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2,security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    long current_balance = resultSet.getLong("balance");
                    if(amount<=current_balance) {
                        String query = "update accounts set balance = balance - ? where account_number = ?";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(query);
                        preparedStatement1.setDouble(1, amount);
                        preparedStatement1.setLong(2, account_number);
                        int rowsAffected = preparedStatement1.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Rs. " + amount + " is credited successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                        } else {
                            System.out.println("Transaction failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient balance");
                    }
                } else{
                    System.out.println("Invalid security pin");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void transfer_money(Long sender_account_number) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        long receiver_account_number = scanner.nextLong();
        System.out.println("Enter the amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter security number: ");
        String security_pin = scanner.nextLine();
        try{
            connection.setAutoCommit(false);
            if(sender_account_number!=0 && receiver_account_number!=0){
                PreparedStatement preparedStatement = connection.prepareStatement("select * from accounts where account_number = ? and security_pin = ?");
                preparedStatement.setLong(1,sender_account_number);
                preparedStatement.setString(2,security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    double current_balance = resultSet.getLong("balance");
                    if(amount<=current_balance) {
                        String debit_query = "update accounts set balance = balance - ? where account_number = ?";
                        String credit_query = "update accounts set balance = balance + ? where account_number = ?";
                        PreparedStatement debitpreparedStatement = connection.prepareStatement(debit_query);
                        PreparedStatement creditpreparedStatement = connection.prepareStatement(credit_query);
                        debitpreparedStatement.setDouble(1, amount);
                        debitpreparedStatement.setLong(2, sender_account_number);
                        creditpreparedStatement.setDouble(1, amount);
                        creditpreparedStatement.setLong(2, receiver_account_number);
                        int rowsAffected1 = debitpreparedStatement.executeUpdate();
                        int rowsAffected2 = creditpreparedStatement.executeUpdate();
                        if (rowsAffected1 > 0 && rowsAffected2>0) {
                            System.out.println("Transaction Successful!");
                            System.out.println("Rs."+amount+" Transferred Successfully from "+sender_account_number+" account to "+receiver_account_number+" account");
                            connection.commit();
                            connection.setAutoCommit(true);
                        } else {
                            System.out.println("Transaction failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient balance");
                    }
                } else{
                    System.out.println("Invalid security pin");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void getBalance(long account_number){
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                double balance = resultSet.getDouble("balance");
                System.out.println("Balance: "+balance);
            }else{
                System.out.println("Invalid Pin!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
