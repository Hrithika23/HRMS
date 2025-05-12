package net.javaguides.hrms.database;

import java.sql.*;
import net.javaguides.hrms.bean.Salary;
import net.javaguides.hrms.util.DBConnection;

public class SalaryDAO {

    // Add salary to the database
    public boolean addSalary(Salary salary) {
        boolean result = false;
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DBConnection.getConnection();
            String sql = "INSERT INTO salary (emp_code, emp_name, salary, paid_days, pf_sal, esi_sal, pt, month, year, "
                       + "gross_salary, pf_ee, pf_er, esic_ee, esic_er, deduction, net_salary) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pst = con.prepareStatement(sql);
            pst.setString(1, salary.getEmpCode());
            pst.setString(2, salary.getEmpName());
            pst.setDouble(3, salary.getSalary());
            pst.setInt(4, salary.getPaidDays());
            pst.setDouble(5, salary.getPfSal());
            pst.setDouble(6, salary.getEsiSal());
            pst.setDouble(7, salary.getPt());
            pst.setString(8, salary.getMonth());
            pst.setInt(9, salary.getYear());
            pst.setDouble(10, salary.getGrossSalary());
            pst.setDouble(11, salary.getPfEe());
            pst.setDouble(12, salary.getPfEr());
            pst.setDouble(13, salary.getEsicEe());
            pst.setDouble(14, salary.getEsicEr());
            pst.setDouble(15, salary.getDeduction());
            pst.setDouble(16, salary.getNetSalary());

            result = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    // Get salary details by employee code
    public Salary getSalaryByEmpCode(String empCode) {
        Salary salary = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            String sql = "SELECT * FROM salary WHERE emp_code = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, empCode);
            rs = pst.executeQuery();

            if (rs.next()) {
                salary = new Salary();
                salary.setEmpCode(rs.getString("emp_code"));
                salary.setEmpName(rs.getString("emp_name"));
                salary.setSalary(rs.getDouble("salary"));
                salary.setPaidDays(rs.getInt("paid_days"));
                salary.setPfSal(rs.getDouble("pf_sal"));
                salary.setEsiSal(rs.getDouble("esi_sal"));
                salary.setPt(rs.getDouble("pt"));
                salary.setMonth(rs.getString("month"));
                salary.setYear(rs.getInt("year"));
                salary.calculateSalary();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return salary;
    }

    // Get salary details by employee code, month, and year
    public Salary getSalaryByEmpCodeMonthYear(String empCode, String month, int year) {
        Salary salary = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            String sql = "SELECT * FROM salary WHERE emp_code = ? AND month = ? AND year = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, empCode);
            pst.setString(2, month);
            pst.setInt(3, year);
            rs = pst.executeQuery();

            if (rs.next()) {
                salary = new Salary();
                salary.setEmpCode(rs.getString("emp_code"));
                salary.setEmpName(rs.getString("emp_name"));
                salary.setSalary(rs.getDouble("salary"));
                salary.setPaidDays(rs.getInt("paid_days"));
                salary.setPfSal(rs.getDouble("pf_sal"));
                salary.setEsiSal(rs.getDouble("esi_sal"));
                salary.setPt(rs.getDouble("pt"));
                salary.setMonth(rs.getString("month"));
                salary.setYear(rs.getInt("year"));
                salary.calculateSalary();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return salary;
    }

    // Delete salary by emp_code
    public static boolean deleteSalaryByEmpCode(String empCode) {
        boolean result = false;
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DBConnection.getConnection();
            String sql = "DELETE FROM salary WHERE emp_code = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, empCode);
            result = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }
}
