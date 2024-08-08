package trust_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        final String DB_URL = "jdbc:mysql://localhost:3306/";
        final String USERNAME = "root";
        final String PASSWORD = "1234";

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connected database successfully...");

            stmt = conn.createStatement();

            String sql = "CREATE DATABASE TRUST_MANAGEMENT";
            stmt.executeQuery(sql);

            System.out.println("Database created successfully...");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}