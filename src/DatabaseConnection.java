import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    // Metode untuk membuat koneksi ke database
    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:contacts.db"; // Nama file database SQLite
            conn = DriverManager.getConnection(url);
            System.out.println("Koneksi ke database berhasil.");
            createTable(conn); // Memastikan tabel dibuat jika belum ada
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Metode untuk membuat tabel jika belum ada
    private static void createTable(Connection conn) {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS contacts (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "nama TEXT NOT NULL," +
                                "nomor_telepon TEXT NOT NULL," +
                                "kategori TEXT NOT NULL" +
                                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCreateTable);
            System.out.println("Tabel 'contacts' siap digunakan.");
        } catch (SQLException e) {
            System.out.println("Gagal membuat tabel: " + e.getMessage());
        }
    }
}
