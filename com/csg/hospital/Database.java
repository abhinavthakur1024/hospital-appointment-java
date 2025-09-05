// package com.csg.hospital;

// import java.sql.*;

// public class Database {
//     private static final String DB_URL = "jdbc:sqlite:hospital.db";

//     static {
//         // Initialize schema when the class loads
//         try (Connection conn = getConnection()) {
//             initSchema(conn);
//         } catch (SQLException e) {
//             throw new RuntimeException("Failed to init DB schema: " + e.getMessage(), e);
//         }
//     }

//     public static Connection getConnection() throws SQLException {
//         return DriverManager.getConnection(DB_URL);
//     }

//     private static void initSchema(Connection conn) throws SQLException {
//         try (Statement st = conn.createStatement()) {
//             st.executeUpdate("PRAGMA foreign_keys=ON;");

//             st.executeUpdate("""
//                 CREATE TABLE IF NOT EXISTS doctors (
//                     id INTEGER PRIMARY KEY AUTOINCREMENT,
//                     name TEXT NOT NULL,
//                     specialty TEXT NOT NULL
//                 );
//             """);

//             st.executeUpdate("""
//                 CREATE TABLE IF NOT EXISTS patients (
//                     id INTEGER PRIMARY KEY AUTOINCREMENT,
//                     name TEXT NOT NULL,
//                     phone TEXT NOT NULL
//                 );
//             """);

//             st.executeUpdate("""
//                 CREATE TABLE IF NOT EXISTS appointments (
//                     id INTEGER PRIMARY KEY AUTOINCREMENT,
//                     doctor_id INTEGER NOT NULL,
//                     patient_id INTEGER NOT NULL,
//                     time TEXT NOT NULL,
//                     status TEXT NOT NULL CHECK (status IN ('BOOKED','CANCELLED')),
//                     created_at TEXT NOT NULL DEFAULT (datetime('now')),
//                     FOREIGN KEY(doctor_id) REFERENCES doctors(id) ON DELETE CASCADE,
//                     FOREIGN KEY(patient_id) REFERENCES patients(id) ON DELETE CASCADE,
//                     UNIQUE(doctor_id, time)
//                 );
//             """);
//         }
//     }
// }

package com.csg.hospital;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TestDB";
    private static final String USER = "root"; 
    private static final String PASSWORD = "9915336387@Ab";

    static {
        // Initialize schema when the class loads
        try (Connection conn = getConnection()) {
            initSchema(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to init DB schema: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    private static void initSchema(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {

            // MySQL supports foreign keys by default; no PRAGMA needed
            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS doctors (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    specialty VARCHAR(255) NOT NULL
                );
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS patients (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    phone VARCHAR(20) NOT NULL
                );
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS appointments (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    doctor_id INT NOT NULL,
                    patient_id INT NOT NULL,
                    time DATETIME NOT NULL,
                    status ENUM('BOOKED','CANCELLED') NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE,
                    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
                    UNIQUE KEY unique_appointment (doctor_id, time)
                );
            """);
        }
    }
}
