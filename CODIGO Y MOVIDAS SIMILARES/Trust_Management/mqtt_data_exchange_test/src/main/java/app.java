import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class app {
    public static void main(String[] args) {
        try {
            //1. Crear conexión
            Connection miConexion = DriverManager.getConnection("jdbc:mysql//localhost:3306/trust_management", "root", "");

            //2. Crear objeto statement
            Statement miStatement = miConexion.createStatement();

            //3. Ejecutar SQL
            ResultSet miResultSet = miStatement.executeQuery("SELECT * FROM REPUTACION");

            //4. Recorrer el ResultSet
            while (miResultSet.next()){
                System.out.println(miResultSet.getString(1) + " " + miResultSet.getString(2));
            }
        }catch (Exception e){
            System.out.println("No conecta!!!");
            e.printStackTrace();
        }
    }
}
