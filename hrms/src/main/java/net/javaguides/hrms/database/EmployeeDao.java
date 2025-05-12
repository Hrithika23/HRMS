package net.javaguides.hrms.database;



import net.javaguides.hrms.bean.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDao {

    public int registerEmployee(Employee emp) {
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hrms", "root", "Hrithi@12345"
            );

            String sql = "INSERT INTO employee_master (emp_code, emp_name, gender, dob, home_no, street, taluk, district, pincode, state, country, email, contact_no, emergency_contact, higher_education, specialization, department, designation, date_of_resign, relieving_date, next_company, previous_employment_from, previous_employment_to, reference_name, reference_contact_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, emp.getEmpCode());
            ps.setString(2, emp.getEmpName());
            ps.setString(3, emp.getGender());
            ps.setDate(4, emp.getDob() != null ? new java.sql.Date(emp.getDob().getTime()) : null);
            ps.setString(5, emp.getHomeNo());
            ps.setString(6, emp.getStreet());
            ps.setString(7, emp.getTaluk());
            ps.setString(8, emp.getDistrict());
            ps.setString(9, emp.getPincode());
            ps.setString(10, emp.getState());
            ps.setString(11, emp.getCountry());
            ps.setString(12, emp.getEmail());
            ps.setString(13, emp.getContactNo());
            ps.setString(14, emp.getEmergencyContact());
            ps.setString(15, emp.getHigherEducation());
            ps.setString(16, emp.getSpecialization());
            ps.setString(17, emp.getDepartment());
            ps.setString(18, emp.getDesignation());
            ps.setDate(19, emp.getDateOfResign() != null ? new java.sql.Date(emp.getDateOfResign().getTime()) : null);
            ps.setDate(20, emp.getRelievingDate() != null ? new java.sql.Date(emp.getRelievingDate().getTime()) : null);
            ps.setString(21, emp.getNextCompany());
            ps.setDate(22, emp.getPreviousEmploymentFrom() != null ? new java.sql.Date(emp.getPreviousEmploymentFrom().getTime()) : null);
            ps.setDate(23, emp.getPreviousEmploymentTo() != null ? new java.sql.Date(emp.getPreviousEmploymentTo().getTime()) : null);
            ps.setString(24, emp.getReferenceName());
            ps.setString(25, emp.getReferenceContactNo());
            
            result = ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Employee getEmployeeByCode(String empCode) {
        Employee employee = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hrms", "root", "Hrithi@12345"
            );

            String sql = "SELECT * FROM employee_master WHERE emp_code = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, empCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employee = new Employee();
                employee.setEmpCode(rs.getString("emp_code"));
                employee.setEmpName(rs.getString("emp_name"));
                employee.setGender(rs.getString("gender"));
                employee.setDob(rs.getDate("dob"));
                employee.setHomeNo(rs.getString("home_no"));
                employee.setStreet(rs.getString("street"));
                employee.setTaluk(rs.getString("taluk"));
                employee.setDistrict(rs.getString("district"));
                employee.setPincode(rs.getString("pincode"));
                employee.setState(rs.getString("state"));
                employee.setCountry(rs.getString("country"));
                employee.setEmail(rs.getString("email"));
                employee.setContactNo(rs.getString("contact_no"));
                employee.setEmergencyContact(rs.getString("emergency_contact"));
                employee.setHigherEducation(rs.getString("higher_education"));
                employee.setSpecialization(rs.getString("specialization"));
                employee.setDepartment(rs.getString("department"));
                employee.setDesignation(rs.getString("designation"));
                employee.setDateOfResign(rs.getDate("date_of_resign"));
                employee.setRelievingDate(rs.getDate("relieving_date"));
                employee.setNextCompany(rs.getString("next_company"));
                employee.setPreviousEmploymentFrom(rs.getDate("previous_employment_from"));
                employee.setPreviousEmploymentTo(rs.getDate("previous_employment_to"));
                employee.setReferenceName(rs.getString("reference_name"));
                employee.setReferenceContactNo(rs.getString("reference_contact_no"));
                

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}
