package Database;

import java.sql.*;

public class GalgelegDTO {
    public static void main(String[] args) {
        String brugernavn = "Frank";
        int user_id = 50;
        int antalforsoeg = 3;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("s176367@db.diplomportal.dk:3306");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Galgeleg values ");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
