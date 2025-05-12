package net.javaguides.hrms.database;

import net.javaguides.hrms.bean.Staff;
import java.sql.*;

public class StaffDAO {
    private Connection conn;

    public StaffDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Save a new password and mark it as temporary (used for generated passwords).
     * @param empCode Employee code
     * @param hashedPassword Hashed version of the password
     * @return true if updated successfully
     * @throws SQLException
     */
    public boolean savePassword(String empCode, String hashedPassword) throws SQLException {
        String sql = "UPDATE staff SET password = ?, is_temp_password = ? WHERE emp_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) { // 🔄 Fixed from `connection` to `conn`
            stmt.setString(1, hashedPassword);
            stmt.setBoolean(2, true); // 🔐 Set temp flag to true
            stmt.setString(3, empCode);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Update password and mark it as permanent (after reset).
     * @param emp_code Employee code
     * @param newPassword Hashed new password
     * @throws SQLException
     */
    public void updatePassword(String emp_code, String newPassword) throws SQLException {
        String sql = "UPDATE staff SET password = ?, is_temp_password = FALSE WHERE emp_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, emp_code);
            stmt.executeUpdate();
        }
    }
    public void addStaff(Staff staff) throws SQLException {
        String sql = "INSERT INTO staff (emp_code, name, email, password) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, staff.getEmp_code());
        ps.setString(2, staff.getName());
        ps.setString(3, staff.getEmail());
        ps.setString(4, staff.getPassword());
        ps.executeUpdate();
    }


    /**
     * Get staff details by employee code.
     * @param emp_code Employee code
     * @return Staff object or null
     * @throws SQLException
     */
    public Staff getStaffByEmpCode(String emp_code) throws SQLException {
        String sql = "SELECT * FROM staff WHERE emp_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emp_code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Staff(
                    rs.getString("emp_code"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getBoolean("is_temp_password")
                );
            }
        }
        return null;
    }
}
