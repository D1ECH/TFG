import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PruebaJDBC {
    public static void main(String[] args) {
        final String DB_URL = "jdbc:mysql://localhost/trust_management";
        final String USERNAME = "root";
        final String PASSWORD = "";

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connected database successfully...");

            stmt = conn.createStatement();

            String sql = "SELECT * FROM PRUEBA";
            ResultSet miResultSet = stmt.executeQuery(sql);

            while (miResultSet.next()){
                System.out.println(miResultSet.getString(1) + " " + miResultSet.getString(2));
            }

            System.out.println("Database created successfully...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
