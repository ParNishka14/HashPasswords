package org.example;

public class CurrentUser {
    private static String name;
    private static String login;
    private static String password;
    private static int id;

    public CurrentUser() {

    }


    public CurrentUser(String login, String password, String name, int id) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.id = id;

    }

    public static void setName(String name) {
        CurrentUser.name = name;
    }

    public static void setLogin(String login) {
        CurrentUser.login = login;
    }

    public static void setPassword(String password) {
        CurrentUser.password = password;
    }

    public static String getPassword() {
        return password;
    }

    public static String getName() {
        return name;
    }

    public static String getLogin(){
        return login;
    }

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
