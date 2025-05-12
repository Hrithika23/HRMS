package net.javaguides.hrms.web;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.io.PrintWriter;
import java.io.StringWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;

import net.javaguides.hrms.util.EmailUtil; // ✅ Ensure this is available

@WebServlet("/LeaveApprovalServlet")
public class LeaveApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Update with your DB connection
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hrms";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Hrithi@12345";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int requestId = Integer.parseInt(request.getParameter("id"));
        String empCode = request.getParameter("emp_code");
        String action = request.getParameter("action"); // "approve" or "reject"
        String status = "";
        String employeeEmail = "";
        boolean updated = false;

        try (Connection con = getConnection()) {

            if ("approve".equalsIgnoreCase(action)) {
                status = "Approved";

                // Fetch leave details
                PreparedStatement ps2 = con.prepareStatement(
                        "SELECT leave_type, from_date, to_date FROM leave_requests WHERE id = ?");
                ps2.setInt(1, requestId);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    String leaveType = rs2.getString("leave_type");
                    Date fromDate = rs2.getDate("from_date");
                    Date toDate = rs2.getDate("to_date");

                    LocalDate from = fromDate.toLocalDate();
                    LocalDate to = toDate.toLocalDate();
                    int daysCount = 0;
                    if ("CL".equalsIgnoreCase(leaveType)) {
                        // Working days excluding weekends and holidays
                        String clQuery = "SELECT COUNT(*) AS working_days FROM (" +
                            "SELECT ADDDATE(?, INTERVAL units.i + tens.i*10 + hundreds.i*100 + thousands.i*1000 DAY) AS dt " +
                            "FROM (SELECT 0 i UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
                            "UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) units " +
                            "CROSS JOIN (SELECT 0 i UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
                            "UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) tens " +
                            "CROSS JOIN (SELECT 0 i UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
                            "UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) hundreds " +
                            "CROSS JOIN (SELECT 0 i UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
                            "UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) thousands " +
                            "WHERE ADDDATE(?, INTERVAL units.i + tens.i*10 + hundreds.i*100 + thousands.i*1000 DAY) <= ?) calendar " +
                            "WHERE WEEKDAY(dt) < 5 AND dt NOT IN (SELECT `date` FROM holidays)";

                        PreparedStatement workingDaysStmt = con.prepareStatement(clQuery);
                        workingDaysStmt.setDate(1, fromDate);
                        workingDaysStmt.setDate(2, fromDate);
                        workingDaysStmt.setDate(3, toDate);

                        ResultSet wdRs = workingDaysStmt.executeQuery();
                        if (wdRs.next()) {
                            daysCount = wdRs.getInt("working_days");
                        }

                        int monthsBetween = (int) ChronoUnit.MONTHS.between(from.withDayOfMonth(1), to.withDayOfMonth(1));

                        if ((monthsBetween < 3 && daysCount > 1) || daysCount > 3) {
                            request.setAttribute("msg", "CL limit exceeded. Max 1 CL per month or 3 CLs together after 3 months.");
                            RequestDispatcher rd = request.getRequestDispatcher("admin_leave_approval.jsp");
                            rd.forward(request, response);
                            return;
                        }

                        updateLeaveBalance(con, "casual_leave", empCode, daysCount);
                    } else if ("EL".equalsIgnoreCase(leaveType)) {
                        daysCount = (int) ChronoUnit.DAYS.between(from, to) + 1;
                        updateLeaveBalance(con, "earned_leave", empCode, daysCount);
                    } else if ("ML".equalsIgnoreCase(leaveType)) {
                        daysCount = (int) ChronoUnit.DAYS.between(from, to) + 1;
                        updateLeaveBalance(con, "maternity_leave", empCode, daysCount);
                    }

                    // Update request status to Approved
                    PreparedStatement ps = con.prepareStatement("UPDATE leave_requests SET status=? WHERE id=?");
                    ps.setString(1, status);
                    ps.setInt(2, requestId);
                    updated = ps.executeUpdate() > 0;
                }

            } else if ("reject".equalsIgnoreCase(action)) {
                status = "Rejected";
                PreparedStatement ps = con.prepareStatement("UPDATE leave_requests SET status=? WHERE id=?");
                ps.setString(1, status);
                ps.setInt(2, requestId);
                updated = ps.executeUpdate() > 0;
            }

            // Fetch employee email
            PreparedStatement emailStmt = con.prepareStatement("SELECT email FROM employee_master WHERE emp_code=?");
            emailStmt.setString(1, empCode);
            ResultSet rs = emailStmt.executeQuery();
            if (rs.next()) {
                employeeEmail = rs.getString("email");
            }

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String errorDetails = sw.toString();
            request.setAttribute("msg", "Error processing leave approval: <br><pre>" + errorDetails + "</pre>");
            request.getRequestDispatcher("admin_leave_approval.jsp").forward(request, response);
            return;
        }

        // Send email if updated
        if (updated && employeeEmail != null && !employeeEmail.isEmpty()) {
            try {
                String subject = "Leave Request " + status;
                String body = "Dear " + empCode + ",\n\nYour leave request has been *" + status + "* by HR.\n\nRegards,\nHR Team";
                EmailUtil.sendEmail(employeeEmail, subject, body);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("msg", updated ? "Leave " + status + " and email sent." : "Failed to process leave request.");
        request.getRequestDispatcher("admin_leave_approval.jsp").forward(request, response);
    }

    private void updateLeaveBalance(Connection con, String column, String empCode, int days) throws SQLException {
        String query = "UPDATE leave_master SET " + column + " = " + column + " - ? WHERE emp_code = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, days);
            ps.setString(2, empCode);
            ps.executeUpdate();
        }
    }
}

