package org.example;

import java.sql.*;
import java.util.Scanner;

public class DBConnector {
    public static String name = "";
    public static int id;

    public static CurrentUser currentUsers;



    private static boolean FindLogin(String login){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/my_login_db", "postgres", "admin");
            String request = "SELECT * FROM public.users where login='" + login + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(request);
            while(resultSet.next()) {
                return true;
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Пользователь не найден");
            return false;
        }
        return false;
    }

    public static CurrentUser FindUser(String login, String password){
        boolean isFind = false;
        //password = Hasher.PasswordIsValid()
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/my_login_db", "postgres", "admin");
            String request = "SELECT * FROM public.users where login='" +login+ "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(request);
            if(login.isEmpty() || password.isEmpty()){
                isFind = false;
            }
            else {
                if (FindLogin(login)) {
                    isFind = true;
                    while (resultSet.next()) {
                        String pass = resultSet.getString("password");
                        name = resultSet.getString("username");
                        id = resultSet.getInt("id");
                        if (Hasher.PasswordIsValid(password, pass)) {
                            isFind = true;
                        } else {
                            isFind = false;
                        }
                    }
                    CurrentUser.setName(name);
                } else {
                    isFind = false;
                }
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            isFind = false;
            System.out.println("Пользователь не найден");
            return null;
        }
        if(isFind){
            return new CurrentUser(login, password, name, id);
        } else return null;
    }
    public static CurrentUser RegisterUser(String login, String password, String name) {
        try {
            Connection connection2 = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/my_login_db", "postgres", "admin");
            String request = "INSERT INTO users VALUES(DEFAULT,'" + name + "'," + "'" + password + "'," + "'" + login + "'" + ");";
            Statement statement = connection2.createStatement();
            ResultSet s = statement.executeQuery(request);
            if (!s.next()) {
                s.close();
                statement.close();
                connection2.close();
                return new CurrentUser(login, password, name, 0);
            }
            return new CurrentUser(login, password, name, 0);

        } catch (SQLException exception) {
            if (exception.getMessage().equals("Запрос не вернул результатов.")) {
                return new CurrentUser(login, password, name, 0);
            } else {
                System.out.println(exception.getMessage());
                return null;
            }
        }
    }

    public void PrintBD() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/my_login_db", "postgres", "admin");
            String request = "SELECT * FROM public.users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()){
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("login"));
                System.out.println(resultSet.getInt("id"));
                }
            }  catch (SQLException ignored){
        }
    }
    public void connect() {
        Scanner sc = new Scanner(System.in);
        System.out.println(" 1 - войти \n 2 - зарегаться");
        int command = sc.nextInt();
        if (command == 2){
            System.out.println("Введите логин");
            String login = sc.next();
            System.out.println("Введите имя");
            String regName = sc.next();
            System.out.println("Введите пароль");
            String regPassword = sc.next();
            regPassword = Hasher.hashPassword(regPassword);
            currentUsers =  RegisterUser(login,regPassword, regName);
            System.out.println("вы успешно зарегистрировались");
        } else if(command==1){
            System.out.println("Введите логин");
            String login = sc.next();
            System.out.println("Введите пароль");
            String password = sc.next();
            if(FindUser(login,password) != null){
                System.out.println("Вы вошли");
                currentUsers = FindUser(login,password);
            } else {
                System.out.println("Не правильно введены данные");
                connect();
            }
        }
    }
}
