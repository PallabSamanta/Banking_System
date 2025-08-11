import java.sql.*;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;
    public User(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void register(){
        scanner.nextLine();
        System.out.println("Full name: ");
        String full_name = scanner.nextLine();
        System.out.println("email: ");
        String email = scanner.nextLine();
        System.out.println("password: ");
        String password = scanner.nextLine();
        if(user_exists(email)){
            System.out.println("User already exists for this email!!");
            return;
        }
        String query = "insert into user(full_name,email,password) values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,full_name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Registration Successful!!");
            }
            else {
                System.out.println("Registration failed");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String login(){
        scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        String query = "select * from user where email = ? and password = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return email;
            else return null;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean user_exists(String email){
        String query = "select * from user where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else return false;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
