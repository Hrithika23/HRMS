package net.javaguides.hrms.web;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@WebServlet("/LeaveApplyServlet")
public class LeaveApplyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    	String empCode = request.getParameter("emp_code"); // This will now work

        String leaveType = request.getParameter("leave_type");
        String fromDate = request.getParameter("from_date");
        String toDate = request.getParameter("to_date");

        int days = 0;
        try {
            LocalDate from = LocalDate.parse(fromDate);
            LocalDate to = LocalDate.parse(toDate);
            days = (int) ChronoUnit.DAYS.between(from, to) + 1;
        } catch (Exception e) {
            days = 1;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrms", "root", "Hrithi@12345");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO leave_requests (emp_code, leave_type, from_date, to_date, days, status) VALUES (?, ?, ?, ?, ?, 'Pending')");
            ps.setString(1, empCode);
            ps.setString(2, leaveType);
            ps.setDate(3, Date.valueOf(fromDate));
            ps.setDate(4, Date.valueOf(toDate));
            ps.setInt(5, days);
            ps.executeUpdate();

            response.sendRedirect("leave_apply.jsp?msg=success");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("leave_apply.jsp?msg=error");
        }
    }
}