/*
 * package net.javaguides.hrms.web;
 * 
 * import java.io.IOException; import java.sql.*; import
 * jakarta.servlet.ServletException; import
 * jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*;
 * 
 * import net.javaguides.hrms.util.EmailUtil; // ✅ Your EmailUtil class
 * 
 * @WebServlet("/LeaveApprovalServlet") public class LeaveApprovalServlet
 * extends HttpServlet { protected void doPost(HttpServletRequest request,
 * HttpServletResponse response) throws ServletException, IOException {
 * 
 * int requestId = Integer.parseInt(request.getParameter("id")); String empCode
 * = request.getParameter("emp_code"); String action =
 * request.getParameter("action"); // "approve" or "reject" String employeeEmail
 * = ""; boolean updated = false; String status = "";
 * 
 * try { // Step 1: DB connection Class.forName("com.mysql.cj.jdbc.Driver");
 * Connection con =
 * DriverManager.getConnection("jdbc:mysql://localhost:3306/hrms", "root",
 * "Hrithi@12345");
 * 
 * if ("approve".equals(action)) { status = "Approved";
 * 
 * // Update status PreparedStatement ps =
 * con.prepareStatement("UPDATE leave_requests SET status='Approved' WHERE id=?"
 * ); ps.setInt(1, requestId); updated = ps.executeUpdate() > 0;
 * 
 * // Fetch leave type & duration PreparedStatement ps2 = con.
 * prepareStatement("SELECT emp_code, leave_type, DATEDIFF(to_date, from_date) + 1 AS days FROM leave_requests WHERE id=?"
 * ); ps2.setInt(1, requestId); ResultSet rs2 = ps2.executeQuery();
 * 
 * if (rs2.next()) { String leaveType = rs2.getString("leave_type"); int days =
 * rs2.getInt("days");
 * 
 * // Update leave_master balance String updateQuery = ""; if
 * ("EL".equals(leaveType)) { updateQuery =
 * "UPDATE leave_master SET earned_leave = earned_leave - ? WHERE emp_code = ?";
 * } else if ("CL".equals(leaveType)) { updateQuery =
 * "UPDATE leave_master SET casual_leave = casual_leave - ? WHERE emp_code = ?";
 * } else if ("ML".equals(leaveType)) { updateQuery =
 * "UPDATE leave_master SET maternity_leave = maternity_leave - ? WHERE emp_code = ?"
 * ; }
 * 
 * if (!updateQuery.isEmpty()) { PreparedStatement ps3 =
 * con.prepareStatement(updateQuery); ps3.setInt(1, days); ps3.setString(2,
 * empCode); ps3.executeUpdate(); } } } else if ("reject".equals(action)) {
 * status = "Rejected";
 * 
 * // Update status to Rejected PreparedStatement ps =
 * con.prepareStatement("UPDATE leave_requests SET status='Rejected' WHERE id=?"
 * ); ps.setInt(1, requestId); updated = ps.executeUpdate() > 0; }
 * 
 * // Fetch employee email PreparedStatement emailStmt =
 * con.prepareStatement("SELECT email FROM employee_master WHERE emp_code=?");
 * emailStmt.setString(1, empCode); ResultSet rs = emailStmt.executeQuery(); if
 * (rs.next()) { employeeEmail = rs.getString("email"); }
 * 
 * con.close(); } catch (Exception e) { e.printStackTrace(); }
 * 
 * // Send email if update was successful if (updated && employeeEmail != null
 * && !employeeEmail.isEmpty()) { try { String subject = "Leave Request " +
 * status; String body = "Dear " + empCode +
 * ",\n\nYour leave request has been *" + status +
 * "* by HR.\n\nRegards,\nHR Team"; EmailUtil.sendEmail(employeeEmail, subject,
 * body); } catch (Exception e) { e.printStackTrace();
 * System.out.println("Failed to send email to: " + employeeEmail); } }
 * 
 * request.setAttribute("msg", updated ? "Leave " + status + " and email sent."
 * : "Failed to process leave request.");
 * request.getRequestDispatcher("admin_leave_approval.jsp").forward(request,
 * response); } }
 * 
 * 
 */