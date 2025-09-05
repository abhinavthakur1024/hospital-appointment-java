import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement()) {

            System.out.println("✅ Connected to DB!");

            // Check if doctors table exists and print doctors
            ResultSet rs = st.executeQuery("SELECT id, name, specialty FROM doctors");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("specialty"));
            }

        } catch (Exception e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
