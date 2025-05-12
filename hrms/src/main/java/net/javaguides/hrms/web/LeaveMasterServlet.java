package net.javaguides.hrms.web;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/LeaveMasterServlet")
public class LeaveMasterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empCode = request.getParameter("emp_code");
        int year = Integer.parseInt(request.getParameter("year"));
        int casualLeave = Integer.parseInt(request.getParameter("casual_leave"));
        int earnedLeave = Integer.parseInt(request.getParameter("earned_leave"));
        String maternityStr = request.getParameter("maternity_leave");
        int maternityLeave = maternityStr.isEmpty() ? 0 : Integer.parseInt(maternityStr);
        String action = request.getParameter("action");

        String successMessage = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hrms", "root", "Hrithi@12345");

            if ("Add".equals(action)) {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO leave_master(emp_code, year, casual_leave, earned_leave, maternity_leave) VALUES (?, ?, ?, ?, ?)");
                ps.setString(1, empCode);
                ps.setInt(2, year);
                ps.setInt(3, casualLeave);
                ps.setInt(4, earnedLeave);
                ps.setInt(5, maternityLeave);
                ps.executeUpdate();
                successMessage = "Leave record added successfully!";
            } else if ("Modify".equals(action)) {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE leave_master SET casual_leave=?, earned_leave=?, maternity_leave=? WHERE emp_code=? AND year=?");
                ps.setInt(1, casualLeave);
                ps.setInt(2, earnedLeave);
                ps.setInt(3, maternityLeave);
                ps.setString(4, empCode);
                ps.setInt(5, year);
                ps.executeUpdate();
                successMessage = "Leave record modified successfully!";
            } else if ("View".equals(action)) {
                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM leave_master WHERE emp_code=? AND year=?");
                ps.setString(1, empCode);
                ps.setInt(2, year);
                ResultSet rs = ps.executeQuery();
                StringBuilder result = new StringBuilder();
                while (rs.next()) {
                    result.append("<br>Emp Code: ").append(rs.getString("emp_code"));
                    result.append("<br>Year: ").append(rs.getInt("year"));
                    result.append("<br>Casual Leave: ").append(rs.getInt("casual_leave"));
                    result.append("<br>Earned Leave: ").append(rs.getInt("earned_leave"));
                    result.append("<br>Maternity Leave: ").append(rs.getInt("maternity_leave"));
                }
                successMessage = result.toString();
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            successMessage = "Error: " + e.getMessage();
        }

        // Set the success message as a request attribute
        request.setAttribute("successMessage", successMessage);

        // Forward the request back to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("leave_master.jsp");
        dispatcher.forward(request, response);
    }
}
