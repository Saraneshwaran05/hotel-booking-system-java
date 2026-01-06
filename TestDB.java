import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hoteldb",
                "root",
                "Saran23@"   
            );
            System.out.println("Database Connected Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
