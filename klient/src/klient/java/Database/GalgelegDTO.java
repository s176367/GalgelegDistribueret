package Database;

import java.sql.*;
import java.util.ArrayList;

public class GalgelegDTO {
    public static void main(String[] args) {
        String brugernavn = "Frank";
        int user_id = 50;
        int antalforsoeg = 3;
        GalgelegDTO dto = new GalgelegDTO();
        try {
            dto.save("s176367", 3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void save(String brugernavn, int antalforsog) throws SQLException {
            Connection conn = DriverManager.getConnection("jdbc:mysql://s176367@db.diplomportal.dk:3306","s176367", "FzhBmzA9QCCDAU3KnIa55");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO s176367.Galgeleg(username, antalForkerte) values(?,?)");
            stmt.setString(1,brugernavn);
            stmt.setInt(2,antalforsog);
            stmt.execute();
    }
    public ArrayList<String> getAll() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://s176367@db.diplomportal.dk:3306","s176367", "FzhBmzA9QCCDAU3KnIa55");
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM s176367.Galgeleg");
        ResultSet rs = stmt.executeQuery();
        ArrayList<String> list = new ArrayList<>();
        while (rs.next()){
            String score="";
            score = rs.getString("username")+" :" + rs.getString("antalForkerte");
            list.add(score);
        }
        return list;
    }

}
