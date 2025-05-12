package net.javaguides.hrms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hrms"; // Replace with your database URL
    private static final String USER = "root"; // Replace with your database username
    private static final String PASSWORD = "Hrithi@12345"; // Replace with your database password

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }
    }

    // Method to close the database connection and statement
    public static void closeConnection(Connection con, PreparedStatement pst) {
        try {
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/*
 * package net.javaguides.hrms.util;
 * 
 * 
 * 
 * import java.sql.Connection; import java.sql.DriverManager; import
 * java.sql.SQLException;
 * 
 * public class DBConnection { private static final String URL =
 * "jdbc:mysql://localhost:3306/hrms"; private static final String USER =
 * "root"; private static final String PASSWORD = "Hrithi@12345";
 * 
 * private static Connection connection = null;
 * 
 * public static Connection getConnection() { if (connection == null) { try {
 * Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8+ driver connection =
 * DriverManager.getConnection(URL, USER, PASSWORD);
 * System.out.println("✅ Database connected successfully."); } catch
 * (ClassNotFoundException | SQLException e) {
 * System.out.println("❌ Database connection failed."); e.printStackTrace(); } }
 * return connection; } }
 */
