package net.javaguides.hrms.database;

import net.javaguides.hrms.bean.Attendance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/hrms"; // Adjust DB name if needed
    private static final String USER = "root";
    private static final String PASS = "Hrithi@12345";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
    }

    // Add attendance (Login time and status)
    public boolean addAttendance(Attendance attendance) throws SQLException {
        String sql = "INSERT INTO attendance (emp_code, login_time, attendance_date, status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, attendance.getEmpCode());
            stmt.setTimestamp(2, attendance.getLoginTime());
            stmt.setDate(3, new java.sql.Date(attendance.getAttendanceDate().getTime()));
            stmt.setString(4, attendance.getStatus());
            
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Attendance successfully recorded for " + attendance.getEmpCode());
            }
            return rowsInserted > 0;
        }
    }

    // Get attendance records by employee code
    public List<Attendance> getAttendanceByEmpCode(String empCode) throws SQLException {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE emp_code = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, empCode);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setEmpCode(rs.getString("emp_code"));
                attendance.setLoginTime(rs.getTimestamp("login_time"));
                attendance.setLogoutTime(rs.getTimestamp("logout_time"));
                attendance.setAttendanceDate(rs.getDate("attendance_date"));
                attendance.setStatus(rs.getString("status"));
                list.add(attendance);
            }
        }
        return list;
    }

    // Get today's attendance record by employee code
    public Attendance getTodaysAttendanceByEmpCode(String empCode) throws SQLException {
        Attendance attendance = null;
        String sql = "SELECT * FROM attendance WHERE emp_code = ? AND attendance_date = CURDATE()";  // This gets today's attendance
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, empCode);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                attendance = new Attendance();
                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setEmpCode(rs.getString("emp_code"));
                attendance.setLoginTime(rs.getTimestamp("login_time"));
                attendance.setLogoutTime(rs.getTimestamp("logout_time"));
                attendance.setAttendanceDate(rs.getDate("attendance_date"));
                attendance.setStatus(rs.getString("status"));
            }
        }
        return attendance;  // Returns null if no attendance record for today
    }

    // Update logout time for the most recent attendance record of the employee
    public boolean updateLogoutTime(String empCode, Timestamp logoutTime) throws SQLException {
        // SQL query to find the most recent attendance record with NULL logout_time for the employee
        String sql = "UPDATE attendance SET logout_time = ? WHERE emp_code = ? AND logout_time IS NULL ORDER BY attendance_id DESC LIMIT 1";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, logoutTime); // Set the logout time
            stmt.setString(2, empCode);       // Set the employee code
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Return true if the update was successful
        }
    }
}
/*
 * package net.javaguides.hrms.database;
 * 
 * import net.javaguides.hrms.bean.Attendance;
 * 
 * import java.sql.*; import java.util.ArrayList; import java.util.List;
 * 
 * public class AttendanceDAO { private static final String URL =
 * "jdbc:mysql://localhost:3306/hrms"; // Adjust DB name if needed private
 * static final String USER = "root"; private static final String PASS =
 * "Hrithi@12345";
 * 
 * static { try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch
 * (ClassNotFoundException e) { throw new
 * RuntimeException("MySQL Driver not found", e); } }
 * 
 * // Add attendance (Login time and status) public boolean
 * addAttendance(Attendance attendance) throws SQLException { String sql =
 * "INSERT INTO attendance (emp_code, login_time, attendance_date, status) VALUES (?, ?, ?, ?)"
 * ;
 * 
 * try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
 * PreparedStatement stmt = conn.prepareStatement(sql)) {
 * 
 * stmt.setString(1, attendance.getEmpCode()); stmt.setTimestamp(2,
 * attendance.getLoginTime()); stmt.setDate(3, new
 * java.sql.Date(attendance.getAttendanceDate().getTime())); stmt.setString(4,
 * attendance.getStatus());
 * 
 * int rowsInserted = stmt.executeUpdate(); if (rowsInserted > 0) {
 * System.out.println("Attendance successfully recorded for " +
 * attendance.getEmpCode()); } return rowsInserted > 0; } }
 * 
 * // Get attendance records by employee code public List<Attendance>
 * getAttendanceByEmpCode(String empCode) throws SQLException { List<Attendance>
 * list = new ArrayList<>(); String sql =
 * "SELECT * FROM attendance WHERE emp_code = ?";
 * 
 * try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
 * PreparedStatement stmt = conn.prepareStatement(sql)) {
 * 
 * stmt.setString(1, empCode); ResultSet rs = stmt.executeQuery();
 * 
 * while (rs.next()) { Attendance attendance = new Attendance();
 * attendance.setAttendanceId(rs.getInt("attendance_id"));
 * attendance.setEmpCode(rs.getString("emp_code"));
 * attendance.setLoginTime(rs.getTimestamp("login_time"));
 * attendance.setLogoutTime(rs.getTimestamp("logout_time"));
 * attendance.setAttendanceDate(rs.getDate("attendance_date"));
 * attendance.setStatus(rs.getString("status")); list.add(attendance); } }
 * return list; }
 * 
 * public boolean updateLogoutTime(String empCode, Timestamp logoutTime) throws
 * SQLException { // SQL query to find the most recent attendance record with
 * NULL logout_time for the employee String sql =
 * "UPDATE attendance SET logout_time = ? WHERE emp_code = ? AND logout_time IS NULL ORDER BY attendance_id DESC LIMIT 1"
 * ;
 * 
 * try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
 * PreparedStatement stmt = conn.prepareStatement(sql)) {
 * 
 * stmt.setTimestamp(1, logoutTime); // Set the logout time stmt.setString(2,
 * empCode); // Set the employee code
 * 
 * int rowsUpdated = stmt.executeUpdate(); return rowsUpdated > 0; // Return
 * true if the update was successful } }
 * 
 * }
 */