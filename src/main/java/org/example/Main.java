package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnector db = new DBConnector();
        db.connect();
        //db.PrintBD();
    }
}